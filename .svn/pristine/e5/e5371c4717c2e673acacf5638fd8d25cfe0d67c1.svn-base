package com.yixin.alixjob;

import com.yixin.core.context.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Configuration
@SpringBootApplication
@ComponentScan(basePackages = { "com.yixin" })
@tk.mybatis.spring.annotation.MapperScan(basePackages = { "com.yixin.alixjob.mapper" })
@EnableTransactionManagement
public class SpringbootApplication  extends SpringBootServletInitializer {

//
	@Autowired
	private RestTemplateBuilder templateBuilder;

	@Bean
	public RestTemplate getRestTemplate() {
		return templateBuilder.build();
	}

	@Bean
	public SpringContextUtil springContextUtil() {
		return new SpringContextUtil();
	}
	/**
	 * 实现SpringBootServletInitializer可以让spring-boot项目在web容器中运行
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		builder.sources(this.getClass());
		return super.configure(builder);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}


}
