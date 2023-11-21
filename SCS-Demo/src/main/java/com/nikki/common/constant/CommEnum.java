package com.nikki.common.constant;

/**
 * 全局枚举类型
 * 
 * @author xyb
 *
 */
public enum CommEnum {
    /**
     * 是或否状态 （0：否 1：是）
     */
    YN_STATUS("yesorno_status", "是或否状态"),
    YN_STATUS_NO("0", "否"),
    YN_STATUS_YES("1", "是"),

    /**
     * 删除状态 （0：未删除 1：删除）
     */
    DELETE_STATUS("delete_status", "删除状态"),
    DELETE_STATUS_NO("0", "未删除"),
    DELETE_STATUS_YES("1", "删除"),

    /**
     * 有效状态 （1：生效，0：无效）
     */
    VALID_STATUS("valid_status", "有效状态"),
	VALID_STATUS_YES("1", "有效"),
	VALID_STATUS_NO("0", "无效");

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典描述
     */
    private String msg;

    /**
     * 构造函数
     *
     * @param code 字典编码
     * @param msg  字典描述
     */
    private CommEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
