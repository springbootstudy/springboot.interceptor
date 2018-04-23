package com.ctsi.springboot.token.web.application;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctsi.springboot.token.util.JwtUtil;

@RestController
public class IndexController {
	
	private static final Logger log = Logger.getLogger(IndexController.class);
	
	@RequestMapping("/index")
	public String index() {
		log.info("## Index");
		
		return "OK";
	}
	
	@RequestMapping("/login")
	public Object login(String username, String passwd) {
		log.info("## login " + username + ", " + passwd);
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 通过认证的账号
		if ("a".equals(username) && "b".equals(passwd)) {
			Map<String, Object> claims = new HashMap<>();
			String token = JwtUtil.generateToken(claims);
			log.info("## " + token);
			
			map.put("token", token);
		}
		// 不通过
		else {
			map.put("error", HttpStatus.UNAUTHORIZED);
		}
		
		return map;
	}

}
