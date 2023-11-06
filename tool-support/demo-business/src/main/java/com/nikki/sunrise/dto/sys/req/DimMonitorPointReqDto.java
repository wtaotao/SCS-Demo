/**
 * DimMonitorPointReqDto.java
 * Created at 2023-11-06
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.sunrise.dto.sys.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 监控点维度表
 * @author wangyanjun  2023-11-06
 */
@Data
@ApiModel(value = "监控点维度表请求Dto")
public class DimMonitorPointReqDto {
    /**
     * 监控点名称
     */
    @ApiModelProperty(value = "监控点名称" )
    @NotEmpty(message = "监控点名称不能为空")
    @Length(max = 32, message = "监控点名称字段过长, 最大长度为32")
    private String monitorPointName;

    /**
     * 监控收费(天)
     */
    @ApiModelProperty(value = "监控收费(天)" )
    private BigDecimal monitorPointAmt;

    /**
     * 监控点风险等级
     */
    @ApiModelProperty(value = "监控点风险等级" )
    @NotEmpty(message = "监控点风险等级不能为空")
    @Length(max = 12, message = "监控点风险等级字段过长, 最大长度为12")
    private String monitorPointRiskGrade;

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