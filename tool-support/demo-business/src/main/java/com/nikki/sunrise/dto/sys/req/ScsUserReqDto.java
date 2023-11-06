/**
 * ScsUserReqDto.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.dto.sys.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户表
 * @author wangyanjun  2023-11-06
 */
@Data
@ApiModel(value = "用户表请求Dto")
public class ScsUserReqDto {
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名" )
    @NotEmpty(message = "姓名不能为空")
    @Length(max = 32, message = "姓名字段过长, 最大长度为32")
    private String name;

    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄" )
    private Integer age;

    /**
     * 性别（0：男，1：女）
     */
    @ApiModelProperty(value = "性别（0：男，1：女）" )
    private Boolean sex;

    /**
     * 数据更新时间
     * 默认值:CURRENT_TIMESTAMP
     */
    @ApiModelProperty(value = "数据更新时间" )
    @NotNull(message = "数据更新时间不能为空")
    private Date updateTime;

    /**
     * 数据有效标志(Y/N)
     */
    @ApiModelProperty(value = "数据有效标志(Y/N)" )
    @NotEmpty(message = "数据有效标志(Y/N)不能为空")
    @Length(max = 1, message = "数据有效标志(Y/N)字段过长, 最大长度为1")
    private String flag;
}