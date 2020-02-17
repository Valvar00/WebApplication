
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

@WebServlet("/Login")
public class Login_Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {  
		      Cookie Username = new Cookie("Username", request.getParameter("Username"));
		      Username.setMaxAge(60*60*24);
		      response.addCookie( Username );
    	      if(request.getParameter("RemPass")=="on") {
	    	      Cookie Pass = new Cookie("Password", request.getParameter("Password"));
	    	      Pass.setMaxAge(60*60*24);
	    	      response.addCookie(Pass);
    	      };
    	      DB_Connection obj_DB_Connection=new DB_Connection();
    	  	  Connection conn=obj_DB_Connection.get_connection();
    	      response.setContentType("text/html");
    	      try {
    	          Statement stmt = conn.createStatement();
    	          String sql;
    	          sql = "SELECT id, first, last, age FROM Employees";
    	          ResultSet rs = stmt.executeQuery(sql);

    	          // Extract data from result set
    	          while(rs.next()){
    	             //Retrieve by column name
    	             int id  = rs.getInt("id");
    	             int age = rs.getInt("age");
    	             String first = rs.getString("first");
    	             String last = rs.getString("last");

    	             //Display values
    	             System.out.println("ID: " + id + "<br>");
    	             System.out.print(", Age: " + age + "<br>");
    	             System.out.print(", First: " + first + "<br>");
    	             System.out.print(", Last: " + last + "<br>");
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
    	      //RequestDispatcher view = request.getRequestDispatcher("html/details.html");
    	      //view.forward(request, response);
    	   }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {

    	      doGet(request, response);
    	   }

}