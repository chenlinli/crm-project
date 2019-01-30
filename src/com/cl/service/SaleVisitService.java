package com.cl.service;

import org.hibernate.criterion.DetachedCriteria;

import com.cl.domain.PageBean;
import com.cl.domain.SaleVisit;

public interface SaleVisitService {

	PageBean<SaleVisit> findByPage(DetachedCriteria criteria, Integer currPage, Integer pageSize);

	void save(SaleVisit saleVisit);

}
