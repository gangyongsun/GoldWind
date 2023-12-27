package cn.com.goldwind.kis.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.service.KeyInfoService;
import cn.com.goldwind.kis.utils.redis.RedisCacheStorage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisCacheStorageTest {

	@Autowired
	private KeyInfoService keyInfoService;
	
	@Autowired
	@Qualifier("redisCacheStorage")
	private RedisCacheStorage<String, List<List<String>> > redisCacheStorage;

//	@Test
//	public void testSet() throws Exception {
//		System.out.print("开始执行测试");
//		
//		AccessSummary as = new AccessSummary();
//		as.setClassification("分类1");
//		as.setTotalAccess(100);
//
//		redisCacheStorage.set("AccessSummary", as);
//		
//		AccessSummary as2 = (AccessSummary) redisCacheStorage.get("AccessSummary", new AccessSummary());
//
//		System.out.println("=======" + as2.getClassification() + "=====" + as2.getTotalAccess());
//	}

	@Test
	public void test2() throws Exception {
		List<List<String>> resultList = new ArrayList<List<String>>();
		List<String> englishList = keyInfoService.findTermList_en();
		List<String> chineseList = keyInfoService.findTermList_cn();
		resultList.add(englishList);
		resultList.add(chineseList);
		
		redisCacheStorage.set("resultList", resultList);
		List<List<String>> resultListSer =redisCacheStorage.get("resultList", new ArrayList<List<String>>());
		System.out.println(resultListSer.get(0).toString());
	}

}