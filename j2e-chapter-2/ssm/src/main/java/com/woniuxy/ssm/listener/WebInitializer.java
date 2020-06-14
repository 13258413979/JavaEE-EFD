package com.woniuxy.ssm.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.woniuxy.ssm.config.App;
import com.woniuxy.ssm.config.WebMvcConfig;

public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext act=new AnnotationConfigWebApplicationContext();
 		act.register(App.class,WebMvcConfig.class);
 		//和servlet整合
 		act.setServletContext(servletContext);
 		
 		//配置springmvc的控制器
 		Dynamic dynamic = servletContext.addServlet("springmvc", new DispatcherServlet(act));
 		//设置后缀
 		dynamic.addMapping("/");
 		dynamic.setLoadOnStartup(1);
	}

}
