<%@page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String id= request.getParameter("id");
String username= request.getParameter("username");
String password= request.getParameter("password");
String fullname= request.getParameter("fullname");
String age= request.getParameter("age");
String address= request.getParameter("address");

out.print(id+username+password+fullname+age+address);


try{
	Class.forName("org.gjt.mm.mysql.Driver");
	String user = "root";
	String dbpass = "11298014";
	String url = "jdbc:mysql://localhost/usercenter";
	Connection con = DriverManager.getConnection(url, user, dbpass);
	
	
	String sql="update account set username=?,password=? where id=?";

	PreparedStatement ps = con.prepareStatement(sql);
	ps.setString(1,username);
	ps.setString(2,password);
	ps.setInt(3,Integer.parseInt(id));
	ps.executeUpdate();
	
	String sql2="update user_info set fullname=?,age=?,address=? where accid=?";

	PreparedStatement ps2 = con.prepareStatement(sql2);
	ps2.setString(1,fullname);
	ps2.setInt(2,Integer.parseInt(age));
	ps2.setString(3,address);
	ps2.setInt(4,Integer.parseInt(id));
	ps2.executeUpdate();
	
	
}catch(Exception e)
{
	out.print(e);
	}
%>