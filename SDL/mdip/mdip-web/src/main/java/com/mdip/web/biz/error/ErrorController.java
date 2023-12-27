package com.mdip.web.biz.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
	
	@RequestMapping("404")
	public String err404(){
		return "/errors/404";
	}
	
	@RequestMapping("500")
	public String err500(){
		return "/errors/500";
	}
}
