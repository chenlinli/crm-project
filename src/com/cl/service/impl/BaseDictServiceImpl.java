package com.cl.service.impl;

import java.util.List;

import com.cl.dao.BaseDictDao;
import com.cl.domain.BaseDict;
import com.cl.service.BaseDictService;

public class BaseDictServiceImpl implements BaseDictService{

	private BaseDictDao baseDictDao;

	public void setBaseDictDao(BaseDictDao baseDictDao) {
		this.baseDictDao = baseDictDao;
	}

	@Override
	public List<BaseDict> findByTypeCode(String dict_type_code) {
		return baseDictDao.findByTypeCode(dict_type_code);
	}
	
}
