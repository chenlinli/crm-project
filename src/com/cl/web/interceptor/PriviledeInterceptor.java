package com.cl.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.cl.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class PriviledeInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		//判断sesion是否有登陆的用户
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user==null){
			//村错误信息，到登陆页面
			ActionSupport action = (ActionSupport) arg0.getAction();
			action.addActionError("没有登录，没有权限访问");
			return action.LOGIN;
		}else{
			return arg0.invoke();
		}
	}

}
