/**
 * DimMonitorPointResDto.java
 * Created at 2022-11-16
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.nikki.dto.sys.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 监控点维度表
 * @author wangyanjun  2022-11-16
 */
@Data
@ApiModel(value = "监控点维度表响应Dto")
public class DimMonitorPointResDto {
    /**
     * 监控点编号
     */
    @ApiModelProperty(value = "监控点编号" )
    private Integer monitorPointId;

    /**
     * 监控点名称
     */
    @ApiModelProperty(value = "监控点名称" )
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
    private String monitorPointRiskGrade;

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