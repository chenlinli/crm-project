package com.cl.web.action;

import org.apache.struts2.ServletActionContext;

import com.cl.domain.User;
import com.cl.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User> {

	private User user = new User();
	@Override
	public User getModel() {
		return user;
	}
	
	//注入Service
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 用户注册
	 * @return
	 */
	public String regist(){
		userService.regist(user);
		return "login";
	}
	/**
	 * 用户登录
	 */
	public String login(){
		User u=userService.login(user);
		if(u==null){
			//登录失败
			//添加错误信息
			this.addActionError("用户名或密码错误");
			return LOGIN;
			
		}else{
			//ServletActionContext.getRequest().getSession().setAttribute("user", u);
			ActionContext.getContext().getSession().put("user", u);
			return SUCCESS;
		}
	}

}
