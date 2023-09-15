/**
 * anji-allways.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.anji.allways.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import com.nikki.demo.web.HelloController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author wangyanjun
 * @version $Id: SmokeTest.java, v 0.1 2017年8月1日 上午11:01:19 wangyanjun Exp $
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private HelloController controller;
    
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
}
