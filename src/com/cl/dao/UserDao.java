package com.cl.dao;

import com.cl.domain.User;

public interface UserDao extends BaseDao<User> {

	User login(User user);

}
