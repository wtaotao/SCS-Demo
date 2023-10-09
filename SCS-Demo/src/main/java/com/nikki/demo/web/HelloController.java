/**
 * nikki.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.nikki.demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *
 * </pre>
 * @author wangyanjun
 * @version $Id: HelloController.java, v 0.1 2017年7月31日 上午11:13:54 wangyanjun Exp $
 */
@RestController
public class HelloController {
    
    @RequestMapping("/") 
    public String root() {
        return "This is the root page!";
    }

    @RequestMapping("/hello")
    public String index() {
        return "Hello World!";
    }
}
