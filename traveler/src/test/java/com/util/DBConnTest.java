package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnTest {

    String url="jdbc:mysql://localhost:3306/";
    String dbName = "traveler?useSSL=false";
    String user="root";
    String pwd="java$!";


    DBConnTest(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver 로딩 성공");
            Connection conn = DriverManager.getConnection(url+dbName, user, pwd);
        } catch (ClassNotFoundException e){
            System.out.println("Driver 로딩 실패");
        } catch (SQLException e){
            System.out.println("서버 연결 실패" + e);
        }
    }


    public static void main(String[] args) {
        new DBConnTest();
    }
}