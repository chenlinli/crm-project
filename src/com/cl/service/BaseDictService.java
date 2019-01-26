package com.cl.service;

import java.util.List;

import com.cl.domain.BaseDict;

public interface BaseDictService {

	List<BaseDict> findByTypeCode(String dict_type_code);

}
