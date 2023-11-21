/**
 * VerifyCodeException.java
 * Created at 2021-08-26
 * Created by xyb
 * Copyright (C) 2020 nikki, All rights reserved.
 */
package com.nikki.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常严重
 * 
 * @author xyb
 *
 */
public class VerifyCodeException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9153672838410206084L;

	public VerifyCodeException(String msg) {
		super(msg);
	}
}
