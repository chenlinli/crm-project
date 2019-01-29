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
	
	//ע��ͻ������service
	private CustomerService customerService;
	

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public String findAll(){
		DetachedCriteria criteria = DetachedCriteria.forClass(LinkMan.class);
		//��������
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
		//��ѯ�����ͻ�
		List<Customer> list = customerService.findAll();
		//list���浽ֵջ
		ActionContext.getContext().getValueStack().set("list", list);
		return "saveUI";
	}
	
	public String save(){
		linkManService.save(linkMan);
		return "saveSuccess";
	}
	
	public String edit(){
		//��ѯĳ����ϵ�ˣ����пͻ�
		List<Customer> list = customerService.findAll();
		//����Id����ϵ��
		linkMan = linkManService.findById(linkMan.getLkm_id());
		//��list����ϵ�˶������ҳ����
		ActionContext.getContext().getValueStack().set("list", list);
		ActionContext.getContext().getValueStack().push(linkMan);
		return "editSuccess";
	}
	
	public String update(){
		linkManService.update(linkMan);
		return "updateSuccess";
	}
	
	public String delete(){
		//���ܼ���ɾ��
		//����ɾ
		LinkMan linkMan2 = linkManService.findById(linkMan.getLkm_id());
		linkManService.delete(linkMan2);
		return "deleteSuccess";
	}

}
