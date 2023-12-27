package cn.com.goldwind.kis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.com.goldwind.kis.bo.HolyPublication;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.repository.DemoRepository;

@Service
public class DemoService extends BaseMybatisDao<DemoRepository>{
	
	@Cacheable(value = "keycache")
	public String testCache(String key) {
		System.out.println("testCache:" + key);
		return key;
	}
	
	@Autowired
	private DemoRepository demoRepository;

	public List<HolyPublication> findHolyPublicationList() {
		return demoRepository.selectAll();
	}
}