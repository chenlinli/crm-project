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
 * ͨ��Daoʵ����
 * @author CL
 *
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T>{

	private Class clazz;

	/**
	 * ���������й��췽���������ṩ�޲ι��죬����þ�������Class
	 * �������Ĳ��������͵�ʵ�����Ͳ���
	 */
	public BaseDaoImpl() {
		//���͵ķ���
		//���Class����CustomerDaoImpl��Class
		//this.getClass():BaseDaoImpl�����Class
		Class<? extends BaseDaoImpl> class1 = this.getClass();
		//��ô��з��͵ĸ��ࣺ����������,BaseDaoImpl<User>/BaseDaoImpl<Customer>
		Type superclass = class1.getGenericSuperclass();
		//ǿתΪ����������
		ParameterizedType pt= (ParameterizedType) superclass;
		//ͨ�����������ͻ��ʵ�����Ͳ������飬�����ж����ͬ���Ͳ���
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
		//�õ��������͵�Class
		T t = (T) this.getHibernateTemplate().get(clazz, id);
		return t;
	}
	//������
	@Override
	public List<T> findAll() {
		return (List<T>) this.getHibernateTemplate().find("from "+clazz.getSimpleName());
	}
	//ͳ�Ƹ���
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
