/**
 * DimMonitorPointEntity.java
 * Created at 2022-11-16
 * Created by wangyanjun
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 **/
package com.anji.allways.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 监控点维度表
 * @author wangyanjun  2022-11-16
 */
public class DimMonitorPointEntity implements Serializable {
    /**
     * 监控点编号
     */
    private Integer monitorPointId;

    /**
     * 监控点名称
     */
    private String monitorPointName;

    /**
     * 监控收费(天)
     */
    private BigDecimal monitorPointAmt;

    /**
     * 监控点风险等级
     */
    private String monitorPointRiskGrade;

    /**
     * 数据更新时间
     * 默认值:CURRENT_TIMESTAMP
     */
    private Date updateTime;

    /**
     * 数据有效标志(Y/N)
     */
    private String flag;

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回字段:监控点编号
     */
    public Integer getMonitorPointId() {
        return monitorPointId;
    }

    /**
     * 设置字段值:监控点编号
     */
    public void setMonitorPointId(Integer monitorPointId) {
        this.monitorPointId = monitorPointId;
    }

    /**
     * 返回字段:监控点名称
     */
    public String getMonitorPointName() {
        return monitorPointName;
    }

    /**
     * 设置字段值:监控点名称
     */
    public void setMonitorPointName(String monitorPointName) {
        this.monitorPointName = monitorPointName == null ? null : monitorPointName.trim();
    }

    /**
     * 返回字段:监控收费(天)
     */
    public BigDecimal getMonitorPointAmt() {
        return monitorPointAmt;
    }

    /**
     * 设置字段值:监控收费(天)
     */
    public void setMonitorPointAmt(BigDecimal monitorPointAmt) {
        this.monitorPointAmt = monitorPointAmt;
    }

    /**
     * 返回字段:监控点风险等级
     */
    public String getMonitorPointRiskGrade() {
        return monitorPointRiskGrade;
    }

    /**
     * 设置字段值:监控点风险等级
     */
    public void setMonitorPointRiskGrade(String monitorPointRiskGrade) {
        this.monitorPointRiskGrade = monitorPointRiskGrade == null ? null : monitorPointRiskGrade.trim();
    }

    /**
     * 返回字段:数据更新时间
     * 默认值:CURRENT_TIMESTAMP
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置字段值:数据更新时间
     * 默认值:CURRENT_TIMESTAMP
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 返回字段:数据有效标志(Y/N)
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置字段值:数据有效标志(Y/N)
     */
    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    /**
     * toString 
     * @return java.lang.String 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", monitorPointId=").append(monitorPointId);
        sb.append(", monitorPointName=").append(monitorPointName);
        sb.append(", monitorPointAmt=").append(monitorPointAmt);
        sb.append(", monitorPointRiskGrade=").append(monitorPointRiskGrade);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", flag=").append(flag);
        sb.append("]");
        return sb.toString();
    }
}