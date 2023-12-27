package cn.com.goldwind.kis.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.goldwind.kis.service.impl.DemoService;

@Controller
@RequestMapping("/demo")
public class DemoController {
    @Autowired  
    DemoService demoService;  
     
    @RequestMapping("testcache")
    @ResponseBody
    public String testCache(@RequestParam String key){
        String s = demoService.testCache(key);
        return s;
    }
    
    @RequestMapping("/getseansession")
    @ResponseBody
    public Map<String,String> getSession(HttpServletRequest request){
        request.getSession().setAttribute("message", request.getRequestURI());
        
        Map<String,String> attributeMap = new HashMap<String, String>();
        attributeMap.put("message", request.getRequestURI());
        
        System.out.println("sessionID:" + request.getSession().getId());
        return attributeMap;
    }
}