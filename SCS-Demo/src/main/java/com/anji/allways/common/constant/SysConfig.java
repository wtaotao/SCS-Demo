/**
 * SysConfig.java
 * Created at 2021-12-05
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.anji.allways.common.constant;

/**
 * 系统配置
 *
 * @author xieyingbin
 */
public class SysConfig {

    /**
     * 密码过期功能开关（0：关闭，1：开启）
     */
	public static final String PWD_EXPIRE_SW = "pwd_expire_sw";

    /**
     * 密码过期天数
     */
	public static final String PWD_EXPIRE_DAYS = "pwd_expire_days";

    /**
     * 密码不重复功能开关（0：关闭，1：开启）
     */
	public static final String PWD_NO_REPEAT_SW = "pwd_no_repeat_sw";

    /**
     * 密码连续重复次数
     */
	public static final String PWD_NO_REPEAT_TIMES = "pwd_no_repeat_times";

    /**
     * 账号过期功能开关（0：关闭，1：开启）
     */
	public static final String ACCOUNT_EXPIRE_SW = "account_expire_sw";

    /**
     * 账号过期天数
     */
	public static final String ACCOUNT_EXPIRE_DAYS = "account_expire_days";

}
