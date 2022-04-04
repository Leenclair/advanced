package com.jdbc.day1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UpdateSelf1_book {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("업데이트 할 번호, 제목, 가격을 입력하세요");
		int no = sc.nextInt();
		sc.nextLine();
		String title = sc.nextLine();
		int price = sc.nextInt();
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			//1. 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로딩 성공!");
			
			//2. DB 서버에 연결하기 위한 Connection 객체 생성
			String url = "jdbc:oracle:thin:@DESKTOP-RIB5UEF:1521:xe";
			String user = "javauser", pwd = "javauser123";
			con = DriverManager.getConnection(url, user, pwd);
			System.out.println("DB연결 성공!");
			
			//3. sql문을 처리하기 위한 PreparedStatement 객체 생성
			String sql = "update book set title=?, price=? where no=?";
			ps = con.prepareStatement(sql);
			
			ps.setString(1, title);
			ps.setInt(2, price);
			ps.setInt(3, no);
			
			int cnt = ps.executeUpdate();
			System.out.println("처리된 행의 개수"+cnt);
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
