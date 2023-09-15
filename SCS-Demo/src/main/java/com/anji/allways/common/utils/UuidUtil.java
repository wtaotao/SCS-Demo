/**
 * UuidUtil.java
 * Created at 2021-08-26
 * Created by xieyingbin
 * Copyright (C) 2020 ANJI LOGISTICS, All rights reserved.
 */
package com.anji.allways.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

/**
 * 唯一ID工具类
 * 
 * @author xieyingbin
 *
 */
@Slf4j
public final class UuidUtil {
    /**
     *  获取UUID
     *
     * @return UUID
     */
    public static final String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取批次号（18位：格式为yyMMddhhmmssSSS + 3位随机数）
     * 
     * @return 批次号
     */
    public static final String getBatchNo() {
    	String timeStamp = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
        return timeStamp + String.format("%03d", new Random().nextInt(1000));
    }

//    public static void main(String[] args) {
//    	log.info("uuid {}", UuidUtil.getUuid());
//    	log.info("batch no {}", UuidUtil.getBatchNo());
//    }
}
