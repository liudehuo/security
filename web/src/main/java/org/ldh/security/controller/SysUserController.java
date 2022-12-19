package org.ldh.security.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.ldh.security.entity.SysUser;
import org.ldh.security.service.SysUserService;
import org.ldh.security.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
public class SysUserController {

    private static Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/getTokenInfo")
    public String getTokenInfo(String username) {
        SysUser sysUser = sysUserService.queryUser(username);
        Map<String,String> map = new HashMap<>();
        map.put("username",sysUser.getUsername());
        map.put("userId",sysUser.getId().toString());
        String token = JWTUtil.getToken(map);
        return token;
    }

    @RequestMapping(value = "/verifyToken",method = RequestMethod.POST)
    public Map<String,Object> verifyToken(String token) {
        Map<String,Object> map = new HashMap<>();
        try {
            DecodedJWT verify = JWTUtil.verify(token);
            map.put("status",200);
            map.put("message","请求成功");
            return map;
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("message","无效签名");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("message","token已过期");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("message","算法不正确");
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("message",500);
        return map;
    }

    @RequestMapping(value = "/admin-vue", method = RequestMethod.GET)
    public String adminVue() {
        System.out.println("@@@@@@@@");
        return "admin for vue";
    }

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
