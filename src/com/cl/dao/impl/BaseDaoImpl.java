package com.cl.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.cl.dao.BaseDao;

/**
 * 通用Dao实现类
 * @author CL
 *
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{

	private Class clazz;

	/**
	 * 不想子类有构造方法，父类提供无参构造，并获得具体类型Class
	 * 获得子类的参数化类型的实际类型参数
	 */
	public BaseDaoImpl() {
		//泛型的反射
		//获得Class，如CustomerDaoImpl的Class
		//this.getClass():BaseDaoImpl子类的Class
		Class<? extends BaseDaoImpl> class1 = this.getClass();
		//获得带有泛型的父类：参数化类型,BaseDaoImpl<User>/BaseDaoImpl<Customer>
		Type superclass = class1.getGenericSuperclass();
		//强转为参数化类型
		ParameterizedType pt= (ParameterizedType) superclass;
		//通过参数化类型获得实际类型参数数组，可能有多个不同类型参数
		Type[] typeArguments = pt.getActualTypeArguments();
		clazz=(Class) typeArguments[0];
		
	}
	@Override
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}

	@Override
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	@Override
	public T findById(Serializable id) {
		//拿到具体类型的Class
		T t = (T) this.getHibernateTemplate().get(clazz, id);
		return t;
	}
	//查所有
	@Override
	public List<T> findAll() {
		return (List<T>) this.getHibernateTemplate().find("from "+clazz.getSimpleName());
	}
	//统计个数
	@Override
	public Integer findCount(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(criteria);
		if(list.size()>0){
			return list.get(0).intValue();
		}
		return null;
	}
	@Override
	public List<T> findByPage(DetachedCriteria criteria, Integer begin, Integer pageSize) {
		criteria.setProjection(null);
		return (List<T>) this.getHibernateTemplate().findByCriteria(criteria,begin,pageSize);
	}

}
