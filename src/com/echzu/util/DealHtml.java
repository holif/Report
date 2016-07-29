package com.echzu.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.echzu.bean.Job;

public class DealHtml {
	private Job job = new Job();
	private Map<String, String> salary = new HashMap<String, String>();
	private Map<String, String> educa = new HashMap<String, String>();
	private Map<String, String> workExp = new HashMap<String, String>();
	
	public static List<String> getchildURL(String url){
		List<String> childURL = new ArrayList<String>();
		String dealhtml;	
		int i = 1;	
		String arr[] ={"",""};
		arr= url.split("job");
		while(i<7){
			url = arr[0]+"job/pn"+i+arr[1];
			dealhtml = GetHtmlByURLUtil.getHtmlResourceByUrl(url);
			dealhtml = dealhtml.replaceAll("\"", "\'");
			dealhtml = dealhtml.replaceAll("  ", "");
			Pattern r = Pattern.compile(arr[0]+"tech/\\d+x.shtml");
			Matcher m = r.matcher(dealhtml);
			String temp = "";
			while (m.find()) {
				temp = m.group();
				childURL.add(temp);
			}
			++i;
		}
		return childURL;
	}
	public  Job getJob(List<String> list){

		String dealhtml;
		for (String string : list) {
			dealhtml = GetHtmlByURLUtil.getHtmlResourceByUrl(string);
			dealhtml = dealhtml.replaceAll("\"", "\'");
			dealhtml = dealhtml.replaceAll("  ", "");			
			job.setCompanyNum(job.getCompanyNum()+1);
			getWorkerNum(dealhtml);
			getSalary(dealhtml);
			getEduca(dealhtml);
			getWorkExp(dealhtml);
		}
		job.setSalary(salary);
		job.setEduca(educa);
		job.setWorkExp(workExp);
		return job;
	}
	
	/**
	 * 获取当前页面招聘总人数
	 * @return
	 */
	private void getWorkerNum(String dealhtml) {
		// 创建 Pattern 对象
		String temp = "";
		int workerNum = 0;
		Pattern r = Pattern.compile("(招\\d+人)");
		// 现在创建 matcher 对象
		Matcher m = r.matcher(dealhtml);	
		if(m.find()){
			temp = m.group();
			temp = temp.substring(1, temp.length()-1);
			workerNum =Integer.parseInt(temp);
			job.setWorkerNum(job.getWorkerNum()+workerNum);
		}	
	}

	private void getSalary(String dealhtml) {
		Pattern r = Pattern.compile("<strong>\\d+-\\d+元");
		Matcher m = r.matcher(dealhtml);
		
		String temp = "";
		String value = "";
		if(m.find()){
			temp = m.group();
			value = temp.substring(8, temp.length()-1);
			if (salary.get(value) != null) {
				salary.put(value, Integer.parseInt(salary.get(value)) + 1 + "");
			} else {
				salary.put(value, "1");
			}
		} else {
			if(salary.get("my") != null){
				salary.put("my", Integer.parseInt(salary.get("my"))+1+"");
			} else {
				salary.put("my", "1");
			}			
		}
	}

	private void getEduca(String dealhtml) {
		Pattern r = Pattern.compile("<span>学历要求：</span>[\\u4e00-\\u9fa5]+");
		Matcher m = r.matcher(dealhtml);	
		String temp = "";
		String value = "";
		if (m.find()) {
			temp = m.group();
			value = temp.substring(18, temp.length());
			if(value.equals("本科")){
				value = "bk";
			} else if (value.equals("大专")) {
				value = "dz";
			} else if (value.equals("中专")) {
				value = "zz";
			} else if (value.equals("高中")) {
				value = "gz";
			} else if (value.equals("不限")) {
				value = "bx";
			} else {
				value = "ss";
			}
			if (educa.get(value) != null) {
				educa.put(value, Integer.parseInt(educa.get(value)) + 1 + "");
			} else {
				educa.put(value, "1");
			}
		}
	}

	private void getWorkExp(String dealhtml) {
		Pattern r = Pattern.compile("<span>工作年限：</span>\\d-\\d年");
		Matcher m = r.matcher(dealhtml);
		String temp = "";
		String value = "";
		if(m.find()){
			temp = m.group();
			value = temp.substring(18, temp.length()-1);
			if (workExp.get(value) != null) {
				workExp.put(value, Integer.parseInt(workExp.get(value)) + 1
						+ "");
			} else {
				workExp.put(value, "1");
			}
		} else {
			if(workExp.get("bx") != null){
				workExp.put("bx",Integer.parseInt(workExp.get("bx"))+ 1 + "");
			} else {
				workExp.put("bx", "1");
			}
		}		
	}
}