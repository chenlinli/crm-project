package com.cl.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cl.domain.Customer;
import com.cl.domain.PageBean;

public interface CustomerService {

	void save(Customer customer);

	PageBean<Customer> findByPage(DetachedCriteria criteria, Integer currentPage, Integer pageSize);

	Customer findById(Long cust_id);

	void delete(Customer customer);

	void update(Customer customer);

	List<Customer> findAll();

}
