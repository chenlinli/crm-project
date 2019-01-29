package com.cl.web.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cl.domain.Customer;
import com.cl.domain.LinkMan;
import com.cl.domain.PageBean;
import com.cl.service.CustomerService;
import com.cl.service.LinkManService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan> {

	private LinkMan linkMan = new LinkMan();
	
	@Override
	public LinkMan getModel() {
		// TODO Auto-generated method stub
		return linkMan;
	}
	
	private LinkManService linkManService;

	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}
	
	private Integer currPage=1;
	
	private Integer pageSize=3;
	
	

	public void setCurrPage(Integer currPage) {
		if(currPage==null){
			currPage=1;
		}
		this.currPage = currPage;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize==null){
			pageSize=3;
		}
		this.pageSize = pageSize;
	}
	
	//注入客户管理的service
	private CustomerService customerService;
	

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public String findAll(){
		DetachedCriteria criteria = DetachedCriteria.forClass(LinkMan.class);
		//设置条件
		if(linkMan.getLkm_name()!=null&& !(linkMan.getLkm_name().equals(""))){
			criteria.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));
		}
		if(linkMan.getLkm_gender()!=null && !(linkMan.getLkm_gender().equals(""))){
			criteria.add(Restrictions.eq("lkm_gender", linkMan.getLkm_gender()));
		}
		PageBean<LinkMan> page = linkManService.findAll(criteria,currPage,pageSize);
		System.out.println(page);
		ActionContext.getContext().getValueStack().push(page);
		return "findAll";
	}
	
	public String saveUI(){
		//查询所属客户
		List<Customer> list = customerService.findAll();
		//list保存到值栈
		ActionContext.getContext().getValueStack().set("list", list);
		return "saveUI";
	}
	
	public String save(){
		linkManService.save(linkMan);
		return "saveSuccess";
	}
	
	public String edit(){
		//查询某个联系人，所有客户
		List<Customer> list = customerService.findAll();
		//根据Id查联系人
		linkMan = linkManService.findById(linkMan.getLkm_id());
		//将list和联系人对象带到页面上
		ActionContext.getContext().getValueStack().set("list", list);
		ActionContext.getContext().getValueStack().push(linkMan);
		return "editSuccess";
	}
	
	public String update(){
		linkManService.update(linkMan);
		return "updateSuccess";
	}
	
	public String delete(){
		//可能级联删除
		//查再删
		LinkMan linkMan2 = linkManService.findById(linkMan.getLkm_id());
		linkManService.delete(linkMan2);
		return "deleteSuccess";
	}

}
