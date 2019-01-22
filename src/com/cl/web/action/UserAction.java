package com.cl.web.action;

import com.cl.domain.User;
import com.cl.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User> {

	private User user = new User();
	@Override
	public User getModel() {
		return user;
	}
	
	//×¢ÈëService
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * ÓÃ»§×¢²á
	 * @return
	 */
	public String regist(){
		userService.regist(user);
		return "login";
	}

}
