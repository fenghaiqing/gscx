<%@page import="java.sql.*"%>
<%@page import="java.text.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	String fullname = request.getParameter("fullname");
	String age = request.getParameter("age");
	String address = request.getParameter("address");

	Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String newtime = format.format(new java.util.Date());

	try {
		Class.forName("org.gjt.mm.mysql.Driver");
		String user = "root";
		String dbpass = "11298014";
		String url = "jdbc:mysql://localhost/usercenter";
		Connection con = DriverManager.getConnection(url, user, dbpass);

		String sql = "insert into account(username,password,created_time) values(?,?,?)";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, username);
		ps.setString(2, password);
		ps.setString(3, newtime);
		ps.executeUpdate();

		String sql3 = "select * from account where username=? and password=?";
		PreparedStatement ps3 = con.prepareStatement(sql3);
		ps3.setString(1,username);
		ps3.setString(2,password);
		ResultSet rs = ps3.executeQuery();
		
		
		if (rs.next()) {
			int accid = rs.getInt(1);
			System.out.print(accid);
			String sql2 = "insert into user_info(fullname,age,address,accid) values(?,?,?,?)";
			PreparedStatement ps2 = con.prepareStatement(sql2);
			ps2.setString(1,fullname);
			ps2.setInt(2, Integer.parseInt(age));
			ps2.setString(3, address);
			ps2.setInt(4, accid);
			ps2.executeUpdate();
			
			out.print("注册成功~");
		}

	} catch (Exception e) {
		out.print(e);
	}
%>