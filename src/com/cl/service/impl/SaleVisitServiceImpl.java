package com.cl.service.impl;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.cl.dao.SaleVisitDao;
import com.cl.domain.PageBean;
import com.cl.domain.SaleVisit;
import com.cl.service.SaleVisitService;

@Transactional
public class SaleVisitServiceImpl implements SaleVisitService{

	@Resource(name="saleVisitDao")
	private SaleVisitDao saleVisitDao;

	@Override
	public PageBean<SaleVisit> findByPage(DetachedCriteria criteria, Integer currPage, Integer pageSize) {
	
		PageBean<SaleVisit> bean = new PageBean<>();
		bean.setCurrPage(currPage);
		bean.setPageSize(pageSize);
		Integer totalCount = saleVisitDao.findCount(criteria);
		bean.setTotalCount(totalCount);
		Double t= Math.ceil(totalCount*1.0/pageSize);
		bean.setTotalPage(t.intValue());
		Integer begin = (currPage-1)*pageSize;
		bean.setList(saleVisitDao.findByPage(criteria, begin, pageSize));
		return bean;
	}

	@Override
	public void save(SaleVisit saleVisit) {
		saleVisitDao.save(saleVisit);
	}
	
}
