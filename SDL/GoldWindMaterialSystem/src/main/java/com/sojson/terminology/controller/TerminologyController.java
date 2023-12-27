package com.sojson.terminology.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sojson.common.controller.BaseController;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.common.utils.PropertiesUtil;
import com.sojson.core.mybatis.page.Pagination;
import com.sojson.core.shiro.token.manager.TokenManager;
import com.sojson.terminology.bo.EntryId;
import com.sojson.terminology.bo.Hit;
import com.sojson.terminology.bo.MaterialCollected;
import com.sojson.terminology.bo.OrderItem;
import com.sojson.terminology.service.OrderItemService;
import com.sojson.terminology.service.TerminologyService;

/**
 * 查询关键词
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("terminology")
public class TerminologyController extends BaseController {

	@Autowired
	public TerminologyService termService;

	@Autowired
	public OrderItemService orderItemService;

	/**
	 * 跳转到关键词门户首页,包含search
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index() {
		return new ModelAndView("terminology/index");
	}

	/**
	 * 跳转到关键词详情页
	 * 
	 * @return
	 */
	@RequestMapping("detail")
	public ModelAndView terminologyDetail(ModelMap modelMap) {
		return new ModelAndView("terminology/detail");
	}

	/**
	 * 搜索关键词
	 * 
	 * @param terminologyWord 关键词
	 * @param languageId      语言编号
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView search(ModelMap modelMap, String findContent, String languageId) {
		List<Hit> hitlist = new ArrayList<Hit>();
		try {
			int languageCode = Integer.parseInt(PropertiesUtil.getValueByKey(languageId, "config.properties"));

			JSONArray hitsArray = termService.search(generateSdlToken(), findContent, languageCode);
			if (null != hitsArray) {
				for (Iterator<Object> iterator = hitsArray.iterator(); iterator.hasNext();) {
					JSONObject jsonObj = (JSONObject) iterator.next();

					JSONObject entryIdObject = (JSONObject) jsonObj.get("entryId");
					EntryId entryId = new EntryId();
					entryId.setId(entryIdObject.getIntValue("id"));
					entryId.setUuid(entryIdObject.getString("uuid"));

					Hit hit = new Hit();
					hit.setLanguageId(jsonObj.getIntValue("languageId"));
					hit.setScore(jsonObj.getBigDecimal("score"));
					hit.setSimilarity(jsonObj.getIntValue("similarity"));
					hit.setTerm(jsonObj.getString("term"));
					hit.setTermbaseId(jsonObj.getIntValue("termbaseId"));
					hit.setEntryId(entryId);

					/**
					 * 判断该关键词或物资是否已经被收藏
					 */
					Long uid = TokenManager.getUserId();
					MaterialCollected result = termService.searchByNameAndUid(hit.getTerm(), uid);
					if (null != result) {
						hit.setCollectStatus(true);
					} else {
						hit.setCollectStatus(false);
					}
					hitlist.add(hit);
				}
			}
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "查询关键词失败！source[%s]", findContent);
		}
		modelMap.put("termlist", hitlist);
		return new ModelAndView("terminology/index");
	}

	/**
	 * 加入购物清单
	 * 
	 * @param materialCollected
	 * @return
	 */
	@RequestMapping(value = "add2cart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add2cart(OrderItem orderItem) {
		try {
			OrderItem entity = orderItemService.searchByNameAndUid(orderItem.getItem_name(), TokenManager.getUserId());
			if (null != entity) {
				entity.setNum(entity.getNum() + 1);
				orderItemService.updateByPrimaryKey(entity);
			} else {
				orderItem.setUser_id(TokenManager.getUserId());
				orderItem.setNum(1);
				int count = orderItemService.insertSelective(orderItem);
				resultMap.put("successCount", count);
			}
			resultMap.put("status", 200);
			resultMap.put("message", "加入购物清单成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "加入购物清单失败！");
			LoggerUtils.fmtError(getClass(), e, "加入购物清单失败：source[%s]", orderItem.toString());
		}
		return resultMap;
	}

	/**
	 * 收藏关键词
	 * 
	 * @param materialCollected
	 * @return
	 */
	@RequestMapping(value = "add2collection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add2collection(MaterialCollected materialCollected) {
		try {
			MaterialCollected entity = termService.searchByNameAndUid(materialCollected.getMaterial_name(), TokenManager.getUserId());
			if (null != entity) {
				resultMap.put("status", 501);
				resultMap.put("message", "该物资已经被收藏，请勿重复收藏！");
			} else {
				materialCollected.setUid(TokenManager.getUserId());
				Date date = new Date();
				materialCollected.setCollected_time(date);
				materialCollected.setStatus(true);
				int count = termService.insertSelective(materialCollected);
				resultMap.put("status", 200);
				resultMap.put("resultMsg", "收藏成功！");
				resultMap.put("successCount", count);
			}
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "收藏失败！");
			LoggerUtils.fmtError(getClass(), e, "收藏关键词失败：source[%s]", materialCollected.toString());
		}
		return resultMap;
	}

	/**
	 * 取消收藏关键词
	 * 
	 * @param materialCollected
	 * @return
	 */
	@RequestMapping(value = "removeFromCollection", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> removeFromCollection(MaterialCollected materialCollected) {
		materialCollected.setUid(TokenManager.getUserId());
		try {
			int count = termService.deleteCollect(materialCollected);
			resultMap.put("status", 200);
			resultMap.put("resultMsg", "删除收藏成功！");
			resultMap.put("successCount", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "删除收藏失败！");
			LoggerUtils.fmtError(getClass(), e, "删除收藏失败：source[%s]", materialCollected.toString());
		}
		return resultMap;
	}

	/**
	 * 物资收藏列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "collection")
	public ModelAndView list(ModelMap modelMap, Integer pageNo) {
		modelMap.put("uid", TokenManager.getToken().getId());
		Pagination<MaterialCollected> page = termService.findByPage(modelMap, pageNo, pageSize);
		modelMap.put("page", page);
		return new ModelAndView("terminology/collection");
	}

	/**
	 * 关键词物资自定义
	 * 
	 * @return
	 */
	@RequestMapping(value = "termcustom")
	public ModelAndView termcustom(ModelMap modelMap) {
		return new ModelAndView("terminology/termcustom");
	}

	/**
	 * 根据主键ID数组删除收藏物资或关键词
	 * 
	 * @param ids 如果有多个，以“,”间隔
	 * @return
	 */
	@RequestMapping(value = "deleteMaterialByIds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMaterialrByIds(String ids) {
		return termService.deleteMaterialByIds(ids);
	}

}
