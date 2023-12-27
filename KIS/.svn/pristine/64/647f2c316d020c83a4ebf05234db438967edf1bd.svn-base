package cn.com.goldwind.kis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.entity.ClickLog;
import cn.com.goldwind.kis.entity.KeyInfoNonSearch;
import cn.com.goldwind.kis.entity.SearchLog;
import cn.com.goldwind.kis.entity.TermTypeBrowseLog;
import cn.com.goldwind.kis.entity.WebAccessLog;
import cn.com.goldwind.kis.entity.WebResponse;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.service.ClickLogService;
import cn.com.goldwind.kis.service.NonSearchService;
import cn.com.goldwind.kis.service.SearchLogService;
import cn.com.goldwind.kis.service.TermTypeBrowseLogService;
import cn.com.goldwind.kis.service.WebAccessLogService;
import cn.com.goldwind.kis.service.WebResponseService;

@Controller
@RequestMapping("/console")
public class ConsoleController {
	@Autowired
	private NonSearchService nonSearchService;

	@Autowired
	private WebResponseService webResponseService;

	@Autowired
	private ClickLogService clickLogService;

	@Autowired
	private SearchLogService searchLogService;

	@Autowired
	private TermTypeBrowseLogService termTypeBrowseLogService;

	@Autowired
	private WebAccessLogService webAccessLogService;

	/**
	 * noneSearchIndex初始页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "noneSearchIndex")
	public ModelAndView noneSearchIndex(ModelMap map) {
		return new ModelAndView("console/noneSearch");
	}

	/**
	 * webResponseIndex初始页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "webResponseIndex")
	public ModelAndView webResponseIndex(ModelMap map) {
		return new ModelAndView("console/webResponse");
	}

	/**
	 * monitor初始页面
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "monitorIndex")
	public ModelAndView monitorIndex(ModelMap map) {
		return new ModelAndView("console/monitorIndex");
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "noneSearchList")
	@ResponseBody
	public TableSplitResult<KeyInfoNonSearch> noneSearchList(ModelMap map, String findContent, Integer pageSize, Integer pageNumber) {
		map.put("findContent", findContent);
		TableSplitResult<KeyInfoNonSearch> page = nonSearchService.findNoneSearchListPage(map, pageNumber, pageSize);
		return page;
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "webResponseList")
	@ResponseBody
	public TableSplitResult<WebResponse> webResponseList(ModelMap map, String findContent, Integer pageSize, Integer pageNumber) {
		map.put("findContent", findContent);
		TableSplitResult<WebResponse> page = webResponseService.findWebResponseListPage(map, pageNumber, pageSize);
		return page;
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "clickLogList")
	@ResponseBody
	public TableSplitResult<ClickLog> clickLogList(ModelMap map, Integer pageSize, Integer pageNumber) {
		TableSplitResult<ClickLog> page = clickLogService.findPage(map, pageNumber, pageSize);
		return page;
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "searchLogList")
	@ResponseBody
	public TableSplitResult<SearchLog> searchLogList(ModelMap map, String findContent, Integer pageSize, Integer pageNumber) {
		map.put("findContentCondition", findContent);
		TableSplitResult<SearchLog> page = searchLogService.findPage(map, pageNumber, pageSize);
		return page;
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "termTypeBrowseLogList")
	@ResponseBody
	public TableSplitResult<TermTypeBrowseLog> termTypeBrowseLogList(ModelMap map, String termType, Integer pageSize, Integer pageNumber) {
		map.put("termTypeCondition", termType);
		TableSplitResult<TermTypeBrowseLog> page = termTypeBrowseLogService.findPage(map, pageNumber, pageSize);
		return page;
	}

	/**
	 * bootstrap table分页查询
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "webAccessLogList")
	@ResponseBody
	public TableSplitResult<WebAccessLog> webAccessLogList(ModelMap map, Integer pageSize, Integer pageNumber) {
		TableSplitResult<WebAccessLog> page = webAccessLogService.findPage(map, pageNumber, pageSize);
		return page;
	}

}
