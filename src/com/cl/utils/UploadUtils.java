package com.cl.utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.UUID;

public class UploadUtils {

	/**
	 * Ψһ�ļ���
	 * @param filename
	 * @return
	 */
	public static String getUuidFileName(String filename){
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return UUID.randomUUID().toString().replace("-", "")+extension;
	}
	/**
	 * Ŀ¼���룺2��
	 * @param uuidFileName
	 * @return
	 */
	public static String getPath(String uuidFileName){
		int code1=uuidFileName.hashCode();
		int d1=code1 & 0xf;//һ��Ŀ¼
		int code2 = code1 >>> 4;
		int d2 = code2 & 0xf;//����Ŀ¼
		return "/"+d1+"/"+d2;
	}
	
	public static void main(String[] args) {
		System.out.println(getUuidFileName("aa.txt"));
	}
}
