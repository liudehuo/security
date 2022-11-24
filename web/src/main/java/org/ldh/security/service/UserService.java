package org.ldh.security.service;

import org.ldh.security.entity.User;

public interface UserService {

    User queryUser(String username);

}
