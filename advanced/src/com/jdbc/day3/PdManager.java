package com.jdbc.day3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import com.db.DBUtil;
import com.pd.model.PdDAO;
import com.pd.model.PdDTO;

public class PdManager {
	private Scanner sc = new Scanner(System.in);
	private PdDAO pdDao;
	
	public PdManager(){
		pdDao=new PdDAO();
	}
	
	public void showAll() throws SQLException {
		//1. 
		//2.
		List<PdDTO> list = pdDao.selectAll();
		
		//3.
		for(int i=0;i<list.size();i++) {
			PdDTO dto = list.get(i);
			
			System.out.println(dto.getNo()+"\t"+dto.getPdName()+"\t"+dto.getPrice()+"\t"+dto.getRegdate());
		}//for
		
		System.out.println();

	}


	public void register() throws SQLException {
		//1. 사용자 입력 받기
		System.out.println("상품명, 가격 입력!");
		String pdName=sc.nextLine();
		String sPrice=sc.nextLine();
		int price=0;
		if(sPrice!=null && !sPrice.isEmpty()) {
			price=Integer.parseInt(sPrice);
		}

		//2. db 작업
		PdDTO dto = new PdDTO();//빈껍데기
		dto.setPdName(pdName);//값세팅
		dto.setPrice(price);
		int cnt=pdDao.insertPd(dto);

		//3. 결과 처리
		String result=(cnt>0)?"등록 성공":"등록 실패";
		System.out.println(result);
	}


	public void showByNo() throws NumberFormatException, SQLException {
		//1.사용자 입력받기 
		System.out.println("조회할 번호 입력!");
		String sNo = sc.nextLine();
		
		//유효성 검사(필수로 입력받아야 할때 유효성 검사함)
		if(sNo==null || sNo.isEmpty()) {
			System.out.println("번호 입력해야 함!!!");
			return;
		}
		
		//2.db작업
		PdDTO dto = pdDao.selectByNo(Integer.parseInt(sNo));
		
		//3.결과처리
		System.out.println("번호:"+dto.getNo());
		System.out.println("상품명:"+dto.getPdName());
		System.out.println("가격:"+dto.getPrice());
		System.out.println("등록일:"+dto.getRegdate()+"\n");
	}

	public void edit() throws SQLException {
		//1.사용자 입력받기
		System.out.println("수정하려는 번호, 상품명, 가격 입력!");
		String sNo = sc.nextLine();
		String pdName = sc.nextLine();
		String sPrice = sc.nextLine();
		int price=0;
		if(sPrice!=null && !sPrice.isEmpty()) {
			price=Integer.parseInt(sPrice);
		}
		
		//유효성 검사
		if(sNo==null || sNo.isEmpty()) {
			System.out.println("번호 입력해주세요!");
			return;
		}
		
		//2.db작업
		
		PdDTO dto = new PdDTO();
		dto.setNo(Integer.parseInt(sNo));
		dto.setPdName(pdName);
		dto.setPrice(price);
		
		int cnt = pdDao.updatePd(dto);
		
		
		//3.결과처리
		String result=(cnt>0)?"업데이트 성공":"업데이트 실패";
		System.out.println(result);
	}

	public void showByname() throws SQLException {
		//1.사용자 입력받기
		System.out.println("조회할 이름 입력!");
		String pdName = sc.nextLine();
		
		//2. db작업
		List<PdDTO> list = pdDao.selectByName(pdName);
		
		//3.결과처리
		for(PdDTO dto : list) {
			System.out.println(dto.getNo()+"\t"+dto.getPdName()+"\t"+dto.getPrice()+"\t"+dto.getRegdate());
		}
		System.out.println();
	}

	public void delete() throws NumberFormatException, SQLException {
		//1.사용자 입력받기
		System.out.println("삭제할 번호 입력!");
		String no=sc.nextLine();
		
		if(no==null || no.isEmpty()) {
			System.out.println("번호 입력해야 함!!");
			return;
		}
		
		//2. db작업
		int cnt=pdDao.deletePd(Integer.parseInt(no));
		
		//3. 결과처리
		String result="";
		if(cnt>0) {
			result="상품 삭제 성공";
		}else {
			result="상품 삭제 실패";
		}
		
		System.out.println(result);
	}
	
	public void delete2() throws NumberFormatException, SQLException {
		//1.사용자 입력받기
		System.out.println("삭제할 번호 입력!");
		String no=sc.nextLine();
		
		if(no==null || no.isEmpty()) {
			System.out.println("번호 입력해야 함!!");
			return;
		}
		
		//2. db작업
		PdDTO dto = new PdDTO();//빈껍데기
		dto.setNo(Integer.parseInt(no));//값세팅
		int cnt = pdDao.deletePd2(dto);
		
		//3. 결과처리
		String result="";
		if(cnt>0) {
			result="상품 삭제 성공";
		}else {
			result="상품 삭제 실패";
		}
		
		System.out.println(result);
	}
}