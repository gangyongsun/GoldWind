package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyInfoProperties {

	@Value("${cn.com.goldwind.title}")
	private String title;

	@Value("${cn.com.goldwind.description}")
	private String description;
}
