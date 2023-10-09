/**
 * CustomUserDetails.java
 * Created at 2020-03-20
 * Created by xieyingbin
 * Copyright (C) 2020 nikki, All rights reserved.
 */
package com.nikki.common.security;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 登录用户认证详细信息，包括用户权限集合。
 * 
 * @author xieyingbin
 *
 */
public class CustomUserDetails implements UserDetails {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = 1L;
	private String userId = null;      // PK_ID
	private String username = null;    // 登录账号
	private String displayName = null; // 展示姓名
	private String password = null;    // 登录密码
	private boolean accountNonExpired = false;     // 账号过期标志
	private boolean accountNonLocked = false;      // 账号锁定标志
	private boolean credentialsNonExpired = false; // 密码过期标志
	private boolean enabled = false;          // 账号可用标志
	private boolean isFirstLogin = false;     // 是否首次登录
	private int credentialsExpireDays = -1;   // 密码即将过期天数

	private Collection<? extends GrantedAuthority> authorities = null; // 权限集合
	private List<Long> roleIds = null; // 角色Id集合

	public CustomUserDetails(String userId, String account, String password, Collection<? extends GrantedAuthority> authorities, List<Long> roleIds) {
		this(userId, account, password, "", true, true, true, true, false, -1, authorities, roleIds);
	}

	public CustomUserDetails(String userId, String username, String password, String displayName, boolean enabled, boolean accountNonExpired,
                               boolean credentialsNonExpired, boolean accountNonLocked, boolean isFirstLogin, int credentialsExpireDays,
                               Collection<? extends GrantedAuthority> authorities, List<Long> roleIds) {
		if (username != null && !"".equals(username) ) {
			logger.info("Provides core user information.");
			this.userId = userId;
			this.username = username;
			this.displayName = displayName;
			this.password = password;
			this.enabled = enabled;
			this.accountNonExpired = accountNonExpired;
			this.accountNonLocked = accountNonLocked;
			this.credentialsNonExpired = credentialsNonExpired;
			this.isFirstLogin = isFirstLogin;
			this.credentialsExpireDays = credentialsExpireDays;
			this.authorities = authorities;
			this.roleIds = roleIds;
		} else {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
	}

	public boolean equals(Object rhs) {
		return rhs instanceof CustomUserDetails
				&& this.getUsername().equals(((CustomUserDetails) rhs).getUsername());
	}

	public int hashCode() {
		return this.getUsername().hashCode();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the isFirstLogin
	 */
	public boolean isFirstLogin() {
		return isFirstLogin;
	}
	
	/**
	 * @return credentialsExpireDays
	 */
	public int getCredentialsExpireDays() {
		return credentialsExpireDays;
	}

	/**
	 * 擦除密码信息
	 */
	public void erasePassword() {
		this.password = "";
	}

	/**
	 * @return
	 */
	public List<Long> getRoleIds() {
		return roleIds;
	}
}



