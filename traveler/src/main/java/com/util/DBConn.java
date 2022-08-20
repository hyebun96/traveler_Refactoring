package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConn {
	private static Connection conn = null;

	public static Connection getConnection() {
		//String url="jdbc:mysql:thin:@//localhost:3306/traveler";
		String url="jdbc:mysql://localhost:3306/";
		String dbName = "traveler?useSSL=false";
		String user="root";
		String pwd="java$!";
		
		if(conn == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(url+dbName, user, pwd);
			}catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		return conn;
	}

	public static void close() {
		if(conn==null)
			return;
		try {
			if(!conn.isClosed())
				conn.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		conn=null;
	}
}
