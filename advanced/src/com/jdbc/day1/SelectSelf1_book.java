package com.jdbc.day1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SelectSelf1_book {

	public static void main(String[] args) {

		Connection con= null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//1.����̹� �ε�
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("�ε� ����");
			
			//2.DB ���� ���� connetion ��ü ����
			String url = "jdbc:oracle:thin:@DESKTOP-RIB5UEF:1521:xe";
			String user ="javauser", pwd="javauser123";
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("db���� ����");
			
			//3.ps
			String sql = "select * from book";
			ps = con.prepareStatement(sql);
			
			//4.����
			rs=ps.executeQuery();
			while(rs.next()) {
				int no = rs.getInt(1);
				String title=rs.getString("title");
				int price=rs.getInt("price");
				Date joindate=rs.getDate("joindate");
				
				System.out.print(no+"\t");
				System.out.print(title+"\t");
				System.out.print(price+"\t");
				System.out.print(joindate+"\n");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}