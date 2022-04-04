package com.jdbc.day2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ProcedureTest1 {

	
	public static void main(String[] args) {
		//exec personInsert('�Ѳ�','010-0100-1234');
		
		Scanner sc = new Scanner(System.in);
		System.out.println("�̸�, ��ȭ��ȣ �Է�!");
		String name = sc.nextLine();
		String tel = sc.nextLine();
		
		Connection con = null;
		CallableStatement cs=null; //���ν����� CallableStatement����
		try {
			//1.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� ���� ����");
			
			//2.
			String url="jdbc:oracle:thin:@DESKTOP-RIB5UEF:1521:xe";
			String user="javauser",pwd="javauser123";
			con=DriverManager.getConnection(url, user, pwd);
			System.out.println("DB���� ����");
			
			//3
			String sql="call personInsert(?,?)"; //���ν����� exec�� call�� �ٲ۴�
			cs=con.prepareCall(sql); //call�� ��������
			
			//in parameter setting
			cs.setString(1, name);
			cs.setString(2, tel);
			
			//4
			boolean bool = cs.execute();
			System.out.println("���ν��� ���� ���:"+bool); //false
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