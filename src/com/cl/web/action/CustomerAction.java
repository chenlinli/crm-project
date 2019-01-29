package com.cl.web.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cl.domain.Customer;
import com.cl.domain.PageBean;
import com.cl.service.CustomerService;
import com.cl.utils.UploadUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	
	private Customer customer = new Customer();
	@Override
	public Customer getModel() {
		return customer;
	}
	//ע��Service	
	private CustomerService customerService;

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	//ʹ��setXxx()��������
	private Integer currPage=1;
	public void setCurrPage(Integer currPage) {
		if(currPage==null){
			this.currPage=1;
		}else
			this.currPage = currPage;
	}
	
	
	//ʹ��set����ÿҳ��ʾ�ļ�¼��
	private Integer pageSize=3;
	public void setPageSize(Integer pageSize) {
		if(pageSize==null){
			pageSize=3;
		}else{
			this.pageSize = pageSize;
		}
	}
	
	//�ϴ�ͼƬ��������
	private String uploadFileName;//�����ļ�����"upload"
	private File upload;   //�����file��nameһ��
	private String uploadContenType; //�ļ�����
	
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
	 * ��ת����ӿͻ���ҳ��
	 * @return
	 */
	public String saveUI(){
		//��ѯ�ֵ�����
		return "saveUI";
	}
	
	/**
	 * ����ͻ�
	 * @throws IOException 
	 */
	
	public String save() throws IOException{
		//�ϴ�ͼƬ
		if(upload!=null){
			//�����ļ��ϴ�·��
			String path="E:/test/upload";
			//����һ��Ŀ¼����ͬ�ļ������ǣ���������ļ���
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			//һ��Ŀ¼�´�ŵ��ļ����࣬Ŀ¼����
			String realPath = UploadUtils.getPath(uuidFileName);
			//����Ŀ¼
			String url=path+realPath;
			File file=new File(url);
			if(!file.exists())
				file.mkdirs();
			File dictFile=new File(url+"/"+uuidFileName);
			FileUtils.copyFile(upload, dictFile);
			//�ֶ���װcust_image��ֵ
			customer.setCust_image(url+"/"+uuidFileName);
		}
		//����ͻ�
		customerService.save(customer);
		return "saveSuccess";
	}
	
	public String findAll(){
		//���ղ�������ҳ����
		//ʹ��detachedCriteria,������ѯ����ҳ
		//System.out.println("currPage:"+currPage+"  pageSize:"+pageSize);
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
		//��������
		
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
		ActionContext.getContext().getValueStack().push(page);//����ֵջ
		return "findAll";
	}
	
	public String delete(){
		//�Ȳ�ѯ��ɾ�������㼶��ɾ��
		System.out.println("Action delete***********************************");
		customer = customerService.findById(customer.getCust_id());
		//ɾ���ͻ�ͼƬ
		System.out.println(customer);
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
		//����id��ѯ
		customer = customerService.findById(customer.getCust_id());
		//���ְ취���ͻ����ݵ�ҳ�棺��ͳ��ǩ
		//1.�ֶ�ѹջ
			//ҳ��ȡֵ��<s:property value="cust_name">
		//ActionContext.getContext().getValueStack().push(customer);
		//2.customerģ������������ֵջ��:
			//ȡֵ��<s:property value="model.cust_name">
		//��תҳ�棬������
		//ʹ��struts�ı�ǩ
		return "editSuccess";
	}
	
	public String update() throws IOException{
		//�ļ����Ƿ��޸ģ����ѡ���ˣ�ɾ��ԭ�ļ�
		if(upload!=null){
			//ɾ��ԭ�ļ�
			String image = customer.getCust_image();
			if(image!=null ||image!=""){
				File file = new File(image);
				if(file.exists())
					file.delete();
			}
			//�ϴ����ļ�
			String path="E:/test/upload";
			//����һ��Ŀ¼����ͬ�ļ������ǣ���������ļ���
			String uuidFileName = UploadUtils.getUuidFileName(uploadFileName);
			//һ��Ŀ¼�´�ŵ��ļ����࣬Ŀ¼����
			String realPath = UploadUtils.getPath(uuidFileName);
			//����Ŀ¼
			String url=path+realPath;
			File file=new File(url);
			if(!file.exists())
				file.mkdirs();
			File dictFile=new File(url+"/"+uuidFileName);
			FileUtils.copyFile(upload, dictFile);
			//�ֶ���װcust_image��ֵ
			customer.setCust_image(url+"/"+uuidFileName);
		}
		customerService.update(customer);
		return "updateSuccess";
	}
}
