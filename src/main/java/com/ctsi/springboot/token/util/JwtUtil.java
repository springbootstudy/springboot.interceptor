package com.ctsi.springboot.token.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @author lb
 *
 * @since 2018年4月23日
 *
 *
 */
public class JwtUtil {
	
	private static final Logger log = Logger.getLogger(JwtUtil.class);
	
	/**
     * 密钥
     */
    private static final String secret = "nc.moc.istc";

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String generateToken(Map<String, Object> claims) {
    	long time = 3600 * 1000 * 2;
        Date expirationDate = new Date(System.currentTimeMillis() + time);
        
        String token = Jwts.builder().setClaims(claims).setExpiration(expirationDate).
        		signWith(SignatureAlgorithm.HS512, secret).compact();
        log.info(token);
        
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static  Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } 
        catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     */
    public static void validateToken(String token) throws Exception {
    	Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//    	try {
//    	}
//    	catch (Exception ex) {
//    		log.info("解析令牌失败");
//    		ex.printStackTrace();
//    	}
    }

}
