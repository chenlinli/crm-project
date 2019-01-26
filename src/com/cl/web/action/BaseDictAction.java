package com.cl.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.cl.domain.BaseDict;
import com.cl.service.BaseDictService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseDictAction extends ActionSupport implements ModelDriven<BaseDict>{

	private BaseDict baseDict=new BaseDict();
	private BaseDictService baseDictService;
	
	public void setBaseDictService(BaseDictService baseDictService) {
		this.baseDictService = baseDictService;
	}

	@Override
	public BaseDict getModel() {
		return baseDict;
	}
	
	/**
	 * 根据类型名称查字典来源
	 * @throws IOException 
	 * 
	 */
	public String findByTypeCode() throws IOException{
		//System.out.println("findByTypeCode()");
		List<BaseDict> list = baseDictService.findByTypeCode(baseDict.getDict_type_code());
		//list转json---jsonlib fast-json
		JsonConfig jsonConfig = new JsonConfig();
		//不解析三个字段
		jsonConfig.setExcludes(new String[]{"dict_enable","dict_memo","dict_sort"});
		JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
		//System.out.println(jsonArray.toString());
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
		//JSONObject将对象转成json，JSONArray将数组转json，JSONConfig：转Json的配置
		return NONE;
	}

}
