package com.cl.utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.UUID;

public class UploadUtils {

	/**
	 * 唯一文件名
	 * @param filename
	 * @return
	 */
	public static String getUuidFileName(String filename){
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return UUID.randomUUID().toString().replace("-", "")+extension;
	}
	/**
	 * 目录分离：2级
	 * @param uuidFileName
	 * @return
	 */
	public static String getPath(String uuidFileName){
		int code1=uuidFileName.hashCode();
		int d1=code1 & 0xf;//一级目录
		int code2 = code1 >>> 4;
		int d2 = code2 & 0xf;//二级目录
		return "/"+d1+"/"+d2;
	}
	
	public static void main(String[] args) {
		System.out.println(getUuidFileName("aa.txt"));
	}
}
