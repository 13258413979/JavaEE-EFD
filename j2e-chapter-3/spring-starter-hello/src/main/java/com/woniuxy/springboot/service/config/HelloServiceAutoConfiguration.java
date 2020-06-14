package com.woniuxy.springboot.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.woniuxy.springboot.service.HelloService;

@Configuration
//开启属性配置
@EnableConfigurationProperties(HelloServiceProperties.class)
//如果在类路径下存在HelloService才进行自动的配置
@ConditionalOnClass(HelloService.class)
//总开关
@ConditionalOnProperty(prefix = "woniuxy.hello", value = "enabled", matchIfMissing = true)
public class HelloServiceAutoConfiguration {
	
	@Autowired
	private HelloServiceProperties helloServiceProperties;

	@Bean
	@ConditionalOnMissingBean(HelloService.class)
	// bean不存在则创建
	public HelloService helloService() {
		HelloService helloService = new HelloService();
		helloService.setMessage(helloServiceProperties.getMessage());
		return helloService;
	}

}
