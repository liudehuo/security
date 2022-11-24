package org.ldh.security.service;

import org.ldh.security.entity.SysUser;

public interface SysUserService {

    SysUser queryUser(String username);

}
