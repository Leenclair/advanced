package com.jdbc.day2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ProcedureTest1 {

	
	public static void main(String[] args) {
		//exec personInsert('뿌꾸','010-0100-1234');
		
		Scanner sc = new Scanner(System.in);
		System.out.println("이름, 전화번호 입력!");
		String name = sc.nextLine();
		String tel = sc.nextLine();
		
		Connection con = null;
		CallableStatement cs=null; //프로시저는 CallableStatement쓴다
		try {
			//1.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 연동 성공");
			
			//2.
			String url="jdbc:oracle:thin:@DESKTOP-RIB5UEF:1521:xe";
			String user="javauser",pwd="javauser123";
			con=DriverManager.getConnection(url, user, pwd);
			System.out.println("DB연결 성공");
			
			//3
			String sql="call personInsert(?,?)"; //프로시저는 exec을 call로 바꾼다
			cs=con.prepareCall(sql); //call이 세번나옴
			
			//in parameter setting
			cs.setString(1, name);
			cs.setString(2, tel);
			
			//4
			boolean bool = cs.execute();
			System.out.println("프로시저 실행 결과:"+bool); //false
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(cs!=null) cs.close();
				if(con!=null)con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}