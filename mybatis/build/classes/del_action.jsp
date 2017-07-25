<%@page import="java.text.*"%>
<%@page import="java.sql.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String id=request.getParameter("id");
out.print(id);


try{
Class.forName("org.gjt.mm.mysql.Driver");
String user = "root";
String dbpass = "11298014";
String url = "jdbc:mysql://localhost/usercenter";
Connection con = DriverManager.getConnection(url, user, dbpass);
String sql="delete from account where id=?";

PreparedStatement ps = con.prepareStatement(sql);
ps.setString(1,id);
ps.executeUpdate();
out.print("删除成功~");
out.print("<a href='userlist.jsp'>用户列表</a>");
}catch(Exception e)
{
	out.print(e);
}
%>