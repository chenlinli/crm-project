package com.cl.service;

import java.util.List;

import com.cl.domain.User;

public interface UserService {

	void regist(User user);

	User login(User user);

	List<User> findAll();

}
