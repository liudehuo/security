package org.ldh.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ldh.security.entity.SysUser;
import org.springframework.stereotype.Repository;

@Mapper//mybatis
@Repository//spring
public interface SysUserMapper {

    SysUser selectUser(String username);

}
