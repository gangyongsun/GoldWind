package cn.com.goldwind.kis.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.entity.ClickLog;
import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.entity.SearchLog;
import cn.com.goldwind.kis.entity.TermOperate;
import cn.com.goldwind.kis.entity.TermTypeBrowseLog;
import cn.com.goldwind.kis.entity.WebAccessLog;
import cn.com.goldwind.kis.entity.WebResponse;
import cn.com.goldwind.kis.mmseg.similarity.SimWordInterface;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.service.ClickLogService;
import cn.com.goldwind.kis.service.KeyInfoService;
import cn.com.goldwind.kis.service.SearchLogService;
import cn.com.goldwind.kis.service.TermOperateService;
import cn.com.goldwind.kis.service.TermTypeBrowseLogService;
import cn.com.goldwind.kis.service.WebAccessLogService;
import cn.com.goldwind.kis.service.WebResponseService;
import cn.com.goldwind.kis.shiro.controller.BaseController;
import cn.com.goldwind.kis.utils.LoggerUtils;
import cn.com.goldwind.kis.utils.redis.RedisCacheStorage;

@Controller
@RequestMapping("/kis")
public class KeyInfoController extends BaseController {
	private final String USERID_STR = "userId";
	private final String SIGN_STR = "sign";
	private final String SEARCHAUTORESULTLIST = "searchAutoResultListInRedis";

	private final String PRAISE = "praise";
	private final String TRAMPLE = "trample";
	private final String COLLECT = "collect";
	private final String REMARK = "remark";

	@Autowired
	private KeyInfoService keyInfoService;

	@Autowired
	private WebResponseService webResponseService;

	@Autowired
	private WebAccessLogService webAccessLogService;

	@Autowired
	private TermTypeBrowseLogService termTypeBrowseLogService;

	@Autowired
	private SearchLogService searchLogService;

	@Autowired
	private ClickLogService clickLogService;

	@Autowired
	private TermOperateService termOperateService;

	@Autowired
	private RedisCacheStorage<String, List<List<String>>> redisCacheStorage;

	/**
	 * 初始页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		// 1.首先获取userId和key
		String userId = request.getParameter(USERID_STR);
		String key = request.getParameter(SIGN_STR);

		if (null != userId && null != key) {
			// 2.验证userId是否被改过
			boolean flag = validateUserId(userId, key);
			// 3.如果userId没问题，则将HTTP头中的userId设置到Session
			if (flag) {
				HttpSession session = request.getSession();
				Object obj = session.getAttribute(USERID_STR);
				// 3.1如果session里已经存在userId属性
				if (null != obj) {
					LoggerUtils.fmtDebug(getClass(), "session中已经存在userId:[%s]", obj.toString());
					// 3.1.1判断是否与当前userId相同，不同则替换
					if (!userId.equals(obj.toString())) {
						session.setAttribute(USERID_STR, userId);
					}
				} else {
					// 3.2如果session中没有userId则设置
					session.setAttribute(USERID_STR, userId);
				}

				/**
				 * 记录网站访问记录
				 */
				WebAccessLog webAccessLog = new WebAccessLog();
				webAccessLog.setEmpNo(session.getAttribute(USERID_STR).toString());
				webAccessLog.setAccessTime(new Date());
				webAccessLogService.insert(webAccessLog);
			} else {
				LoggerUtils.info(this.getClass(), "验证失败!");
				gotoURL(response, "http://eis.goldwind.com.cn/showLogin");
			}
		} else {
			String empNoString = getEmpNO(request);
			if (null == empNoString || "".equals(empNoString)) {
				gotoURL(response, "http://eis.goldwind.com.cn/showLogin");
			}
		}

		// 热词展示
		List<String> hotKeyInfoList = keyInfoService.findHotTerms(8);
		if (null != hotKeyInfoList && hotKeyInfoList.size() > 0) {
			map.put("hotKeyInfoList", hotKeyInfoList);
		}
		return new ModelAndView("search");
	}

	/**
	 * 生成自动补充数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "generateAutofillData")
	@ResponseBody
	public List<List<String>> generateAutofillData() {
		List<List<String>> resultList = new ArrayList<List<String>>();

		List<List<String>> resultFromRedis = redisCacheStorage.get(SEARCHAUTORESULTLIST, new ArrayList<List<String>>());
		if (null == resultFromRedis) {
			List<String> englishList = keyInfoService.findTermList_en();
			List<String> chineseList = keyInfoService.findTermList_cn();
			List<String> abbreviationENList = keyInfoService.findTermList_abbreviationEN();
			List<String> abbreviationCNList = keyInfoService.findTermList_abbreviationCN();
			resultList.add(englishList);
			resultList.add(chineseList);
			resultList.add(abbreviationENList);
			resultList.add(abbreviationCNList);
			redisCacheStorage.set(SEARCHAUTORESULTLIST, resultList);
		} else {
			resultList = resultFromRedis;
		}

		return resultList;
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "search")
	@ResponseBody
	public TableSplitResult<KeyInfo> search(ModelMap map, String termType, String findContent, Integer pageSize, Integer pageNumber, HttpServletRequest request) {
		String empNo = getEmpNO(request);

		TableSplitResult<KeyInfo> page = null;

		// 1.按类型浏览
		if (null != termType && !"".equals(termType)) {
			map.put("termType", termType);
			/**
			 * 如果用户通过OA登录，按类型浏览术语列表记录
			 */
			if (null != empNo) {
				saveTermTypeBrowseLog(empNo, termType);
			}
			page = keyInfoService.findPagedTermByType(map, pageNumber, pageSize);
		}

		// 2.用户输入搜索条件搜索
		findContent = findContent.trim();// 先做一下trim
		// 进行算法匹配查找
		if (null != findContent && !"".equals(findContent)) {
			int isAaccurate = 0;// 设置匹配参数，默认是无匹配搜索0
			// 首先进行分词
			SimWordInterface sim_cal = SimWordInterface.getInstance();
			List<String> findContentResultList = sim_cal.getSimilaryTopWordsList(findContent);
			// 如果返回列表为空，则说明没有搜索到相关词
			if (null == findContentResultList || findContentResultList.size() == 0) {
				page = new TableSplitResult<KeyInfo>();
				page.setPageNo(1);
				page.setPageSize(0);
				page.setTotal(0);
				page.setRows(null);
				/**
				 * 如果用户通过OA登录，则记录匹配搜索记录
				 */
				if (null != empNo) {
					saveSearchLog(empNo, findContent, isAaccurate);
				}
			} else {
				// 如果列表返回的第一个元素与findContent相同，则进行精确搜索，否则进行范围搜索
				if (findContentResultList.get(0).equalsIgnoreCase(findContent)) {
//					map.put("findContent", findContent);
					isAaccurate = 1;// 精确匹配搜索
				} else {
//					map.put("findContentList", findContentResultList);
					isAaccurate = 2;// 分词匹配搜索
				}
				/**
				 * 如果用户通过OA登录，则记录匹配搜索记录
				 */
				if (null != empNo) {
					saveSearchLog(empNo, findContent, isAaccurate);
				}
				map.put("findContentList", findContentResultList);
				page = keyInfoService.findPagedTermBySearchCondition(map, pageNumber, pageSize);
			}
		}
		return page;
	}

	/**
	 * 从session里获取用户OA号;
	 * <p>
	 * 如果获取的对象不为null,则返回OA号字符串;
	 * <p>
	 * 否则返回null
	 * 
	 * @param request
	 * @return
	 */
	private String getEmpNO(HttpServletRequest request) {
		String empNo = null;
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(USERID_STR);
		if (null != obj) {
			empNo = obj.toString();
		}
		return empNo;
	}

	/**
	 * 记录按类型浏览记录
	 * 
	 * @param empNo    用户OA号
	 * @param termType 浏览类型
	 */
	private void saveTermTypeBrowseLog(String empNo, String termType) {
		TermTypeBrowseLog termTypeBrowseLog = new TermTypeBrowseLog(empNo, termType);
		TermTypeBrowseLog result = termTypeBrowseLogService.findByEmpNoAndTermType(termTypeBrowseLog);
		if (null == result) {
			// 查不到，则添加
			termTypeBrowseLog.setBrowseCounts(1);
			termTypeBrowseLogService.insert(termTypeBrowseLog);
		} else {
			// 查得到，则查询次数加1
			result.setBrowseCounts(result.getBrowseCounts() + 1);
			termTypeBrowseLogService.update(result);
		}
	}

	/**
	 * 记录搜索记录
	 * 
	 * @param empNo       用户OA号
	 * @param findContent 搜索内容
	 * @param isAaccurate 搜索匹配模式
	 */
	private void saveSearchLog(String empNo, String findContent, int isAccurate) {
		SearchLog searchLog = new SearchLog(empNo, findContent, isAccurate);
		SearchLog result = searchLogService.findByEmpNoAndFindContent(searchLog);
		if (null == result) {
			// 查不到，则添加
			searchLog.setSearchCounts(1);
			searchLogService.insert(searchLog);
		} else {
			// 查得到，则查询次数加1
			result.setSearchCounts(result.getSearchCounts() + 1);
			searchLogService.update(result);
		}
	}

	/**
	 * 术语点击记录
	 * 
	 * @param empNO  用户OA号
	 * @param termId 术语条目主键id
	 */
	private void saveClickLog(String empNO, Integer termId) {
		ClickLog clickLog = new ClickLog(empNO, termId);
		ClickLog result = clickLogService.findEmpNoAndTermId(clickLog);
		if (null == result) {
			// 查不到，则添加
			clickLog.setClickCounts(1);
			clickLogService.insert(clickLog);
		} else {
			// 查得到，则查询次数加1
			result.setClickCounts(result.getClickCounts() + 1);
			clickLogService.update(result);
		}
	}

	/**
	 * 查询术语详情
	 * 
	 * @param map
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "showDetail")
	public ModelAndView showDetail(ModelMap map, Integer id, HttpServletRequest request) {
		String empNO = getEmpNO(request);
		if (null != empNO) {
			saveClickLog(empNO, id);
			TermOperate termOperateSearchResult = termOperateService.findByTermIdAndEmpNO(id,empNO);
			map.put("termOperate", termOperateSearchResult);
		}

		KeyInfo keyInfo = keyInfoService.findTermById(id);
		if (null != keyInfo) {
			map.put("keyInfo", keyInfo);
			keyInfo.setTotalClick(keyInfo.getTotalClick() + 1);
			keyInfoService.updateTerm(keyInfo);
		}
		return new ModelAndView("detail");
	}

	/**
	 * 查询术语类型对应的数量
	 * 
	 * @return
	 */
	@RequestMapping(value = "quantatySummary")
	@ResponseBody
	public Map<String, Integer> quantatySummary() {
		List<AccessSummary> quantatySummaryList = keyInfoService.findQuantatySummary();
		Map<String, Integer> quantatySummaryMap = new LinkedHashMap<String, Integer>();

		for (AccessSummary quantatySummary : quantatySummaryList) {
			quantatySummaryMap.put(quantatySummary.getClassification(), quantatySummary.getClassificationQuantaty());
		}
		return quantatySummaryMap;
	}

	/**
	 * 查询各术语类型对应的点击量
	 * 
	 * @return
	 */
	@RequestMapping(value = "accessSummary")
	@ResponseBody
	public Map<String, Integer> accessSummary(Integer classificationLevel) {
		List<AccessSummary> accessSummaryList = null;
		if (classificationLevel == 2) {
			accessSummaryList = keyInfoService.findAccessSummary();
		} else if (classificationLevel == 3) {
			accessSummaryList = keyInfoService.findAccessSummary4backGround();
		}

		Map<String, Integer> accessSummaryMap = new LinkedHashMap<String, Integer>();

		for (AccessSummary accessSummary : accessSummaryList) {
			//TODO 三级分类有个是空的
			if (!accessSummary.getClassification().trim().equals("")) {
				accessSummaryMap.put(accessSummary.getClassification(), accessSummary.getTotalAccess());
			}
		}
		return accessSummaryMap;
	}

	/**
	 * 查询更多热词
	 * 
	 * @param number
	 * @return
	 */
	@RequestMapping(value = "showMoreHotTerms")
	public ModelAndView showMoreHotTerms(ModelMap map, Integer number) {
		List<String> hotKeyInfoList = keyInfoService.findHotTerms(number);
		if (null != hotKeyInfoList && hotKeyInfoList.size() > 0) {
			map.put("hotKeyInfoList", hotKeyInfoList);
		}
		return new ModelAndView("hotTerms");
	}

	/**
	 * 收集用户反馈信息
	 * 
	 * @param content
	 * @return
	 */
	@RequestMapping(value = "webresponseLog")
	@ResponseBody
	public Map<String, Object> webresponseLog(String content, HttpServletRequest request) {
		String empNo = getEmpNO(request);
		/**
		 * 如果用户通过OA登录
		 */
		if (null != empNo) {
			WebResponse webResponse = new WebResponse();
			webResponse.setEmpNO(empNo);
			webResponse.setContent(content);
			webResponse.setResponseTime(new Date());
			webResponseService.saveWebresponseLog(webResponse);
			resultMap.put("status", 200);
			resultMap.put("message", "感谢您宝贵的建议！");
			resultMap.put("messageEn", "Thanks for your suggestions!");
		} else {
			resultMap.put("status", 400);
			resultMap.put("message", "请通过OA登录该系统进行意见反馈！");
			resultMap.put("messageEn", "Please log in to the system through OA for feedback!");
		}
		return resultMap;
	}

	/**
	 * 用户对术语的个人操作
	 * 
	 * @param termId
	 * @param operateType
	 * @param operateValue
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "termOperate")
	@ResponseBody
	public Map<String, Object> termOperate(Integer termId, String operateType, boolean operateValue, String remark, HttpServletRequest request) {
		String empNO = getEmpNO(request);
		/**
		 * 如果用户通过OA登录
		 */
		if (null != empNO) {
			int flag;
			// 1.先根据termId和empNo查询有无操作记录
			TermOperate newTermOperate = termOperateService.findByTermIdAndEmpNO(termId,empNO);
			if (null != newTermOperate) {
				// 1.1.如果有操作记录，拿出来更新
				switch (operateType) {
				case PRAISE:
					newTermOperate.setPraise(operateValue);
					break;
				case TRAMPLE:
					newTermOperate.setTrample(operateValue);
					break;
				case COLLECT:
					newTermOperate.setCollect(operateValue);
					break;
				case REMARK:
					newTermOperate.setRemark(remark);
					break;
				default:
					break;
				}
				flag = termOperateService.updateTermOperate(newTermOperate);
			} else {
				// 1.2.如果无操作记录，新增操作记录
				newTermOperate = new TermOperate();
				newTermOperate.setTermId(termId);
				newTermOperate.setEmpNO(empNO);
				switch (operateType) {
				case PRAISE:
					newTermOperate.setPraise(operateValue);
					break;
				case TRAMPLE:
					newTermOperate.setTrample(operateValue);
					break;
				case COLLECT:
					newTermOperate.setCollect(operateValue);
					break;
				case REMARK:
					newTermOperate.setRemark(remark);
					break;
				default:
					break;
				}
				flag = termOperateService.saveTermOperate(newTermOperate);
			}
			if (flag == 1) {
				resultMap.put("status", 200);
				resultMap.put("message", "操作成功！");
				resultMap.put("messageEn", "operation successful!");
			} else {
				resultMap.put("status", 201);
				resultMap.put("message", "操作失败！");
				resultMap.put("messageEn", "operation failed!");
			}
		} else {
			resultMap.put("status", 400);
			resultMap.put("message", "请通过OA登录该系统进行操作！");
			resultMap.put("messageEn", "Please log in to the system through OA for operation!");
		}
		return resultMap;
	}

}
