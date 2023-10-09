/**
 * CorsSetHandler.java
 * Created at 2021-09-10
 * Created by xieyingbin
 * Copyright (C) 2020 nikki, All rights reserved.
 **/
package com.nikki.common.security;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * 设置跨域信息
 * 
 * @author xieyingbin 2021-09-10
 */
@Component
@Slf4j
public class CorsSetHandler {

	/**
	 * 跨域网站信息
	 */
	@Value("${cors.allowedOrigins:*}")
	private String allowedOrigins;

	/**
	 * 表示是否允许发送Cookie
	 */
	@Value("${cors.allowCredentials:false}")
	private Boolean allowCredentials;

	/**
	 * 允许的请求头
	 */
	@Value("${cors.allowedHeaders:*}")
	private String allowedHeaders;

	/**
	 * 允许请求的方法
	 */
	@Value("${cors.allowedMethods:*}")
	private String allowedMethods;

	/**
	 * 允许响应暴露的头信息
	 */
	@Value("${cors.exposedHeaders:*}")
	private String exposedHeaders;

	/**
	 * 用来指定预检请求的有效期，单位为秒
	 */
	@Value("${cors.maxAge:1800}")
	private Long maxAge;

	/**
	 * 星号常量
	 */
	private static String ASTERISK = "*";

	/**
	 * 设置跨域信息
	 * 
	 * @param config
	 */
	public void setCorsConfiguration(CorsConfiguration config) {
		log.info(
				"请求跨域设置【allowedOrigins={},allowCredentials={},allowedHeaders={},allowedMethods={},exposedHeaders={},maxAge={}】",
				allowedOrigins, allowCredentials, allowedHeaders, allowedMethods, exposedHeaders, maxAge);
		if (ASTERISK.equals(allowedOrigins)) {
			config.addAllowedOrigin(allowedOrigins);
		} else {
			config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
		}
		if (ASTERISK.equals(allowedMethods)) {
			config.addAllowedMethod(allowedMethods);
		} else {
			config.setAllowedMethods(Arrays.asList(allowedMethods.split(",")));
		}
		if (ASTERISK.equals(allowedHeaders)) {
			config.addAllowedHeader(allowedHeaders);
		} else {
			config.setAllowedHeaders(Arrays.asList(allowedHeaders.split(",")));
		}

		if (!ASTERISK.equals(exposedHeaders)) {
			config.setExposedHeaders(Arrays.asList(exposedHeaders.split(",")));
		}
		config.setAllowCredentials(allowCredentials);
		config.setMaxAge(maxAge);
	}

	/**
	 * 设置跨域信息
	 *
	 * @param request
	 * @param response
	 */
	public void setAllowOriginHeader(HttpServletRequest request, HttpServletResponse response) {
		String origin = request.getHeader(HttpHeaders.ORIGIN);
		if (StringUtils.isEmpty(origin)) {
			return;
		}
		if (ASTERISK.equals(allowedOrigins)) {
			response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, allowedOrigins);
		} else {
			List<String> list = Arrays.asList(allowedOrigins.split(","));
			for (String check : list) {
				if (origin.startsWith(check)) {
					response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
					break;
				}
			}
		}
	}
}
