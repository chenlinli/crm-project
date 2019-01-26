package com.cl.service;

import com.cl.domain.User;

public interface UserService {

	void regist(User user);

	User login(User user);

}
