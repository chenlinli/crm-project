package com.cl.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cl.domain.Customer;
import com.cl.domain.PageBean;
import com.cl.service.CustomerService;
import com.cl.utils.UploadUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	
	private Customer customer = new Customer();
	@Override
	public Customer getModel() {
		return customer;
	}
	//注入Service	
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	//使用setXxx()接收数据
	private Integer currPage=1;
	public void setCurrPage(Integer currPage) {
		if(currPage==null){
			this.currPage=1;
		}else
			this.currPage = currPage;
	}
	
	
	//使用set接受每页显示的记录数
	private Integer pageSize=3;
	public void setPageSize(Integer pageSize) {
		if(pageSize==null){
			pageSize=3;
		}else{
			this.pageSize = pageSize;
		}
	}
	
	//上传图片三个属性
	private String uploadFileName;//接收文件名称"upload"
	private File upload;   //与表单的file的name一样
	private String uploadContenType; //文件类型
	
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadContenType(String uploadContenType) {
		this.uploadContenType = uploadContenType;
	}
	

	/**
	 * 跳转到添加客户的页面
	 * @return
	 */
	public String saveUI(){
		//查询字典数据
		return "saveUI";
	}
	
	/**
	 * 保存客户
	 * @throws IOException 
	 */
	
	public String save() throws IOException{
		//上传图片
		if(upload!=null){
			//设置文件上传路径
			String path="E:/test/upload";
			//可能一个目录下相同文件名覆盖，随机产生文件名
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			//一个目录下存放的文件过多，目录分离
			String realPath = UploadUtils.getPath(uuidFileName);
			//创建目录
			String url=path+realPath;
			File file=new File(url);
			if(!file.exists())
				file.mkdirs();
			File dictFile=new File(url+"/"+uuidFileName);
			FileUtils.copyFile(upload, dictFile);
			//手动封装cust_image的值
			customer.setCust_image(url+"/"+uuidFileName);
		}
		//保存客户
		customerService.save(customer);
		return "saveSuccess";
	}
	
	public String findAll(){
		//接收参数，分页参数
		//使用detachedCriteria,条件查询带分页
		//System.out.println("currPage:"+currPage+"  pageSize:"+pageSize);
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		//设置条件
		
		if(customer.getCust_name()!=null&&(!(customer.getCust_name().equals("")))){
			criteria.add(Restrictions.like("cust_name", "%"+customer.getCust_name()+"%"));
		}
		
		if(customer.getBaseDictSource()!=null&&customer.getBaseDictSource().getDict_id()!=null
				&& (!customer.getBaseDictSource().getDict_id().equals(""))){
			criteria.add(Restrictions.eq("baseDictSource.dict_id",customer.getBaseDictSource().getDict_id()));
		}
		if(customer.getBaseDictLevel()!=null&&customer.getBaseDictLevel().getDict_id()!=null
				&&!(customer.getBaseDictLevel().getDict_id().equals(""))){
			criteria.add(Restrictions.eq("baseDictLevel.dict_id",customer.getBaseDictLevel().getDict_id()));
		}
		if(customer.getBaseDictIndustry()!=null&&customer.getBaseDictIndustry().getDict_id()!=null
				&&!(customer.getBaseDictIndustry().getDict_id().equals(""))){
			criteria.add(Restrictions.eq("baseDictIndustry.dict_id",customer.getBaseDictIndustry().getDict_id()));
		}
		PageBean<Customer> page = customerService.findByPage(criteria,currPage,pageSize);
		ActionContext.getContext().getValueStack().push(page);//放入值栈
		return "findAll";
	}
	
	public String delete(){
		//先查询再删除，方便级联删除
		customer = customerService.findById(customer.getCust_id());
		//删除客户图片
		if(customer.getCust_image()!=null){
			File file =new File(customer.getCust_image());
			if(file.exists()){
				file.delete();
			}
		}
		customerService.delete(customer);
		return "deleteSuccess"; 
	}
	
	public String edit(){
		//根据id查询
		customer = customerService.findById(customer.getCust_id());
		//两种办法将客户传递到页面：传统标签
		//1.手动压栈
			//页面取值：<s:property value="cust_name">
		//ActionContext.getContext().getValueStack().push(customer);
		//2.customer模型驱动对象在值栈里:
			//取值：<s:property value="model.cust_name">
		//跳转页面，带数据
		//使用struts的标签
		return "editSuccess";
	}
	
	public String update() throws IOException{
		//文件项是否修改，如果选择了，删除原文件
		if(upload!=null){
			//删除原文件
			String image = customer.getCust_image();
			if(image!=null ||image!=""){
				File file = new File(image);
				if(file.exists())
					file.delete();
			}
			//上传新文件
			String path="E:/test/upload";
			//可能一个目录下相同文件名覆盖，随机产生文件名
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			//一个目录下存放的文件过多，目录分离
			String realPath = UploadUtils.getPath(uuidFileName);
			//创建目录
			String url=path+realPath;
			File file=new File(url);
			if(!file.exists())
				file.mkdirs();
			File dictFile=new File(url+"/"+uuidFileName);
			FileUtils.copyFile(upload, dictFile);
			//手动封装cust_image的值
			customer.setCust_image(url+"/"+uuidFileName);
		}
		customerService.update(customer);
		return "updateSuccess";
	}
	
	public String findAllCustomer() throws IOException{
		List<Customer> list = customerService.findAll();
		JsonConfig cfg= new JsonConfig();
		cfg.setExcludes(new String[]{"linkMans","baseDictSource","baseDictIndustry","baseDictLevel"});
		JSONArray jsonArray = JSONArray.fromObject(list,cfg);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		System.out.println(jsonArray.toString());
		ServletActionContext.getResponse().getWriter().println(jsonArray.toString());
		return NONE;
	}
}
