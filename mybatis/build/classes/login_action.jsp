
<%@page import="java.text.*"%>

<%@page import="java.sql.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String username = request.getParameter("username");
String password = request.getParameter("password");

try{
	Class.forName("org.gjt.mm.mysql.Driver");
	String user = "root";
	String dbpass = "11298014";
	String url = "jdbc:mysql://localhost/usercenter";
	Connection con = DriverManager.getConnection(url, user, dbpass);
	String sql="select * from account where username=? and password=?";
	
	PreparedStatement ps = con.prepareStatement(sql);
	ps.setString(1,username);
	ps.setString(2,password);
	ResultSet rs=ps.executeQuery();
	if(rs.next())
	{
		out.print("登陆成功");
		out.print("<a href='userlist.jsp'>用户列表</a>");
	}
	else{
		
		out.print("登陆失败");
		out.print("<a href='login.jsp'>登陆</a>");
	}
}catch(Exception e)
{
out.print(e);	
}



%>