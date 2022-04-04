package com.jdbc.day2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ExecuteInsert {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("��ǰ��, ���� �Է�!");
		String pdname = sc.nextLine();
		int price = sc.nextInt();
		
		Connection con = null;
		PreparedStatement ps=null;
		try {
			//1. ����̹� �ε�
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("����̹� �ε� ����");
			
			//2. DB ���� �������� connection ��ü ����
			String url="jdbc:oracle:thin:@DESKTOP-RIB5UEF:1521:xe";
			String user="javauser", pwd = "javauser123";
			con=DriverManager.getConnection(url, user, pwd);
			System.out.println("DB���� ����");
			
			//3.sql�� ó���ϱ� ���� ps��ü ����
			String sql="insert into pd(no, pdname, price)"
					+ " values(pd_seq.nextval, ?,?)";
			ps=con.prepareStatement(sql);
			
			//in parameter setting
			ps.setString(1, pdname);
			ps.setInt(2, price);
			
			//4.����
			boolean bool=ps.execute();
			System.out.println("bool="+bool);
			if(!bool) {//select���� �ƴϸ�
				int cnt=ps.getUpdateCount(); //DML���� ���� ó���� ���� ���� ��ȯ
				System.out.println("ó���� ���� ����:"+cnt);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null) ps.close();
				if(con!=null) con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}