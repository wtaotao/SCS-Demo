/**
 * anji-allways.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.nikki.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nikki.demo.service.GreetingService;

/**
 * <pre>
 *
 * </pre>
 * @author wangyanjun
 * @version $Id: GreetingController.java, v 0.1 2017年8月2日 上午9:26:11 wangyanjun Exp $
 */
@Controller
public class GreetingController {

    private final GreetingService service;

    public GreetingController(GreetingService service) {
        this.service = service;
    }

    @RequestMapping("/greeting")
    public @ResponseBody String greeting() {
        return service.greet();
    }

}
