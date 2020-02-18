package com.connection.login;

import java.io.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import javax.servlet.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.util.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import com.connection.login.DatabaseConnection;

@WebServlet("/Login")
public class Login_Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {  
		      Cookie Username = new Cookie("Username", request.getParameter("Username"));
		      Username.setMaxAge(60*60*24);
		      response.addCookie( Username );
    	      if(request.getParameter("RemPass") != null) {
	    	      Cookie Pass = new Cookie("Password", request.getParameter("Password"));
	    	      Pass.setMaxAge(60*60*24);
	    	      response.addCookie(Pass);
    	      };
    	      response.setContentType("text/html");
    	      boolean valid_login = false;
    	      try {
                  Connection conn = DatabaseConnection.initializeDatabase(); 
    	          Statement stmt = conn.createStatement();
    	          String sql;
    	          sql = "SELECT id,nickname,email,Pass FROM test.Customers";
    	          ResultSet rs = stmt.executeQuery(sql);
    	          // Extract data from result set
    	          while(rs.next()){
    	             //Retrieve by column name
    	             int id  = rs.getInt("id");
    	             String nickname = rs.getString("nickname");
    	             String email = rs.getString("email");
    	             String Pass = rs.getString("Pass");
    	             if(nickname.equals(request.getParameter("Username")) && Pass.equals(request.getParameter("Password"))) {
    	            	 valid_login = true;
    	             }    
    	          }
    	          rs.close();
    	          stmt.close();
    	          conn.close();
    	       } catch(SQLException se) {
    	          //Handle errors for JDBC
    	          se.printStackTrace();
    	       } catch(Exception e) {
    	          //Handle errors for Class.forName
    	          e.printStackTrace();
    	       }
    	      if(valid_login) {
    	      RequestDispatcher view = request.getRequestDispatcher("html/details.html");
    	      view.forward(request, response);
    	      }
    	      else {
        	      RequestDispatcher view = request.getRequestDispatcher("index.html");
        	      view.forward(request, response);
    	      }
    	   }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {

    	      doGet(request, response);
    	   }

}