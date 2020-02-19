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
    	       PrintWriter out = response.getWriter();
    	      boolean valid_login = false;
    	      try {
                  Connection conn = DatabaseConnection.initializeDatabase(); 
    	    	  String n=request.getParameter("Username");  
    	    	  String p=request.getParameter("Password");  
    	          PreparedStatement ps=conn.prepareStatement(  "SELECT nickname,Pass FROM test.Customers WHERE nickname=? AND Pass=?"); 
    	          ps.setString(1, n);
    	          ps.setString(2, p);
    	          ResultSet rs=ps.executeQuery();  
    	          valid_login=rs.next();    
    	          conn.close();
    	       } catch(SQLException se) {
    	          //Handle errors for JDBC
    	          se.printStackTrace();
    	       } catch(Exception ne) {
    	          //Handle errors for Class.forName
    	          ne.printStackTrace();
    	       }
    	      if(valid_login) {
	    	      RequestDispatcher view = request.getRequestDispatcher("html/details.html");
	    	      view.forward(request, response);
    	      }
    	      else {
    	    	  response.sendRedirect("http://localhost:8080/WebPage/");
    	      }
    	   }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {

    	      doGet(request, response);
    	   }

}