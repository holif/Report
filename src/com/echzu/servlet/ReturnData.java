package com.echzu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.echzu.bean.Job;
import com.echzu.util.DealHtml;

public class ReturnData extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ReturnData() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException  {

		String url = request.getParameter("url");
		List<String> list = DealHtml.getchildURL(url);
		DealHtml tool = new DealHtml();
		Job job = tool.getJob(list);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = JSONArray.fromObject(job);
		out.print(jsonArray);
		System.out.println(jsonArray);
		out.flush();
		out.close();
	}
	
	public void init() throws ServletException {
	}
}
