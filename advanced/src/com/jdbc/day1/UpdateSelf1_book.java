package com.jdbc.day1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateSelf1_book {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("������Ʈ �� ��ȣ, ����, ������ �Է��ϼ���");
		int no = sc.nextInt();
		sc.nextLine();
		String title = sc.nextLine();
		int price = sc.nextInt();
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			//1. ����̹� �ε�
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� �ε� ����!");
			
			//2. DB ������ �����ϱ� ���� Connection ��ü ����
			String url = "jdbc:oracle:thin:@DESKTOP-RIB5UEF:1521:xe";
			String user = "javauser", pwd = "javauser123";
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB���� ����!");
			
			//3. sql���� ó���ϱ� ���� PreparedStatement ��ü ����
			String sql = "update book set title=?, price=? where no=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, title);
			ps.setInt(2, price);
			ps.setInt(3, no);
			
			int cnt = ps.executeUpdate();
			System.out.println("ó���� ���� ����"+cnt);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
