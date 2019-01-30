package com.cl.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.cl.domain.User;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class PriviledeInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		//�ж�sesion�Ƿ��е�½���û�
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		if(user==null){
			//�������Ϣ������½ҳ��
			ActionSupport action = (ActionSupport) arg0.getAction();
			action.addActionError("û�е�¼��û��Ȩ�޷���");
			return action.LOGIN;
		}else{
			return arg0.invoke();
		}
	}

}
