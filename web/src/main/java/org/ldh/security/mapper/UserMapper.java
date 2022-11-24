package org.ldh.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.ldh.security.entity.User;

@Mapper
public interface UserMapper {

    User selectUser(String username);


}
