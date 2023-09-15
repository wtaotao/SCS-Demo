/**
 * OauthAuthenticationToken.java
 * Created at 2021-09-14
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.anji.allways.common.security;

import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * OAuth认证用Token类
 * 
 * @author xieyb
 *
 */
public class OauthAuthenticationToken extends AbstractAuthenticationToken {
	private static final long serialVersionUID = 1L;
	private final String accessToken; // token
	private final String appName; // 应用名

	public OauthAuthenticationToken(String appName, String token, List<GrantedAuthority> authorities) {
		super(authorities);
		this.appName = appName;
		this.accessToken = token;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return this.accessToken;
	}

	@Override
	public Object getPrincipal() {
		return this.appName;
	}

}
