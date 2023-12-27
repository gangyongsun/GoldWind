package cn.com.goldwind.kis.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cn.com.goldwind.kis.web.KeyInfoController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KISApplicationTests {

	@Test
	public void contextLoads() {
	}

	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new KeyInfoController()).build();
	}

	@Test
	public void getIndex() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/kis/").accept(MediaType.APPLICATION_JSON));
	}
}
