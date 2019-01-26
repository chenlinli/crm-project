package com.cl.service.impl;

import com.cl.dao.UserDao;
import com.cl.domain.User;
import com.cl.service.UserService;
import com.cl.utils.MD5Utils;

public class UserServiceImpl implements UserService{

	//ע��Dao
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	@Override
	public void regist(User user) {
		//��������ܴ���
		user.setUser_password(MD5Utils.md5(user.getUser_password()));
		user.setUser_state("1");
		//����Dao
		userDao.save(user);
	}
	@Override
	public User login(User user) {
		//���봦��
		user.setUser_password(MD5Utils.md5(user.getUser_password()));
		return userDao.login(user);
	}

}
