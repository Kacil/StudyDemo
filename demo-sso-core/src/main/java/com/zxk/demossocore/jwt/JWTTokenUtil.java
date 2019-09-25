package com.zxk.demossocore.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * JWT TTOKEN生成工具类
 */
public class JWTTokenUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(JWTTokenUtil.class);
	//证书
	private static final String SECRETKEY = "DEMO_ZXK";
	//失效时间毫秒
	private static final long INVALID_TIME = 1000*60*60*24;


	/**
	 * 根据设置Claims队形属性生成token
	 * 组成部分：中间用点（.）分隔成三个部分{Header（头部）.Payload（负载）.Signature（签名）}
	 * eyJhbGciOiJIUzUxMiJ9
	 * .eyJwaG9uZSI6IjE3NjM4MTY1NTI0IiwiZXhwIjoxNTY5MTIxNjY2LCJ1c2VySWQiOiIxMjM0NTY3ODkifQ
	 * .pezazlTo34V_VT-efUdMDNZWCPlabBjlpwRFR-rKFiQ4qkJGg7LmCDpWIlPk_U7kcfMkozoSTr_y3fTjfiYsrw
	 * @param map
	 * @return
	 */
	public  static String createToken(Map<String, Object> map){
		String userToken = Jwts.builder()
				.setSubject("DEMO")
				.setClaims(map)
				.setExpiration(new Date(System.currentTimeMillis() + INVALID_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRETKEY).compact();
//				.claim("userId", userId)
//				.claim("phone", phone)

		return userToken;
	}

	/** token转换为 */
	public static Claims parseClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param token
	 * @return 根据token获取userId属性
	 * @throws Exception
	 */
	public  static long getUserId(String token) throws Exception{
		Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
		long userId = (Integer)claims.get("userId");
	    return userId;
	}


	/**
	 * @param token
	 * @return 根据token获取phone属性
	 * @throws Exception
	 */
	public  static String getPhone(String token) throws Exception{
		Claims claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
		Map map  = new HashMap();
		String phone = (String)claims.get("phone");
	    return phone;
	}

	/**验证token*/
	public static Object validToken(String token) throws Exception{
		long userId;
		try {
			userId = JWTTokenUtil.getUserId(token);
			if(StringUtils.isEmpty(userId)){
				return false;
			}
		} catch (Exception e) {
			return false;
		}	
		return true;
	}

	/*public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", "123456789");
		map.put("phone", "17638165524");
		//生成token
//		String token = JWTTokenUtil.createToken(map);
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJwaG9uZSI6IjE3NjM4MTY1NTI0IiwiZXhwIjoxNTY5MTIxNjY2LCJ1c2VySWQiOiIxMjM0NTY3ODkifQ.pezazlTo34V_VT-efUdMDNZWCPlabBjlpwRFR-rKFiQ4qkJGg7LmCDpWIlPk_U7kcfMkozoSTr_y3fTjfiYsrw";
//		String token = "eyJhbGciOiJIUzUxMiJ9.eyJwaG9uZSI6IjE3NjM4MTY1NTI0IiwiZXhwIjoxNTY5MTIyNzA4LCJ1c2VySWQiOiIxMjM0NTY3ODkifQ.uc68vTTeGR1MGkTKh40dAin7w00YmzCDwsZHWgh0KomC_yF_nZkBg2uD8OWEWdGMKvj6XIi8zNQzh-BLdQa9lQ";
		System.out.println("生成的TOKEN============="+token);

		//获取Claims 验证token
		Claims claims = JWTTokenUtil.parseClaims(token);

		System.out.println("验证Claims============="+claims);
	}*/
}
