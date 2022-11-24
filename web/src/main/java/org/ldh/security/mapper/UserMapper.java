package org.ldh.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ldh.security.entity.SysUser;

@Mapper
public interface UserMapper {

    SysUser selectUser(String username);


}
