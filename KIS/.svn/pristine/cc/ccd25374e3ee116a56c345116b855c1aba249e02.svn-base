package cn.com.goldwind.kis.web;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.bo.SearchLogCountSum;
import cn.com.goldwind.kis.service.SearchLogService;
import cn.com.goldwind.kis.service.WebAccessLogService;

@Controller
@RequestMapping(value = "/system")
public class SystemController {

	@Autowired
	private WebAccessLogService webAccessLogService;

	@Autowired
	private SearchLogService searchLogService;

	/**
	 * 初始页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "main")
	public ModelAndView index(ModelMap map) {
		map.put("totalAccessEmp", webAccessLogService.getTotalAccessEmp());
		map.put("totalAccessCount", webAccessLogService.getTotalAccessCount());
		map.put("totalSearchCount", searchLogService.getTotalSearchCount());

		SearchLogCountSum searchLogCountSum = searchLogService.getSearchCountByAccurate();
		
		int accurate=searchLogCountSum.getAccurateCount();
		int none=searchLogCountSum.getNoneCount();
		int fuzzy=searchLogCountSum.getFuzzyCount();
		float num= (float)accurate/(accurate+none+fuzzy);  

		DecimalFormat df = new DecimalFormat("0.0%");
		
		map.put("percentage", df.format(num));
		return new ModelAndView("system/main");
	}
}
