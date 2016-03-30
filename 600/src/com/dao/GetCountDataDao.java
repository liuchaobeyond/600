package com.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.common.ConnectionManager;

public class GetCountDataDao {
	private ConnectionManager CM = null;
	private String proxoolName;
	public GetCountDataDao() {
		CM = ConnectionManager.getInstance();
		try {
			proxoolName=ConnectionManager.readValue("jdbc-0.proxool.alias");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public List<HashMap<String, Object>> getDataList(String endDate, String clickType, HttpServletRequest req) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if("day".equals(clickType)){//按照天查询
			String  startDate = null;
			try {
				 Date  endD=df.parse(endDate);
				 startDate=df.format(new Date(endD.getTime() - 14 * 24 * 60 * 60 * 1000L));  //查询前15天(包括当天)
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			PreparedStatement pstam = null;
			Connection conn = null;
			ResultSet rs = null;
			
			
			String sql = "select  ORD_SIGNOPRICE, DATE_FORMAT(DATE,'%m/%d') date, ROUND((ORD_SIGNOPRICE_A/DEMA_CONPRICE_A)*100,1)  PERCENT ,DEMA_CONPRICE,BU_SIGNONUM,ROUND((BU_SIGNONUM_A/ BU_CONNUM_A)*100,1)  AGENCYPERCENT,BU_CONNUM,ROUND((BU_CONNUM_A/ BU_REGNUM_A)*100,1)  AGENCYSENDPERCENT ,BU_REGNUM  from  wx_stat_order_day  where  DATE>= str_to_date('" + startDate+ "', '%Y-%m-%d')  and DATE<=str_to_date('" + endDate+ "', '%Y-%m-%d')  order by DATE asc";
			
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();	

			
			try {
				conn = CM.getConnection();
				pstam = conn.prepareStatement(sql);
				rs = pstam.executeQuery();
				HashMap<String,Object> hm = null;				
				while (rs.next()) {
					hm = new HashMap<String, Object>();
					hm.put("ORD_SIGNOPRICE",  rs.getLong("ORD_SIGNOPRICE"));
					hm.put("DATE",  rs.getString("date"));
					hm.put("PERCENT",  rs.getString("PERCENT"));
					hm.put("DEMA_CONPRICE",  rs.getString("DEMA_CONPRICE"));
					hm.put("BU_SIGNONUM",  rs.getString("BU_SIGNONUM"));
					hm.put("AGENCYPERCENT",  rs.getString("AGENCYPERCENT"));
					
					hm.put("BU_CONNUM",  rs.getString("BU_CONNUM"));
					hm.put("AGENCYSENDPERCENT",  rs.getString("AGENCYSENDPERCENT"));
					
					hm.put("BU_REGNUM",  rs.getString("BU_REGNUM"));
					
	                list.add(hm);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CM.ClosePstam(rs, pstam, proxoolName, conn);
			}
			
			return list;			
			
		}else {//按照天查询
			String  startDate = null;
			try {
				 Date  endD=df.parse(endDate);
				 startDate=df.format(new Date(endD.getTime() - 41 * 24 * 60 * 60 * 1000L));  //查询前28天
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			PreparedStatement pstam = null;
			Connection conn = null;
			ResultSet rs = null;
			
			
			//String sql = "select  ORD_SIGNOPRICE, DATE_FORMAT(DATE,'%m/%d') date, ROUND((ORD_SIGNOPRICE_A/DEMA_CONPRICE_A)*100,1)  PERCENT ,DEMA_CONPRICE,BU_SIGNONUM,ROUND((BU_SIGNONUM_A/ BU_CONNUM_A)*100,1)  AGENCYPERCENT,BU_CONNUM,ROUND((BU_CONNUM_A/ BU_REGNUM_A)*100,1)  AGENCYSENDPERCENT ,BU_REGNUM  from  wx_stat_order_week  where  DATE>= str_to_date('" + startDate+ "', '%Y-%m-%d')  and DATE<=str_to_date('" + endDate+ "', '%Y-%m-%d')  order by DATE asc";
			String sql = "SELECT w.ORD_SIGNOPRICE,	T.jfw,DATE_FORMAT(w.DATE, '%m/%d') date ,	ROUND((w.ORD_SIGNOPRICE_A / w.DEMA_CONPRICE_A		)* 100,		1	)PERCENT,	w.DEMA_CONPRICE,	w.BU_SIGNONUM,	ROUND(		(w.BU_SIGNONUM_A / w.BU_CONNUM_A)* 100,		1	)AGENCYPERCENT,	w.BU_CONNUM,	ROUND(		(w.BU_CONNUM_A / w.BU_REGNUM_A)* 100,	1	)AGENCYSENDPERCENT,	w.BU_REGNUM FROM	 wx_stat_order_week  w, time_dimension T WHERE	w.DATE >= str_to_date('"+startDate+"', '%Y-%m-%d') AND w.DATE <= str_to_date('"+endDate+"', '%Y-%m-%d') and w.DATE = T.date ORDER BY	t.DATE ASC";
			
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();	

			
			try {
				conn = CM.getConnection();
				pstam = conn.prepareStatement(sql);
				rs = pstam.executeQuery();
				HashMap<String, Object> hm = null;				
				while (rs.next()) {
					hm = new HashMap<String, Object>();
					hm.put("ORD_SIGNOPRICE",  rs.getLong("ORD_SIGNOPRICE"));
					hm.put("DATE",  rs.getString("jfw")+"周("+rs.getString("date")+")");
					hm.put("PERCENT",  rs.getString("PERCENT"));
					hm.put("DEMA_CONPRICE",  rs.getString("DEMA_CONPRICE"));
					hm.put("BU_SIGNONUM",  rs.getString("BU_SIGNONUM"));
					hm.put("AGENCYPERCENT",  rs.getString("AGENCYPERCENT"));
					
					hm.put("BU_CONNUM",  rs.getString("BU_CONNUM"));
					hm.put("AGENCYSENDPERCENT",  rs.getString("AGENCYSENDPERCENT"));
					
					hm.put("BU_REGNUM",  rs.getString("BU_REGNUM"));
					
	                list.add(hm);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CM.ClosePstam(rs, pstam, proxoolName, conn);
			}
			
			return list;			
			
		}
		
	}
}
