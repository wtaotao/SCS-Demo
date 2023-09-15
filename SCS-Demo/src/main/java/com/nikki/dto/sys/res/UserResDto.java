/**
 * anji-allways.com Inc.
 * Copyright (c) 2022-2023 All Rights Reserved.
 */
package com.nikki.dto.sys.res;

import java.sql.Date;

import lombok.Data;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: UserResDto.java, v 0.1 2023年9月15日 下午4:19:06 wangyanjun Exp $
 */
@Data
public class UserResDto {
    /**
     * 用户编号
     */
    private Integer id;
    
    /**
     * 用户姓名
     */
    private String name;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 性别（0：男，1：女）
     */
    private Integer sex;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 数据是否有效(Y/N)
     */
    private String flag;
}
