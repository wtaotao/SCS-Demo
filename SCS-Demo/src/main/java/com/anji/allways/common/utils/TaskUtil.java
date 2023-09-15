/**
 * TaskUtil.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.anji.allways.common.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.anji.allways.common.dto.TaskData;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务（异步）工具类
 * 
 * @author xieyingbin
 *
 */
@Slf4j
public class TaskUtil {
	/**
	 * 任务Id前缀
	 */
	private static final String TASK_ID_PREFIX = "TASK-";

    /**
     * 任务Id线程缓存对象
     */
    private static ThreadLocal<String> taskIdCache = new ThreadLocal<>();

    /**
     * 生成任务Id
     *
     * @return 任务Id
     */
    public static String generateTaskId() {
        return TASK_ID_PREFIX + UuidUtil.getUuid();
    }

    /**
     * 获取任务Id
     *
     * @return 任务Id
     */
    public static String getTaskId() {
        return taskIdCache.get();
    }

    /**
     * 设置任务Id
     *
     * @param taskId 任务Id
     */
    public static void setTaskId(String taskId) {
    	taskIdCache.set(taskId);
    }

    /**
     * 保存任务信息
     *
     * @param value 任务对象
     */
    public static boolean saveTask(TaskData value) {
    	String taskId = getTaskId();
    	if (StringUtils.isBlank(taskId)) {
    		return false;
    	}
    	return RedisUtil.set(taskId, value, 5 * 60); // 过期时间5分钟
    }

    /**
     * 获取任务信息
     * 
     * @return 任务对象
     */
    public static TaskData getTask() {
    	String taskId = getTaskId();
    	if (StringUtils.isBlank(taskId)) {
    		return null;
    	}
    	Object obj = RedisUtil.get(taskId);
    	if (obj != null) {
    		JSONObject jsonObj = (JSONObject)obj;
    		TaskData taskData = jsonObj.toJavaObject(TaskData.class);
    		return taskData;
    	} else {
    		return null;
    	}
    }

    /**
     * 获取任务信息
     * 
     * @param taskId 任务Id
     * @return 任务对象
     */
    public static TaskData getTask(String taskId) {
    	if (StringUtils.isBlank(taskId)) {
    		return null;
    	}
    	Object obj = RedisUtil.get(taskId);
    	if (obj != null) {
    		JSONObject jsonObj = (JSONObject)obj;
    		TaskData taskData = jsonObj.toJavaObject(TaskData.class);
    		return taskData;
    	} else {
    		return null;
    	}
    }

	/**
	 * 记录任务信息
	 * 
	 * @param message 异常消息
	 */
    public static void recordData(TaskData taskData) {
		log.info("Method recordData. taskData={}", taskData);
		// 任务信息
		if (taskData != null) {
			saveTask(taskData);
		}
	}

	/**
	 * 记录任务异常信息
	 * 
	 * @param message 异常消息
	 */
    public static void recordException(String message) {
		log.info("Method recordException. message={}", message);
		// 任务异常信息
		TaskData taskData = getTask();
		if (taskData != null) {
			taskData.exception(message);
			saveTask(taskData);
		}
	}

	/**
	 * 记录任务正常结束信息
	 * 
	 * @param message 正常结束信息
	 */
    public static void recordComplete(String message) {
		log.info("Method recordComplete. message={}", message);
		// 任务正常结束信息
		TaskData taskData = getTask();
		if (taskData != null) {
			taskData.complete(message);
			saveTask(taskData);
		}
	}

	/**
	 * 记录任务执行信息
	 * 
	 * @param taskPhase 执行阶段信息
	 * @param targetCount 目标数量
	 * @param completeCount 完成数量
	 */
    public static void recordRunning(String taskPhase, int targetCount, int completeCount) {
		log.info("Method recordRunning. taskPhase={}, targetCount={}, completeCount={}", taskPhase, targetCount, completeCount);
		// 任务执行信息
		TaskData taskData = getTask();
		if (taskData != null) {
			taskData.running(taskPhase, targetCount, completeCount);
			saveTask(taskData);
		}
	}

	/**
	 * 增加当前任务完成数量
	 * 
	 * @param incrementCount 完成增量
	 */
    public static void recordRunning(int incrementCount) {
		log.info("Method recordRunning. incrementCount={}", incrementCount);
		// 任务执行信息
		TaskData taskData = getTask();
		if (taskData != null) {
			taskData.setPhaseCompleteCount(taskData.getPhaseCompleteCount() + incrementCount);
			taskData.running(taskData.getTaskPhase(), taskData.getPhaseTargetCount(), taskData.getPhaseCompleteCount());
			saveTask(taskData);
		}
	}

}
