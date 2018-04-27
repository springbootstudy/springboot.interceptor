package com.ctsi.springboot.token.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ctsi.springboot.token.util.JwtUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = Logger.getLogger(LoginInterceptor.class);

	// 控制器执行前调用
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("@@ preHandle");
		response.setContentType("application/json; charset=utf-8");
		
		String token = request.getHeader("token");
		log.info(token);
		// 通过验证 true
		if (StringUtils.isEmpty(token)) {
			log.info("token为空");
			
			try ( Writer writer = response.getWriter() ) {
				writer.write("请登录系统");
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
			return false;
		}
		// 否则 false
		else {
			try {
//				JwtUtil.validateToken(token);
				Claims claims = JwtUtil.getClaimsFromToken(token);
				Date date = claims.getExpiration();
				long tokenTime = date.getTime();
				log.info("获取Token的时间 " + tokenTime + ", " + new Date(tokenTime));
				long curTime = System.currentTimeMillis();
				log.info("当前时间 " + curTime + ", " + new Date(curTime));
				
				if (curTime > tokenTime) {
					try ( Writer writer = response.getWriter() ) {
						writer.write("token 过期，请重新获取");
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					
					return false;
				}
			}
			catch (ExpiredJwtException ex) {
				log.info("token 过期");
				ex.printStackTrace();
				try ( Writer writer = response.getWriter() ) {
					writer.write("token 过期，请重新获取");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				return false;
			}
			catch (Exception ex) {
				ex.printStackTrace();
				log.info("解析token出错");
				
				try ( Writer writer = response.getWriter() ) {
					writer.write("token 不正确");
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				return false;
			}
			return true;
		}
		
//		return super.preHandle(request, response, handler);
	}

	// 在后端控制器执行后调用
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("@@ postHandle");
		
		
		
		super.postHandle(request, response, handler, modelAndView);
	}

	// 整个请求执行完成后调用
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		log.info("@@ afterCompletion");
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		log.info("@@ afterConcurrentHandlingStarted");
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}
