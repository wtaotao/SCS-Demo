/**
 * anji-allways.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.nikki.demo.service;

import org.springframework.stereotype.Service;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: GreetingService.java, v 0.1 2017年8月2日 上午9:27:32 wangyanjun Exp $
 */
@Service
public class GreetingService {

    public String greet() {
        return "Nice to meet you!";
    }
}
