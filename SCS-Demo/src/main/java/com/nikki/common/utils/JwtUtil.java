/**
 * JwtUtil.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.nikki.common.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT工具类
 * 
 * @author xieyb
 *
 */
@Slf4j
public class JwtUtil {
	/**
	 * 默认过期时间30分钟
	 */
	public static final long DEFAULT_EXPIRE_TIME = 30 * 60 * 1000;

	/**
	 * 附加声明名称
	 */
	public static final String CLAIM_NAME = "login_info";
	
	/**
	 * 用户id
	 */
	public static final String USER_ID = "userid";
	
	/**
	 * 用户名
	 */
	public static final String USER_ACCOUNT = "account";

	/**
	 * 用户角色编码
	 */
	public static final String USER_ROLE_CODES = "roleCodes";

	/**
	 * 用户角色ID
	 */
	public static final String USER_ROLE_IDS = "roleIds";

	/**
	 * 登录用户返回JWT的key信息
	 */
	public static final String JWT_KEY = "jwt";

	/**
	 * 默认密钥（安全性考虑，推荐从配置文件中获取。）
	 */
	public static final String DEFAULT_SECRET = "b2e3f6e84db64986bbbf7ae2f48bef38";

	/**
	 * 验证类
	 */
	private static JWTVerifier verifier = JWT.require(Algorithm.HMAC256(DEFAULT_SECRET)).build();

	/**
	 * 生成签名，30min后过期。
	 *
	 * @param claims 私有声明
	 * @return 加密的token
	 */
	public static String sign(Map<String, ?> claims) {
		Date date = new Date(System.currentTimeMillis() + DEFAULT_EXPIRE_TIME);
		Algorithm algorithm = Algorithm.HMAC256(DEFAULT_SECRET);
		Map<String, Object> claimHeader = new HashMap<>();
		claimHeader.put("time", System.currentTimeMillis());
		return JWT.create().withHeader(claimHeader).withClaim(CLAIM_NAME, claims).withExpiresAt(date).sign(algorithm);
	}

	/**
	 * 验证签名
	 * 
	 * @param token 加密token
	 * @return jwt
	 */
	public static DecodedJWT verify(String token) {
		try {
			//Algorithm algorithm = Algorithm.HMAC256(DEFAULT_SECRET);
			//JWTVerifier verifier = JWT.require(algorithm).build();
			return verifier.verify(token);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * 获取登录信息Map
	 * 
	 * @param token 加密token
	 * @return 用户信息Map
	 */
	public static Map<String, ?> getLoginInfoMap(String token) {
		try {
			DecodedJWT jwt = verify(token);
			if (jwt == null) {
				return null;
			}

			Claim claim = jwt.getClaim(CLAIM_NAME);
			return claim.asMap();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * 获取登录信息
	 * 
	 * @param token 加密token
	 * @return 用户信息
	 */
	public static LoginInfo getLoginInfo(String token) {
		LoginInfo loginInfo = new LoginInfo();
		Map<String, ?> map = getLoginInfoMap(token);
		if (map == null) {
			log.error("JWT登录信息异常");
			// 异常登录
			loginInfo.setUserId(-1L); 
			loginInfo.setUserAccount("");
			loginInfo.setRoleCodeList(new ArrayList<>());
			loginInfo.setRoleIdList(new ArrayList<>());
			return loginInfo;
		}

		// 用户ID
		try {
			loginInfo.setUserId(Long.valueOf(String.valueOf(map.get(USER_ID))));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			loginInfo.setUserId(-1L); 
		}

		// 登录账号
		loginInfo.setUserAccount(String.valueOf(map.get(USER_ACCOUNT)));

		// 角色编码
		List<String> roleCodeList = new ArrayList<>();
		Object obj1 = map.get(USER_ROLE_CODES);
		if (obj1 != null && obj1 instanceof ArrayList<?>) {
			for (Object o : (List<?>) obj1) {
				if (o != null) {
					roleCodeList.add(String.valueOf(o));
				}
	        }
		}
		loginInfo.setRoleCodeList(roleCodeList);

		// 角色ID
		List<Long> roleIdList = new ArrayList<>();
		Object obj2 = map.get(USER_ROLE_IDS);
		if (obj2 != null && obj2 instanceof ArrayList<?>) {
			for (Object o : (List<?>) obj2) {
				if (o != null) {
					try {
						roleIdList.add(Long.valueOf(String.valueOf(o)));
					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
					}
				}
	        }
		}
		loginInfo.setRoleIdList(roleIdList);
		return loginInfo;
	}

	/**
	 * JWT中的用户登录信息
	 * 
	 * @author xieyb
	 *
	 */
	@Data
	public static class LoginInfo {
		private Long userId;
		private String userAccount;
		private List<String> roleCodeList;
		private List<Long> roleIdList;
	}

//	public static void main(String[] args) {
//		Map<String, Object> claimMap = new HashMap<>();
//		claimMap.put(USER_ID, "test1");
//		claimMap.put(USER_ACCOUNT, "xieyingbin");
//		// 用户角色
//		List<String> roles = new ArrayList<>(Arrays.asList("role1", "role2"));
//		claimMap.put(USER_ROLE_CODES, roles);
//		List<Long> roleIds = new ArrayList<>(Arrays.asList(1234L, 3456L));
//		claimMap.put(USER_ROLE_IDS, roleIds);
//
//		String jwt = JwtUtil.sign(claimMap);
//		System.out.println(jwt);
//	}
}