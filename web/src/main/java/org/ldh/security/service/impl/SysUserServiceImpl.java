package org.ldh.security.service.impl;

import org.ldh.security.controller.SysUserController;
import org.ldh.security.entity.SysUser;
import org.ldh.security.mapper.SysUserMapper;
import org.ldh.security.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义用户信息实现类
 */
@Service("userDetailsService")
public class SysUserServiceImpl implements SysUserService, UserDetailsService {

    private static Logger log = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        SysUser sysUser = queryUser(username);
        if (sysUser == null) {
            // 认证失败
            throw new UsernameNotFoundException("该用户不存在");
        }
        List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList("CRUD,ROLE_manager");
        return new User(sysUser.getUsername(),new BCryptPasswordEncoder().encode(sysUser.getPassword()),auth);
    }

    @Override
    public SysUser queryUser(String username) {
        return sysUserMapper.selectUser(username);
    }

}
