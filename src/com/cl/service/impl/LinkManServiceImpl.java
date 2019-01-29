package com.cl.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cl.dao.LinkManDao;
import com.cl.domain.LinkMan;
import com.cl.domain.PageBean;
import com.cl.service.LinkManService;

public class LinkManServiceImpl implements LinkManService {

	private LinkManDao linkManDao;

	public void setLinkManDao(LinkManDao linkManDao) {
		this.linkManDao = linkManDao;
	}

	@Override
	public PageBean<LinkMan> findAll(DetachedCriteria criteria, Integer currPage, Integer pageSize) {
		PageBean<LinkMan> bean = new PageBean<LinkMan>();
		bean.setCurrPage(currPage);
		bean.setPageSize(pageSize);
		Integer totalCount = linkManDao.findCount(criteria);
		bean.setTotalCount(totalCount);
		Double tc= Math.ceil(totalCount*1.0/pageSize);
		bean.setTotalPage(tc.intValue());
		Integer begin = (currPage-1)*pageSize;
		List<LinkMan> list = linkManDao.findByPage(criteria,begin,pageSize);
		bean.setList(list);
		return bean;
	}

	@Override
	public void save(LinkMan linkMan) {
		linkManDao.save(linkMan);
	}

	@Override
	public LinkMan findById(Long lkm_id) {
		return linkManDao.findById(lkm_id);
	}

	@Override
	public void update(LinkMan linkMan) {
		linkManDao.update(linkMan);
	}

	@Override
	public void delete(LinkMan linkMan) {
		linkManDao.delete(linkMan);
	}
	
}
