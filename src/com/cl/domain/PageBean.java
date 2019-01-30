package com.cl.domain;

import java.util.List;

public class PageBean<T>{
	@Override
	public String toString() {
		return "PageBean [currPage=" + currPage + ", pageSize=" + pageSize + ", totalPage=" + totalPage
				+ ", totalCount=" + totalCount + ", list=" + list + "]";
	}
	private Integer currPage;//当前页
	private Integer pageSize;//每页的记录数
	private Integer totalPage;//总页数
	private Integer totalCount;//总记录数
	public Integer getCurrPage() {
		return currPage;
	}
	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	private List<T> list;//数据集合

}
