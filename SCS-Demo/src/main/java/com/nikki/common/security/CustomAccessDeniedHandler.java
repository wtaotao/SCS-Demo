/**
 * CustomAccessDeniedHandler.java
 * Created at 2020-03-20
 * Created by xieyingbin
 * Copyright (C) 2020 nikki, All rights reserved.
 */
package com.nikki.common.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.nikki.common.constant.AppConst;
import com.nikki.common.constant.ResponseCode;
import com.nikki.common.dto.ResponseData;


/**
 * 权限用户未授权资源处理
 * 
 * @author xieyingbin
 *
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 跨域设置
	 */
    @Autowired
    private CorsSetHandler corsSetHandler;

    @Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
			throws IOException {
        logger.info("Access denied handle: {} ", request.getRequestURI());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(AppConst.APPLICATION_JSON_UTF8);
        String json = JSONObject.toJSONString(new ResponseData<>(ResponseCode.UNAUTHORIZED));
        PrintWriter out = response.getWriter();
        out.append(json);
        this.corsSetHandler.setAllowOriginHeader(request, response);
        response.setStatus(HttpServletResponse.SC_OK);
	}
}