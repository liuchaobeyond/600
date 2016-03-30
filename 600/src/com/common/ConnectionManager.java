package com.common;


/**
 * <p>Title: WebGPS</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: SEG Navigation Co. Ltd.</p>
 *
 * @author not attributable
 * @version 1.0
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionManager {
    static private ConnectionManager instance;
    static private Properties properties;
    static synchronized public ConnectionManager getInstance() {   
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }
    
    private ConnectionManager() {
        this.init();
    }

    private void init(){
        	InputStream is = getClass().getResourceAsStream("/Proxool.properties");
            properties = new Properties();
            try {
				properties.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			PropertyConfigurator.configure(properties);
    }
    public static String readValue(String key) throws IOException {
        return  properties.getProperty(key);
    }
    public Connection getConnection(){
        Connection con = null;
        try{
            con = DriverManager.getConnection("proxool."+readValue("jdbc-0.proxool.alias"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return con;
    }
    
    public Connection getConnection(String proxoolname) {
        Connection con = null;
        try {
        	con = DriverManager.getConnection(proxoolname);
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return con;
    }
    
    public Connection getConnection(String name, long time) {
        return this.getConnection();
    }
   
    public void freeConnection(String name, Connection con) {
        try{
            if(con!=null&&!con.isClosed()){
            	con.close();
            }
        }catch(Exception ex){ex.printStackTrace();}
    }

    public void ClosePstam(ResultSet rs,PreparedStatement pstam,String dbconn,Connection conn){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        if (pstam != null) {
            try {
                pstam.close();
            } catch (SQLException e) {
            }
        }
        try {
          freeConnection(dbconn, conn);
        }catch (Exception e) {}
      
    }

    public void CloseCstam(ResultSet rs,CallableStatement cstam,String dbconn,Connection conn){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        if (cstam != null) {
            try {
                cstam.close();
            } catch (SQLException e) {
            }
        }
        try {
          freeConnection(dbconn, conn);
        }catch (Exception e) {}
      
    }

    public void CloseStam(ResultSet rs,Statement stam,String dbconn,Connection conn){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        if (stam != null) {
            try {
                stam.close();
            } catch (SQLException e) {
            }
        }
        try {
          freeConnection(dbconn, conn);
        }catch (Exception e) {}
    }
    
    public void release() {}
}
