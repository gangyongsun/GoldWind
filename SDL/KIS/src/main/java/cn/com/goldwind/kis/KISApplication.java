package cn.com.goldwind.kis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("cn.com.goldwind.kis")
public class KISApplication {

	public static void main(String[] args) {
		SpringApplication.run(KISApplication.class, args);
	}

}
