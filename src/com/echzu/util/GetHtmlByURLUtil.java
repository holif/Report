package com.echzu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * HTML页面源码抓取工具类
 */
public class GetHtmlByURLUtil {
	private static String encoding = "utf-8";

	/**
	 * 根据网页URL和编码获取网页的源代码
	 * 
	 * @param url
	 *            网址
	 * @param encoding
	 *            编码
	 * @return 网页源码
	 */
	public static String getHtmlResourceByUrl(String url) {
		StringBuffer buffer = new StringBuffer();
		URL urlObj = null;
		URLConnection uc = null;
		InputStreamReader isr = null;
		BufferedReader buf = null;
		try {
			urlObj = new URL(url);
			uc = urlObj.openConnection();
			uc.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");
			isr = new InputStreamReader(uc.getInputStream(), encoding);
			buf = new BufferedReader(isr);

			String temp = null;
			while ((temp = buf.readLine()) != null) {
				buffer.append(temp);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

	public static void file(String content) {
		FileOutputStream fop = null;
		File file;
		try {

			file = new File("d:/newfile.html");
			fop = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}