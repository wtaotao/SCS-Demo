/**
 * VerifyCodeException.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.nikki.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常严重
 * 
 * @author xieyingbin
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
