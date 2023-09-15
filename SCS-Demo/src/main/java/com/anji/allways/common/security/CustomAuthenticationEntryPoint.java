/**
 * CustomAuthenticationEntryPoint.java
 * Created at 2020-03-20
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.anji.allways.common.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.anji.allways.common.constant.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.anji.allways.common.constant.AppConst;
import com.anji.allways.common.dto.ResponseData;

/**
 * 匿名用户访问认证入口点
 * 
 * @author xieyingbin
 *
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 跨域设置
     */
    @Autowired
    private CorsSetHandler corsSetHandler;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        logger.info("Unauthorized handle: {} ", request.getRequestURI());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(AppConst.APPLICATION_JSON_UTF8);
        String json = JSONObject.toJSONString(new ResponseData<>(ResponseCode.AUTH_FAILURES));
        PrintWriter out = response.getWriter();
        out.append(json);
        this.corsSetHandler.setAllowOriginHeader(request, response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

}