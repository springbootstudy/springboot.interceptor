package com.ctsi.springboot.token.interceptor;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppInterceptor implements WebMvcConfigurer {
	
	private static final Logger log = Logger.getLogger(WebAppInterceptor.class);

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("## 进入拦截器 ##");
		
		// 除了登录，全部拦截
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login");
		
	}
	
	

}
