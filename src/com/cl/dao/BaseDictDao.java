package com.cl.dao;

import java.util.List;

import com.cl.domain.BaseDict;

public interface BaseDictDao {

	List<BaseDict> findByTypeCode(String dict_type_code);

}
