/**
 * SnowflakeId.java
 * Created at 2021-09-01
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.nikki.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

/**
 * 雪花算法ID工具类
 * 
 * @author xieyingbin
 *
 */
@Slf4j
public class SnowflakeId {
	/**
	 * 开始时间截 (建议用服务第一次上线的时间，到毫秒级的时间戳) 
	 */
    private static final long twepoch = 1630425600000L; // 2021-09-01 00:00:00

    /**
     * 机器id位数 (10位)
     */
    private static final long workerIdBits = 10L;

    /** 
     * 最大机器id (0-1023)
     */
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 
     * 序列位数 (12位)
     */
    private static final long sequenceBits = 12L;

    /** 
     * 机器ID向左移位数
     */
    private static final long workerIdShift = sequenceBits;

    /** 
     * 时间截向左移位数(10+12=22) 
     */
    private static final long timestampLeftShift = sequenceBits + workerIdBits;

    /** 
     * 生成序列的掩码 (0b111111111111=0xfff=4095)
     */
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 
     * 工作机器ID (0~1023) 
     */
    private static long workerId;

    /** 
     * 毫秒内序列(0~4095) 
     */
    private static long sequence = 0L;

    /** 
     * 上次生成ID的时间截 
     */
    private static long lastTimestamp = -1L;

    /**
     * 初始随机机器ID，如果多实例应用有要求算法ID全局唯一，需要启动时做唯一获取处理。
     */
    static {
    	workerId = new Random().nextInt((int)maxWorkerId);
    	log.info("Initialized workerId {}", workerId);
    }

    /**
     * 设置WorkId
     * @param pWorkerId 工作ID (0~1023)
     */
    public static void setWorkerId(long pWorkerId) {
        if (pWorkerId > maxWorkerId || pWorkerId < 0) {
        	String msg = "workerId can't be greater than %d or less than 0";
            throw new IllegalArgumentException(String.format(msg, maxWorkerId));
        }
        workerId = pWorkerId;
    }

    /**
     * 获取WorkId
     * @param 当前WorkId
     */
    public static long getWorkerId() {
        return workerId;
    }

    /**
     * 获得ID (线程安全)
     * @return 雪花算法Id
     */
    public synchronized static long getSfid() {
        long timestamp = timeGen();
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
        	String msg = "Clock moved backwards.  Refusing to generate id for %d milliseconds";
            throw new RuntimeException(String.format(msg, lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            //如果毫秒相同，则从0递增生成序列号
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = untilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) 
                | (workerId << workerIdShift) 
                | sequence;
    }

    /**
     * 获得批量ID (线程安全)
     * @return 雪花算法Id列表
     */
    public synchronized static List<Long> getSfidList(int count) {
    	List<Long> idList = new ArrayList<>(count);
    	for (int i = 0; i < count; i++) {
    		idList.add(getSfid());
    	}
    	return idList;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private static long untilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回当前时间 (从1970-01-01 08:00:00算起)
     * @return 当前时间(毫秒)
     */
    private static long timeGen() {
        return System.currentTimeMillis();
    }
    
    public static void main(String[] args) {
    	log.info("{}", SnowflakeId.getSfid());
    }
}
