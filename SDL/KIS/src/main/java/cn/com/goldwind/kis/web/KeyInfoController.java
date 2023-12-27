package cn.com.goldwind.kis.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.service.KeyInfoService;

@Controller
@RequestMapping("/kis")
public class KeyInfoController {
	@Autowired
	KeyInfoService keyInfoService;

	/**
	 * 初始页面进行重定向到list查询
	 * 
	 * @return
	 */
	@GetMapping("/")
	public String index() {
		return "index";
	}

	/**
	 * 查询所有数据
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String hello(Model model) {
		List<KeyInfo> keyInfoList = keyInfoService.findAll();
		model.addAttribute("keyInfoList", keyInfoList);
		return "index";
	}

	/**
	 * 搜索关键词
	 * 
	 * @param modelMap
	 * @param findContent 关键词
	 * @return
	 */
	@RequestMapping(value = "searchAll", method = RequestMethod.POST)
	public String search(ModelMap modelMap, String findContent) {
		List<KeyInfo> keyInfoList = keyInfoService.findByKeyInfo(findContent);
		modelMap.put("keyInfoList", keyInfoList);
		return "index::keyInfoContentFragment";
	}

}
