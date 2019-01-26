package com.cl.web.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cl.domain.Customer;
import com.cl.domain.PageBean;
import com.cl.service.CustomerService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	
	private Customer customer = new Customer();
	@Override
	public Customer getModel() {
		return customer;
	}
	//注入Service	
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	//使用setXxx()接收数据
	private Integer currPage=1;
	public void setCurrPage(Integer currPage) {
		if(currPage==null){
			this.currPage=1;
		}else
			this.currPage = currPage;
	}
	
	
	//使用set接受每页显示的记录数
	private Integer pageSize=3;
	public void setPageSize(Integer pageSize) {
		if(pageSize==null){
			pageSize=3;
		}else{
			this.pageSize = pageSize;
		}
	}

	/**
	 * 跳转到添加客户的页面
	 * @return
	 */
	public String saveUI(){
		//查询字典数据
		return "saveUI";
	}
	
	/**
	 * 保存客户
	 */
	
	public String save(){
		customerService.save(customer);
		return NONE;
	}
	
	public String findAll(){
		//接收参数，分页参数
		//使用detachedCriteria,条件查询带分页
		System.out.println("currPage:"+currPage+"  pageSize:"+pageSize);
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		PageBean<Customer> page = customerService.findByPage(criteria,currPage,pageSize);
		ActionContext.getContext().getValueStack().push(page);//放入值栈
		return "findAll";
	}
}
