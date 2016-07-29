package com.echzu.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储一个页面中的有效信息
 * @author 
 *
 */
public class Job {
	private int companyNum = 0;// 统计公司数
	private int workerNum = 0;// 统计招聘的总人数
	private Map<String, String> salary = new HashMap<String, String>();
	private Map<String, String> educa = new HashMap<String, String>();
	private Map<String, String> workExp = new HashMap<String, String>();
	public int getWorkerNum() {
		return workerNum;
	}
	public void setWorkerNum(int workerNum) {
		this.workerNum = workerNum;
	}
	public int getCompanyNum() {
		return companyNum;
	}
	public void setCompanyNum(int companyNum) {
		this.companyNum = companyNum;
	}
	public Map<String, String> getSalary() {
		return salary;
	}
	public void setSalary(Map<String, String> salary) {
		this.salary = salary;
	}
	public Map<String, String> getEduca() {
		return educa;
	}
	public void setEduca(Map<String, String> educa) {
		this.educa = educa;
	}
	public Map<String, String> getWorkExp() {
		return workExp;
	}
	public void setWorkExp(Map<String, String> workExp) {
		this.workExp = workExp;
	}
}
