package com.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.JsonUtil;
import com.dao.GetCountDataDao;

public class GetCountDataServlet extends  HttpServlet{
	
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		    doPost(req, resp);
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {			
		String beginDate = req.getParameter("beginDate");
		String clickType = req.getParameter("clickType");
		resp.setCharacterEncoding("utf-8");
		//beginDate="2015-02-24";
		GetCountDataDao userdao = new GetCountDataDao();
		List<HashMap<String, Object>> list = userdao.getDataList(beginDate, clickType,req);
		
		JsonUtil.responseJsonObject(resp, list);
		
	}

}
