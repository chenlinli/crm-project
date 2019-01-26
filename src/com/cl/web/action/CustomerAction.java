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
	//ע��Service	
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	//ʹ��setXxx()��������
	private Integer currPage=1;
	public void setCurrPage(Integer currPage) {
		if(currPage==null){
			this.currPage=1;
		}else
			this.currPage = currPage;
	}
	
	
	//ʹ��set����ÿҳ��ʾ�ļ�¼��
	private Integer pageSize=3;
	public void setPageSize(Integer pageSize) {
		if(pageSize==null){
			pageSize=3;
		}else{
			this.pageSize = pageSize;
		}
	}

	/**
	 * ��ת����ӿͻ���ҳ��
	 * @return
	 */
	public String saveUI(){
		//��ѯ�ֵ�����
		return "saveUI";
	}
	
	/**
	 * ����ͻ�
	 */
	
	public String save(){
		customerService.save(customer);
		return NONE;
	}
	
	public String findAll(){
		//���ղ�������ҳ����
		//ʹ��detachedCriteria,������ѯ����ҳ
		System.out.println("currPage:"+currPage+"  pageSize:"+pageSize);
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		PageBean<Customer> page = customerService.findByPage(criteria,currPage,pageSize);
		ActionContext.getContext().getValueStack().push(page);//����ֵջ
		return "findAll";
	}
}
