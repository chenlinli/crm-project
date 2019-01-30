package com.cl.web.action;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cl.domain.PageBean;
import com.cl.domain.SaleVisit;
import com.cl.service.SaleVisitService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit> {

	private SaleVisit saleVisit = new SaleVisit();
	@Override
	public SaleVisit getModel() {
		// TODO Auto-generated method stub
		return saleVisit;
	}

	@Resource(name="saleVisitService")
	private SaleVisitService saleVisitService;
	
	private Integer currPage=1;
	private Integer pageSize=3;
	public void setCurrPage(Integer currPage) {
		if(currPage==null){
			this.currPage=1;
		}else
			this.currPage = currPage;
	}
	public void setPageSize(Integer pageSize) {
		if(pageSize==null){
			this.pageSize=3;
		}else{
			this.pageSize = pageSize;
		}
	}
	
	private Date visit_endtime;
	public void setVisit_endtime(Date visit_endtime) {
		this.visit_endtime = visit_endtime;
	}
	
	public Date getVisit_endtime() {
		return visit_endtime;
	}
	public String findAll(){
		DetachedCriteria criteria = DetachedCriteria.forClass(SaleVisit.class);
		//…Ë÷√Ãıº˛
		if(saleVisit.getVisit_time()!=null && !saleVisit.getVisit_time().equals("")){
			criteria.add(Restrictions.ge("visit_time", saleVisit.getVisit_time()));
		}
		if(visit_endtime!=null){
			criteria.add(Restrictions.le("visit_time", visit_endtime));
		}
		PageBean<SaleVisit> list = saleVisitService.findByPage(criteria,currPage,pageSize);
		ActionContext.getContext().getValueStack().push(list);
		return "findAll";
	}
	
	public String saveUI(){
		return "saveUI";
	}
	
	public String save(){
		saleVisitService.save(saleVisit);
		return "saveSuccess";
	}
}
