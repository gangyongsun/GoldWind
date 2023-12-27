package com.sojson.terminology.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sojson.common.utils.JerseyClient;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.common.utils.PropertiesUtil;
import com.sojson.core.mybatis.BaseMybatisDao;
import com.sojson.core.mybatis.page.Pagination;
import com.sojson.terminology.bo.MaterialCollected;
import com.sojson.terminology.dao.MaterialCollectedMapper;
import com.sojson.terminology.service.TerminologyService;

/**
 * 关键词或物资服务实现类
 * 
 * @author alvin
 *
 */
@Service("terminologyService")
public class TerminologyServiceImpl extends BaseMybatisDao<MaterialCollectedMapper> implements TerminologyService {

	@Autowired
	MaterialCollectedMapper materialCollectedMapper;

	@Override
	public JSONArray search(String token, String terminologyWord, int languageId) {
		String apiURL = PropertiesUtil.getValueByKey("API_SEARCH", "config.properties");
		String params = "{'term':'" + terminologyWord + "','sourceLanguageIds':" + languageId + "}";
		JSONObject jsonObject = JerseyClient.getMethod(apiURL, token, params);
		JSONArray resultArray=null;
		if (null!=jsonObject) {
			 resultArray=jsonObject.getJSONArray("hits");
		}
		return resultArray;
	}

	@Override
	public Pagination<MaterialCollected> findByPage(Map<String, Object> map, Integer pageNo, int pageSize) {
		return super.findPage(map, pageNo, pageSize);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return materialCollectedMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(MaterialCollected record) {
		return materialCollectedMapper.insert(record);
	}

	@Override
	public int insertSelective(MaterialCollected record) {
		return materialCollectedMapper.insertSelective(record);
	}

	@Override
	public MaterialCollected selectByPrimaryKey(Long id) {
		return materialCollectedMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(MaterialCollected record) {
		return materialCollectedMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MaterialCollected record) {
		return materialCollectedMapper.updateByPrimaryKey(record);
	}

	@Override
	public MaterialCollected searchByNameAndUid(String materialName, Long uid) {
		return materialCollectedMapper.searchByNameAndUid(materialName,uid);
	}

	@Override
	public MaterialCollected searchByMidAndUid(String materialId, Long uid) {
		return materialCollectedMapper.searchByMidAndUid(materialId,uid);
	}

	@Override
	public int deleteCollect(MaterialCollected materialCollected) {
		return materialCollectedMapper.deleteCollect(materialCollected);
	}

	@Override
	public Map<String, Object> deleteMaterialByIds(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String id : idArray) {
				count += this.deleteByPrimaryKey(new Long(id));
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除收藏物资出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

}
