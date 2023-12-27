package cn.com.goldwind.kis.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.goldwind.kis.dto.KeyInfoDto;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@RequestMapping("/getKeyInfo")
	public KeyInfoDto getKeyInfoDto() {
		KeyInfoDto keyInfoDto=new KeyInfoDto();
		keyInfoDto.setChinese("你好，世界");
		keyInfoDto.setEnglish("Hello World");
		keyInfoDto.setId("001");
		
		return keyInfoDto;
	}
	
}
