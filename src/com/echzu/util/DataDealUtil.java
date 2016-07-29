package com.echzu.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.echzu.bean.Job;

/**
 * HTML文档处理工具类
 */
public class DataDealUtil {

	private Job job= new Job(); 
	private String dealhtml = "";
	private String urlRoot = "";
	private String urlKey = "";
	
	public DataDealUtil(String url) {
		//url = "http://beijing.58.com/job/?key=java";
		//String [] urlarray = url.split("/job");
		//urlRoot = urlarray[0];
		//urlKey = urlarray[1];
		dealhtml = GetHtmlByURLUtil.getHtmlResourceByUrl(url);

		dealhtml = dealhtml.replaceAll("\"", "\'");
		dealhtml = dealhtml.replaceAll("  ", "");
		//System.out.println(dealhtml);
	}

	/**
	 * 统计当前页面招聘公司数
	 * 
	 * @return 页面总公司数
	 */
	private void getCompanyNum() {
		// 创建 Pattern 对象
		int num = 0;
		Pattern r = Pattern.compile("titbar");

		// 现在创建 matcher 对象
		Matcher m = r.matcher(dealhtml);
		while (m.find()) {
			++num;
		}
		job.setCompanyNum(num);
	}
	/**
	 * 获取当前页面招聘总人数
	 * @return
	 */
	private void getWorkerNum() {
		// 创建 Pattern 对象
		Pattern r = Pattern.compile("招聘人数：</span>\\d+人");
		// 现在创建 matcher 对象
		Matcher m = r.matcher(dealhtml);
		String temp = "";
		String value = "";
		int workerNum = 0;
		while (m.find()) {
			temp = m.group();
			//System.out.println(temp + "\n");
			value = temp.substring(12, temp.length()-1);
			workerNum+=Integer.parseInt(value);
		}
		job.setWorkerNum(workerNum);
	}

	private void getSalary() {
		int i = 0;
		Pattern r = Pattern.compile("转正工资：</span>\\d+-\\d+");
		Matcher m = r.matcher(dealhtml);
		Map<String, String> salary = new HashMap<String, String>();
		String temp = "";
		String value = "";
		while (m.find()) {
			temp = m.group();
			//System.out.println(temp + "\n");
			value = temp.substring(12, temp.length());
			if (salary.get(value) != null) {
				salary.put(value, Integer.parseInt(salary.get(value)) + 1 + "");
			} else {
				salary.put(value, "1");
			}
		}
		// 统计工资为面议的职位数
		r = Pattern.compile("转正工资：</span>面议");
		m = r.matcher(dealhtml);
		while (m.find()) {
			++i;
		}
		salary.put("my", i + "");
		job.setSalary(salary);
	}

	private void getEduca() {
		Pattern r = Pattern.compile("学历要求：</span>[\\u4e00-\\u9fa5]+");
		Matcher m = r.matcher(dealhtml);
		Map<String, String> educa = new HashMap<String, String>();
		String temp = "";
		String value = "";
		while (m.find()) {
			temp = m.group();
			//System.out.println(temp + "\n");
			value = temp.substring(12, temp.length());
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
		job.setEduca(educa);
	}

	private void getWorkExp() {
		Pattern r = Pattern.compile("工作经验：</span>\\d-\\d年");
		Matcher m = r.matcher(dealhtml);
		int i = 0;
		Map<String, String> workExp = new HashMap<String, String>();
		String temp = "";
		String value = "";
		while (m.find()) {
			temp = m.group();
			//System.out.println(temp + "\n");
			value = temp.substring(12, temp.length()-1);
			if (workExp.get(value) != null) {
				workExp.put(value, Integer.parseInt(workExp.get(value)) + 1
						+ "");
			} else {
				workExp.put(value, "1");
			}
		}
		// 统计工作经验为不限的职位数
		r = Pattern.compile("工作经验：</span>不限");
		m = r.matcher(dealhtml);
		while (m.find()) {
			++i;
		}
		workExp.put("bx", i + "");
		job.setWorkExp(workExp);
	}
	
	public Job getJob(){
		getCompanyNum();
		getWorkerNum();
		getSalary();
		getEduca();
		getWorkExp();
		return job;
	}
	
	public Set<String> getUrl(){
		Set<String> tempSet = new HashSet<String>();
		String temp = "";
// <a href='http://bj.58.com/tech/25537112327862x.shtml' urlparams='psid=121779123191353779800781919&entinfo=25537112327862_3'  
//		 target="_blank" class="t"   _t='common'>
//
		Pattern r = Pattern.compile("href='http://\\w+.58.com/tech/\\d+x.shtml'\\s+urlparams='psid=\\d+&entinfo=\\d+_\\d'\\s+target='_blank'\\s+class='t'\\s+_t='jingpin'");
		// 现在创建 matcher 对象
		Matcher m = r.matcher(dealhtml);
		while (m.find()) {
			temp = m.group();
			//value = temp.substring(6, temp.length()-1);
			System.out.println(temp);
			tempSet.add(urlRoot+temp+urlKey);
		}
		return tempSet;
	}
	
	public Set<String> getTest(){
		Set<String> tempSet = new HashSet<String>();
		String temp = "";
		// <a href='http://bj.58.com/tech/25537112327862x.shtml' urlparams='psid=121779123191353779800781919&entinfo=25537112327862_3'  
//		 target='_blank'class='t'_t='common'>
		Pattern r = Pattern.compile("target='_blank'class='t'_t='common'");
		// 现在创建 matcher 对象
		Matcher m = r.matcher(dealhtml);
		while (m.find()) {
			temp = m.group();
			//value = temp.substring(6, temp.length()-1);
			System.out.println(temp);
			tempSet.add(urlRoot+temp+urlKey);
		}
		return tempSet;
	}
	
	public Set<String> getChildsURL(){
		Set<String> tempSet = new HashSet<String>();
		String temp = "";
		Pattern r = Pattern.compile("/job/pn\\d");
		// 现在创建 matcher 对象
		Matcher m = r.matcher(dealhtml);
		while (m.find()) {
			temp = m.group();
			//value = temp.substring(6, temp.length()-1);
			tempSet.add(urlRoot+temp+urlKey);
		}
		return tempSet;
	}
}
