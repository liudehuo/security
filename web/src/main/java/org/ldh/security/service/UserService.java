package org.ldh.security.service;

import org.ldh.security.entity.SysUser;

public interface UserService {

    SysUser queryUser(String username);

}
