/**
 * TaskData.java
 * Created at 2020-12-18
 * Created by xyb
 * Copyright (C) 2020 nikki, All rights reserved.
 **/
package com.nikki.common.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 任务数据
 * 
 * @author xyb 2020-03-28
 */
@Data
@ApiModel(value = "任务数据")
public class TaskData {

	/**
	 * 任务状态：执行中
	 */
	public static final String STATUS_RUNNING = "running";

	/**
	 * 任务状态：已完成
	 */
	public static final String STATUS_COMPLETED = "completed";

	/**
	 * 任务状态：异常
	 */
	public static final String STATUS_EXCEPTION = "exception";

    /**
     * 任务Id
     */
    @ApiModelProperty(value = "任务Id", position = 0)
    private String taskId;

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态（执行中：running, 已完成：completed, 异常：exception）", position = 1)
    private String status;

    /**
     * 执行消息
     */
    @ApiModelProperty(value = "执行消息", position = 2)
    private String message;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间", position = 3)
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间", position = 4)
    private Date endTime;

    /**
     * 执行时间
     */
    @ApiModelProperty(value = "执行时间（秒）", position = 5)
    private Long seconds;

    /**
     * 任务阶段
     */
    @ApiModelProperty(value = "任务阶段", position = 6)
    private String taskPhase;

    /**
     * 阶段目标数量
     */
    @ApiModelProperty(value = "阶段目标数量", position = 7)
    private Integer phaseTargetCount;

    /**
     * 阶段完成数量
     */
    @ApiModelProperty(value = "阶段完成数量", position = 8)
    private Integer phaseCompleteCount;

    /**
     * 任务开始
     * 
     * @param taskId
     */
    public void start(String taskId) {
    	this.taskId = taskId;
    	this.status = STATUS_RUNNING;
    	this.startTime = new Date();
    	this.endTime = null;
    	this.seconds = 0L;
    	this.taskPhase = "";
    	this.phaseTargetCount = 0;
    	this.phaseCompleteCount = 0;
    }

    /**
     * 任务执行中
     * 
     * @param taskPhase 任务阶段
     * @param targetCount 目标数量
     */
    public void running(String taskPhase, int targetCount, int completeCount) {
    	Date now = new Date();
    	this.status = STATUS_RUNNING;
    	this.endTime = null;
    	this.seconds = (now.getTime() - this.startTime.getTime()) / 1000;
    	this.taskPhase = taskPhase;
    	this.phaseTargetCount = targetCount;
    	this.phaseCompleteCount = completeCount;
    }

    /**
     * 任务完成
     * 
     * @param message 任务消息
     */
    public void complete(String message) {
    	Date now = new Date();
    	this.status = STATUS_COMPLETED;
    	this.message = message;
    	this.endTime = now;
    	this.seconds = (now.getTime() - this.startTime.getTime()) / 1000;
    	this.taskPhase = "";
    	this.phaseTargetCount = 0;
    	this.phaseCompleteCount = 0;
    }

    /**
     * 任务异常
     * 
     * @param message 任务消息
     */
    public void exception(String message) {
    	Date now = new Date();
    	this.status = STATUS_EXCEPTION;
    	this.message = message;
    	this.endTime = now;
    	this.seconds = (now.getTime() - this.startTime.getTime()) / 1000;
    	this.taskPhase = "";
    	this.phaseTargetCount = 0;
    	this.phaseCompleteCount = 0;
    }
}
