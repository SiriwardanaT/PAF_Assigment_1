package com;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ConsumtionAPI")
public class ConsumtionAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ConsumtionAPI() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("date"));
		String date  = request.getParameter("date");
		String AccNo = request.getParameter("AccNo");
		int crm = Integer.parseInt(request.getParameter("Cmr"));
		double unitPrice = Double.parseDouble(request.getParameter("Uprice"));
		
		String output = ConsumptionService.AddConsmptionRecord(AccNo, crm, unitPrice,date);
		response.getWriter().write(output); 
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		System.out.println(request.getInputStream());
		System.out.println(paras.get("hiddenID").toString());
		double unitPrice = Double.parseDouble(paras.get("Uprice").toString());
		int status = Integer.parseInt(paras.get("status").toString());
		int id = Integer.parseInt(paras.get("hiddenID").toString());
		try {
			String output = ConsumptionService.updateConsumption(status,unitPrice,id);
			response.getWriter().write(output); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	
		
		
	}


	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		System.out.println(paras.get("Cid").toString());
		int id = Integer.parseInt(paras.get("Cid").toString());
		
		try {
			String output = ConsumptionService.deleteConsumptionRecord(id);
			response.getWriter().write(output); 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static Map getParasMap(HttpServletRequest request)
	{
		{
			 Map<String, String> map = new HashMap<String, String>();
			try
			 {
			 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			 String queryString = scanner.hasNext() ?
			 scanner.useDelimiter("\\A").next() : "";
			 scanner.close();
			 String[] params = queryString.split("&");
			 for (String param : params)
			 { 
				 String[] p = param.split("=");
				 map.put(p[0], p[1]);
				 }
				 }
				catch (Exception e)
				 {
				 }
				return map;
	}
	}
}
