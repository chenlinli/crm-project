package com.cl.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface BaseDao<T> {

	public void save(T t);
	public void update(T t);
	public void delete(T t);
	public T findById(Serializable id);
	public List<T> findAll();
	public Integer findCount(DetachedCriteria criteria);
	public List<T> findByPage(DetachedCriteria criteria, Integer begin, Integer pageSize);
}
