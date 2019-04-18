package com.yixin.alixjob.utils.email;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class HtmlUtil {

	private static final Log logger = LogFactory.getLog(HtmlUtil.class);

	public static byte[] readHtmlTemplate(String filePath, String fileName) throws Exception {
		logger.info("读取html文件,文件路径:" + filePath + ",文件名称:" + fileName);
		StringBuilder sbStr = new StringBuilder("");
		FileInputStream is = null;
		BufferedReader br = null;
		try {
			is = new FileInputStream(filePath + fileName);// 读取模块文件
			br = new BufferedReader(new InputStreamReader(is, "utf-8"));
			while (br.ready()) {
				sbStr.append(br.readLine() + "\n");
			}
		} catch (Exception e) {
			throw new Exception("读取" + fileName + "模板文件异常！", e);
		} finally {
			try {
				br.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sbStr.toString().getBytes();
	}

	public static void main(String[] args) {
		String filePath = "C:/Users/IBM/Desktop/temp/";
		String fileName = "pay.html";
		try {
			System.out.println(new String(HtmlUtil.readHtmlTemplate(filePath, fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}