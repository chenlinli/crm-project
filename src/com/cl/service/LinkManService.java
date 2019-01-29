package com.cl.service;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.cl.domain.LinkMan;
import com.cl.domain.PageBean;

@Transactional
public interface LinkManService {

	PageBean<LinkMan> findAll(DetachedCriteria criteria, Integer currPage, Integer pageSize);

	void save(LinkMan linkMan);

	LinkMan findById(Long lkm_id);

	void update(LinkMan linkMan);

	void delete(LinkMan linkMan);

}
