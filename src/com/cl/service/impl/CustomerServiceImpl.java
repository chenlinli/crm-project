package com.cl.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.cl.dao.CustomerDao;
import com.cl.domain.Customer;
import com.cl.domain.PageBean;
import com.cl.service.CustomerService;

@Transactional
public class CustomerServiceImpl implements CustomerService{

	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public void save(Customer customer) {
		this.customerDao.save(customer);
	}

	@Override
	public PageBean<Customer> findByPage(DetachedCriteria criteria, Integer currentPage,Integer pageSize) {
		PageBean<Customer> pageBean = new PageBean<Customer>();
		//��װ��ǰҳ��
		pageBean.setCurrPage(currentPage);
		//ÿҳ�ļ�¼��
		pageBean.setPageSize(pageSize);
		//�ܼ�¼��
		Integer totalCount = customerDao.findCount(criteria);
		//System.out.println(totalCount);
		pageBean.setTotalCount(totalCount);
		//��ҳ��
		Double ceil = Math.ceil(totalCount*1.0/pageSize);
		pageBean.setTotalPage(ceil.intValue());
		//ÿҳ�����ݵļ���
		Integer begin = (currentPage-1)*pageSize;
		List<Customer> list = customerDao.findByPage(criteria,begin,pageSize);
		pageBean.setList(list);
		System.out.println(list);
		return pageBean;
	}
	
}
