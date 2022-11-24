package org.ldh.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysUserController {

    private static Logger log = LoggerFactory.getLogger(SysUserController.class);

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Security";
    }

    @RequestMapping({"/","/index"})
    public String index() {
        return "index page";
    }

    @RequestMapping("/add")
    public String add() {
        return "add page";
    }

    @RequestMapping("/remove")
    public String remove() {
        return "remove page";
    }

    @RequestMapping("/update")
    public String update() {
        return "update page";
    }

    @RequestMapping("/bak")
    public String bak() {
        return "bak page";
    }
}
