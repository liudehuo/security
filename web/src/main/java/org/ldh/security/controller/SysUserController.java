package org.ldh.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class SysUserController {

    private static Logger log = LoggerFactory.getLogger(SysUserController.class);

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Security";
    }

    @RequestMapping({"/","/index"})
    public String index() {
        return "index";
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

    /**
     * 用户具有指定角色才能访问
     * @return
     */
    @Secured({"ROLE_user","ROLE_admin"})
    @RequestMapping("/test-secured")
    public String testSecured() {
        return "Secured";
    }

    /**
     * 用户具有指定权限才能访问
     * @return
     */
    @PreAuthorize("hasAnyAuthority('CRUD')")
    @RequestMapping("/pre-authorize")
    public String preAuthorize() {
        return "PreAuthorize";
    }

    /**
     * 在方式执行之后进行权限校验
     * @return
     */
    @PostAuthorize("hasAnyAuthority('teacher')")
    @RequestMapping("/teacher")
    public String teacher() {
        log.info("teacher executed");
        return "teacher";
    }

    // 进入控制器之前对数据进行过滤
    // @PreFilter("score>=90") //控制器最终拿到被过滤后的数据
    // 对方法返回的数据进行过滤
    // @PostFilter("username=='user1'") //返回满足条件的数据
}
