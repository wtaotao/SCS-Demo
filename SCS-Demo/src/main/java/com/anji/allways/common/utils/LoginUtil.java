/**
 * LoginUtil.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.anji.allways.common.utils;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.anji.allways.common.security.JwtAuthenticationToken;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户登录工具类
 * 
 * @author xieyingbin
 *
 */
@Slf4j
public class LoginUtil {
	/**
	 * 系统管理员账号
	 */
	public static final List<String> ADMIN_ACCOUNT = Arrays.asList("admin", "administrator", "root", "sys", "system");

	/**
	 * 用户账号请求参数名
	 */
	public static final String USERNAME_KEY = "username";
	
	/**
	 * 用户密码请求参数名
	 */
	public static final String PASSWORD_KEY = "password";
	
    /**
     * 客户端类型
     */
	public static final String CLIENT_TYPE = "client-type";

    /**
     * 请求代理
     */
	public static final String USER_AGENT = "user-agent";

    /**
     * 请求IP地址
     */
	public static final String X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * 登录结果成功
     */
	public static final String LOGIN_SUCCESS = "success";

    /**
     * 登录结果失败
     */
	public static final String LOGIN_FAIL = "fail";

    /**
     * 账号冻结的登录失败次数
     */
	public static final int LOCK_COUNT = 5;

    /**
     * 登录失败冻结时间（秒）
     */
	public static final int LOCK_TIME = 30 * 60;

    /**
     * 获取登录用户id.
     *
     * @return 登录用户id
     */
    public static Long getLoginUserId() {
    	try {
        	JwtAuthenticationToken jwtAuthenticationToken = null;
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        	if (authentication == null) {
        		return -1L;
        	}
        	if (authentication instanceof JwtAuthenticationToken) {
        		jwtAuthenticationToken = (JwtAuthenticationToken)authentication;
            	return Long.valueOf(jwtAuthenticationToken.getPrincipal().toString());
        	}
    	} catch (Exception ex) {
    		log.error(ex.getMessage(), ex);
    	}
    	return -1L;
    }
    
    /**
     * 获取登录用户账号.
     *
     * @return 登录用户账号
     */
    public static String getLoginAccount() {
    	try {
        	JwtAuthenticationToken jwtAuthenticationToken = null;
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        	if (authentication == null) {
        		return "";
        	}
        	if (authentication instanceof JwtAuthenticationToken) {
        		jwtAuthenticationToken = (JwtAuthenticationToken)authentication;
            	return jwtAuthenticationToken.getAccount();
        	}
    	} catch (Exception ex) {
    		log.error(ex.getMessage(), ex);
    	}
    	return "";
    }

    /**
     * 判断登录用户是否系统管理员
     * 
     * @return true: 是  false: 否
     */
    public static boolean isLoginAdmin() {
    	String loginAccount = getLoginAccount();
    	for (String acc : ADMIN_ACCOUNT) {
    		if (acc.equalsIgnoreCase(loginAccount)) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * 判断账号是否系统管理员
     * 
     * @param account 账号
     * @return true: 是  false: 否
     */
    public static boolean isAdminAccount(String account) {
    	for (String acc : ADMIN_ACCOUNT) {
    		if (acc.equalsIgnoreCase(account)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * 获取请求IP地址信息
     *
     * @param request 请求对象
     * @return 获取请求IP地址
     */
    public static String getRemoteIp(HttpServletRequest request) {
        String xff = request.getHeader(X_FORWARDED_FOR);
        if (StringUtils.isNotBlank(xff)) {
            String[] ips = xff.split(",");
            return ips[0];
        } else {
            return request.getRemoteAddr();
        }
    }
}
