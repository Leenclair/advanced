package com.pd.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.db.DBUtil;

public class BookConsole {
	static Scanner sc = new Scanner(System.in);

	private static void showMenu() {
		System.out.println("------------------Main Menu------------------");
		System.out.println("1.입력  2.수정  3.삭제  4.번호로 조회  5.제목으로 조회  6.종료");
		System.out.println("---------------------------------------------");
		System.out.println("번호를 입력하세요");
	}

	public static void main(String[] args){					
		while(true) {				
			showMenu();		
			String str=sc.nextLine();

			try {
				if(str==null || str.isEmpty()) throw new Exception();

				int sel=Integer.parseInt(str);

				switch(sel) {
				case 1:
					register();							
					break;
				case 2:
					update();
					break;
				case 3:
					delete();							
					break;
				case 4:
					searchByNo();
					break;
				case 5:
					selectTitle();
					break;
				case 6:
					System.exit(0);
				default:
					System.out.println("잘못 입력");
				}
			}catch (SQLException e) {
				e.printStackTrace();				
			}catch(Exception e) {
				System.out.println("잘못 입력");
				continue;
			}
		}//while

	}//main



	private static List<BookDTO> selectTitle() throws SQLException{
		System.out.println("책 제목을 입력하세요");
		String title = sc.nextLine();
		Connection con= null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		List<BookDTO> list = new ArrayList<BookDTO>();
		BookDTO dto = new BookDTO();

		try {
			con=DBUtil.getConnection();

			String sql="select * from book where title=?";
			ps=con.prepareStatement(sql);

			ps.setString(1, title);

			rs = ps.executeQuery();
			while(rs.next()) {
				int no=rs.getInt(1);
				title=rs.getString(2);
				int price = rs.getInt(3);
				Timestamp joindate = rs.getTimestamp(4);

				dto.setNo(no);
				dto.setTitle(title);
				dto.setPrice(price);
				dto.setJoindate(joindate);
				
				list.add(dto);

			}
			System.out.println("상품전체 조회 결과 dto="+dto+"list.size()="+list.size());
			
			return list;
		}finally {
			DBUtil.dbClose(rs, ps, con);
		}		
	}

	private static BookDTO searchByNo() throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		BookDTO dto = new BookDTO();
		
		try {
			System.out.println("조회할 번호 입력!");
			String sNo = sc.nextLine();
			
			int no=0;
			if(sNo!=null || !sNo.isEmpty()) {
				no = Integer.parseInt(sNo);
			}
			
			con = DBUtil.getConnection();
			
			String sql="select * from book where no=?";
			ps=con.prepareStatement(sql);
			
			ps.setInt(1, no);
			
			rs=ps.executeQuery();
			if(rs.next()) {
				int price=rs.getInt("price");
				String title=rs.getString("title");
				Timestamp regdate=rs.getTimestamp("joindate");
				
				dto.setNo(no);
				dto.setTitle(title);
				dto.setPrice(price);
				dto.setJoindate(regdate);
			}
			
			System.out.println("번호로 조회 결과 dto="+dto+", 매개변수 no="+no);
			
			return dto;
			}finally {
			DBUtil.dbClose(rs, ps, con);
		}
	}

	private static int delete() throws SQLException {
		Connection con =null;
		PreparedStatement ps=null;
		
		con=DBUtil.getConnection();
		
		try {
		
		System.out.println("삭제할 번호 입력하세요");
		String sNo = sc.nextLine();
		
		String sql="delete from book where no=?";
		ps=con.prepareStatement(sql);
		
		ps.setInt(1, Integer.parseInt(sNo));

		int cnt = ps.executeUpdate();
		System.out.println("처리된 행의 개수 cnt="+cnt);
		
		return cnt;
		}finally {
			DBUtil.dbClose(ps, con);
		}
		
	}

	private static int update() throws SQLException {
		Connection con= null;
		PreparedStatement ps=null;
		
		con=DBUtil.getConnection();
		
		try {
		
		System.out.println("업데이트 할 책 번호, 제목, 가격을 입력하세요");
		String sNo = sc.nextLine();
		String title = sc.nextLine();
		String sPrice = sc.nextLine();
		
		int price=0;
		if(sPrice!=null && !sPrice.isEmpty()) {
			price=Integer.parseInt(sPrice);
		}
				
		String sql="update book set title=?, price=? where no=?";
		ps=con.prepareStatement(sql);
		
		ps.setString(1, title);
		ps.setInt(2, price);
		ps.setInt(3, Integer.parseInt(sNo));

		int cnt = ps.executeUpdate();
		System.out.println("처리된 행의 개수 cnt="+cnt);
		
		return cnt;
		}finally {
			DBUtil.dbClose(ps, con);
		}
		
	}

	private static void register() throws SQLException {
		System.out.println("책 제목, 가격을 입력하세요");
		String title = sc.nextLine();
		String sprice = sc.nextLine();
		
		int price=0;
		if(sprice!=null || !sprice.isEmpty()) {
			price = Integer.parseInt(sprice);
		}
		
		int cnt = insertBook(title, price);
		
		String result=(cnt>0)?"등록 성공":"등록 실패";
	    System.out.println(result);
	}
	
	
	
	public static int insertBook(String title, int price) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		
		try {
			//1,2
			con=DBUtil.getConnection();
			
			//3
			String sql = "insert into book(no, title, price) values(book_seq.nextval,?,?)";
			ps=con.prepareStatement(sql);
			
			ps.setString(1, title);
			ps.setInt(2, price);
			
			//4
			int cnt = ps.executeUpdate();
			System.out.println("처리된 행의 개수 cnt="+ cnt);
			
			return cnt;
		}finally {
			DBUtil.dbClose(ps, con);
		}
	}
}