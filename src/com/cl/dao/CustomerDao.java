package com.cl.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cl.domain.Customer;

public interface CustomerDao {

	void save(Customer customer);

	Integer findCount(DetachedCriteria criteria);

	List<Customer> findByPage(DetachedCriteria criteria, Integer begin, Integer pageSize);

}
