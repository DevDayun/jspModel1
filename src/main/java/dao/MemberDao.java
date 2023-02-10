package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBClose;
import db.DBConnection;
import dto.MemberDto;

public class MemberDao {
	
	private static MemberDao dao = null;
	
	private MemberDao(){
		DBConnection.initConnection();
	}
	
	public static MemberDao getInstance() {
		if(dao == null) {
			dao = new MemberDao();
		}
		return dao;
	}
	
	public boolean getId(String id) {
		
		// SQL 문장을 작성하여 sql 변수에 저장
		String sql = " select id "
				+ "    from member "
				+ "    where id=? ";
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		ResultSet rs = null;
		
		Boolean findid = false;
		
		try {
			// Connection 생성 - .getConnection(연결문자열, DB-ID, DB-PW)
			conn = DBConnection.getConnection();
			System.out.println("1/3 getId success"); // flag를 꽂음 : 단계별로 성공되는지 확인
			
			// PreParedStatement 객체 생성, 객체 생성시 SQL 문장 저장
			psmt = conn.prepareStatement(sql);
			// psmt.set<데이터타입>(순서, 값)
			psmt.setString(1, id);
			System.out.println("2/3 getId success");
			
			// SQL 문장을 실행하고 결과를 리턴
			rs = psmt.executeQuery();
			System.out.println("3/3 getId success");
			
			if(rs.next()) {
				findid = true;
			}
			
		} catch (SQLException e) {
			System.out.println("getId fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		return findid;
	}
	
	public boolean addMember(MemberDto dto) {
		
		String sql = " insert into member(id, pwd, name, email, auth) "
				+"     values(?, ?, ?, ?, 3)";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 addMember success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getPwd());
			psmt.setString(3, dto.getName());
			psmt.setString(4, dto.getEmail());
			System.out.println("2/3 addMember success");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 addMember success");
						
		} catch (SQLException e) {
			System.out.println("addMember fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}
	
		return count>0?true:false;
	}
	
	public MemberDto login(String id, String pwd) {
		
		String sql = " select id, name, email, auth "
				+ "    from member "
				+ "    where id=? and pwd=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto mem = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 login success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pwd);
			System.out.println("2/3 login success");
			
			rs = psmt.executeQuery();
			System.out.println("3/3 login success");
			
			if(rs.next()) {
				 String _id = rs.getString(1);
				 String _name = rs.getString(2);
				 String _email = rs.getString(3);
				 int _auth = rs.getInt(4);
				 
				 mem = new MemberDto(_id, null, _name, _email, _auth);
			}
			
		} catch (SQLException e) {
			System.out.println("login fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return mem;
		
	}
	
}
