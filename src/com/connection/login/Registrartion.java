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

import com.connection.login.DatabaseConnection;

@WebServlet("/Registration")
public class Registrartion extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {  
    	       response.setContentType("text/html");      
    	       PrintWriter pwriter = response.getWriter();
    	       boolean valid_reg = false;
    	      try {
    	    	  String n=request.getParameter("Username");
    	    	  String p=request.getParameter("Password");  
    	    	  String e=request.getParameter("Email");  
                  Connection conn = DatabaseConnection.initializeDatabase(); 
    	          PreparedStatement reg_Ps=conn.prepareStatement(  "SELECT nickname,email FROM test.Customers WHERE nickname=? OR email=?"); 
    	          reg_Ps.setString(1, n);
    	          reg_Ps.setString(2, p);
    	          ResultSet rs=reg_Ps.executeQuery();  
    	          valid_reg=rs.next();
    	          if(!valid_reg) {
	    	          PreparedStatement ps=conn.prepareStatement(  "insert into test.Customers (nickname,email,Pass) VALUES(?,?,?)"); 
	    	          ps.setString(1, n);
	    	          ps.setString(2, e);
	    	          ps.setString(3, p);
	    	          int i=ps.executeUpdate();  
	    	          if(i>0) {  
	    	        	  System.out.print("You are successfully registered...");  
	    	          }
    	          }
    	          conn.close();
    	       } catch(SQLException se) {
    	          //Handle errors for JDBC
    	          se.printStackTrace();
    	       } catch(Exception es) {
    	          //Handle errors for Class.forName
    	          es.printStackTrace();
    	       }
    	       response.sendRedirect("http://localhost:8080/WebPage/");
    	   }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {

    	      doGet(request, response);
    	   }

}