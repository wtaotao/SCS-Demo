/**
 * JwtAuthToken.java
 * Created at 2020-03-20
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.anji.allways.common.security;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * JWT认证用Token类
 * 
 * @author xieyb
 *
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 1L;
	private final String token; // token
	private final Long userId; // 用户ID
	private final String account; // 用户账号

	public JwtAuthenticationToken(Long userId, String account, String token, List<GrantedAuthority> authorities) {
		super(authorities);
		this.userId = userId;
		this.account = account;
		this.token = token;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.token;
	}

	@Override
	public Object getPrincipal() {
		return this.userId;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

}
