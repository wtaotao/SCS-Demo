/**
 * ScsUserResDto.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.dto.sys.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @author wangyanjun  2023-11-06
 */
@Data
@ApiModel(value = "用户表响应Dto")
public class ScsUserResDto {
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号" )
    private Integer id;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名" )
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
    // @JSONField(format = DateUtil.DATETIMESHOWFORMAT)
    @ApiModelProperty(value = "数据更新时间" )
    private Date updateTime;

    /**
     * 数据有效标志(Y/N)
     */
    @ApiModelProperty(value = "数据有效标志(Y/N)" )
    private String flag;
}