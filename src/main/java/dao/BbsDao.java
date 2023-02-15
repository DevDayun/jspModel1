package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;

public class BbsDao {
	
	private static BbsDao dao = null;
	
	private BbsDao() {
	}
	
	public static BbsDao getInstance() {
		if(dao == null) {
			dao = new BbsDao();
		}
		return dao;
	}

	public List<BbsDto> getBbsList() {
		
		String sql = " select seq, id, ref, step, depth, "
				+"     		  title, content, wdate, del, readcount "
				+"     from bbs "
				+"     order by ref desc, step asc ";
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			// Connection 생성 - .getConnection(연결문자열, DB-ID, DB-PW)
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsList success"); // flag를 꽂음 : 단계별로 성공되는지 확인
			
			// PreParedStatement 객체 생성, 객체 생성시 SQL 문장 저장
			psmt = conn.prepareStatement(sql);			
			System.out.println("2/4 getBbsList success");
			
			// SQL 문장을 실행하고 결과를 리턴
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsList success");
			
			while(rs.next()) {
				
				BbsDto dto = new BbsDto(rs.getInt(1), 
										rs.getString(2), 
										rs.getInt(3), 
										rs.getInt(4), 
										rs.getInt(5), 
										rs.getString(6), 
										rs.getString(7), 
										rs.getString(8), 
										rs.getInt(9), 
										rs.getInt(10));
				
				list.add(dto);
			}
			System.out.println("4/4 getBbsList success");
			
		} catch (SQLException e) {
			System.out.println("getBbsList fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
		
	}
	
	public List<BbsDto> getBbsSearchList(String choice, String search) {
		
		String sql = " select seq, id, ref, step, depth,"
				+ "			  title, content, wdate, del, readcount "
				+ "    from bbs ";
		
		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%'";
		}
		else if(choice.equals("content")) {
			searchSql = " where content like '%" + search + "%'";
		} 
		else if(choice.equals("writer")) {
			searchSql = " where id='" + search + "'"; 
		} 
		sql += searchSql;
		
		sql += "    order by ref desc, step asc ";
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			// Connection 생성 - .getConnection(연결문자열, DB-ID, DB-PW)
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsSearchList success"); // flag를 꽂음 : 단계별로 성공되는지 확인
			
			// PreParedStatement 객체 생성, 객체 생성시 SQL 문장 저장
			psmt = conn.prepareStatement(sql);			
			System.out.println("2/4 getBbsSearchList success");
			
			// SQL 문장을 실행하고 결과를 리턴
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsSearchList success");
			
			while(rs.next()) {
				
				BbsDto dto = new BbsDto(rs.getInt(1), 
										rs.getString(2), 
										rs.getInt(3), 
										rs.getInt(4), 
										rs.getInt(5), 
										rs.getString(6), 
										rs.getString(7), 
										rs.getString(8), 
										rs.getInt(9), 
										rs.getInt(10));
				
				list.add(dto);
			}
			System.out.println("4/4 getBbsSearchList success");
			
		} catch (SQLException e) {
			System.out.println("getBbsSearchList fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;
		
	}
	
	// 글의 총 개수 : 반환 값이 정수형이므로 int
	public int getAllBbs(String choice, String search) {
		String sql = " select count(*) from bbs ";
		
		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%'";
		}
		else if(choice.equals("content")) {
			searchSql = " where content like '%" + search + "%'";
		} 
		else if(choice.equals("writer")) {
			searchSql = " where id='" + search + "'"; 
		} 
		sql += searchSql;
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		ResultSet rs = null; //ResultSet 객체는 행 단위로 저장되며, 현재 행을 가리키는 커서cursor를 통해 값을 읽어오는 구조입니다.
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery(); // 쿼리문을 실행. 실행 결과는 ResultSet 객체로 반환
			
			if(rs.next()) { // 커서를 첫 번째 행으로 이동
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return count;
		
	}
	
	public List<BbsDto> getBbsPageList(String choice, String search, int pageNumber) {
		
		String sql = " select seq, id, ref, step, depth, title, content, wdate, del, readcount "
				+ " from "
				+ " (select row_number()over(order by ref desc, step asc) as rnum,"
				+ "	seq, id, ref, step, depth, title, content, wdate, del, readcount "
				+ " from bbs ";

		String searchSql = "";
		if(choice.equals("title")) {
			searchSql = " where title like '%" + search + "%' ";
		}
		else if(choice.equals("content")) {
			searchSql = " where content like '%" + search + "%' ";
		} 
		else if(choice.equals("writer")) {
			searchSql = " where id='" + search + "' "; 
		} 
		sql += searchSql;
		
		// 게시판 목록은 항상 최근 게시물이 상단에 출력되므로 
		// 그룹 번호 컬럼 ref을 내림차순(DESC)으로 정렬한 것입니다. 
		sql += 	  " order by ref desc, step asc) a "
				+ " where rnum between ? and ? ";
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		ResultSet rs = null;
		
		// pageNumber
		int start, end;
		start = 1 + 10 * pageNumber;
		end = 10 + 10 * pageNumber;
		
		// 테이블에서 레코드를 가져올 때는 항상 List 계열의 컬렉션에 저장합니다.
		// List 컬렉션에는 데이터가 순서대로 저장되어 인덱스를 통해 가져올 수 있기 때문입니다.
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			// Connection 생성 - .getConnection(연결문자열, DB-ID, DB-PW)
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbsPageList success"); // flag를 꽂음 : 단계별로 성공되는지 확인
			
			// PreParedStatement 객체 생성, 객체 생성시 SQL 문장 저장
			psmt = conn.prepareStatement(sql);	
			psmt.setInt(1, start);
			psmt.setInt(2, end);
			System.out.println("2/4 getBbsPageList success");
			
			// SQL 문장을 실행하고 결과를 리턴
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbsPageList success");
			
			while(rs.next()) {// 결과를 순화하며 한 행(게시물 하나)의 내용을 DTO에 저장
				
				BbsDto dto = new BbsDto(rs.getInt(1), 
										rs.getString(2), 
										rs.getInt(3), 
										rs.getInt(4), 
										rs.getInt(5), 
										rs.getString(6), 
										rs.getString(7), 
										rs.getString(8), 
										rs.getInt(9), 
										rs.getInt(10));
				
				list.add(dto); // 결과 목록에 저장
			}
			System.out.println("4/4 getBbsPageList success");
			
		} catch (SQLException e) {	
			System.out.println("getBbsList fail");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list;		
	}
		
	public boolean writeBbs(BbsDto dto) {
		
		String sql = " insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount)\r\n"
				+ "	   values(?, "
				+ "		(select ifnull(max(ref),0)+1 from bbs b), 0, 0,"
				+ "		?,?, now(), 0, 0); ";
	
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 writeBbs success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			System.out.println("2/3 writeBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 writeBbs success");
						
		} catch (SQLException e) {
			System.out.println("writeBbs fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
		
	}
	
	// bbsdetail(글 상세보기)
	public BbsDto getBbs(int seq) {
		String sql = " select seq, id, ref, step, depth,"
				+ "			  title, content, wdate, del, readcount "
				+ "    from bbs"
				+ "    where seq=? ";
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		ResultSet rs = null;
		
		BbsDto dto = null;
		
		try {
			// Connection 생성 
			conn = DBConnection.getConnection();
			System.out.println("1/4 getBbs success"); // flag를 꽂음 : 단계별로 성공되는지 확인
			
			// PreParedStatement 객체 생성, 객체 생성시 SQL 문장 저장
			psmt = conn.prepareStatement(sql);	
			psmt.setInt(1, seq);
			System.out.println("2/4 getBbs success");
			
			// SQL 문장을 실행하고 결과를 리턴
			rs = psmt.executeQuery();
			System.out.println("3/4 getBbs success");
			
			if(rs.next()) {
				
				dto = new BbsDto(rs.getInt(1), 		//seq
								 rs.getString(2), 	//id
								 rs.getInt(3), 		//ref
								 rs.getInt(4), 		//step
								 rs.getInt(5), 		//depth
								 rs.getString(6), 	//title
								 rs.getString(7), 	//content
								 rs.getString(8), 	//wdate
								 rs.getInt(9), 		//del
								 rs.getInt(10));	//readcount
								
			}						
			System.out.println("4/4 getBbs success");
			
		} catch (SQLException e) {
			System.out.println("getBbs fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return dto;
				
	}
	
	// readcount 증가 
	 public void updateReadCount(int seq) {
        // 쿼리문 준비 
        String sql = " update bbs "
                   + " set readcount=readcount+1 "
                   + " where seq=?";
        
        Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		
        try {
        	conn = DBConnection.getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, seq); // 인파라미터를 일련번호로 설정
            psmt.execute(); // 쿼리 실행
        }
        catch (SQLException e) {
            System.out.println("updateReadCount fail");
            e.printStackTrace();
        }finally {
			DBClose.close(conn, psmt, null);
		}
  	}
		
	// 답글달기 매서드
	public boolean answer(int seq, BbsDto dto) {
		
		// update
		String sql1 = " update bbs "
				+ " set step=step+1 "
				+ " where ref=(select ref from (select ref from bbs a where seq=?) A) "
				+ "	  and step>(select step from (select step from bbs b where seq=?) B) ";
		
		// insert
		String sql2 = " insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount) "
				+ " values(?, "
				+ "		(select ref from bbs a where seq=?), "
				+ "		(select step from bbs b where seq=?)+1, "
				+ "		(select depth from bbs c where seq=?)+1, "
				+ "		?, ?, now(), 0, 0) ";
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		int count1 = 0;
		int count2 = 0;
		
		try {
			conn = DBConnection.getConnection();
			// commit을 비활성화			commit(=확정+적용) <-> rollback
			conn.setAutoCommit(false);
			
			// update
			// PreParedStatement 객체 생성, 객체 생성시 SQL 문장 저장
			psmt = conn.prepareStatement(sql1);	
			psmt.setInt(1, seq);
			psmt.setInt(2, seq);
			
			count1 = psmt.executeUpdate();
			
			// psmt 초기화
			psmt.clearParameters();
			
			// insert
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, dto.getId());
			psmt.setInt(2, seq);
			psmt.setInt(3, seq);
			psmt.setInt(4, seq);
			psmt.setString(5, dto.getTitle());
			psmt.setString(6, dto.getContent());
			
			count2 = psmt.executeUpdate();
			
			conn.commit();
						
		} catch (SQLException e) {
			try {
				conn.rollback();
			}
			catch (SQLException se) {
				se.printStackTrace();
			} e.printStackTrace();
		}finally {
			try {
				conn.setAutoCommit(true);				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBClose.close(conn, psmt, null);
		}
		
		if(count2 > 0) {
			return true;
		}else {
			return false;
		}

	}
	
	public boolean updateBbs(int seq, String title, String content) {
		String sql = " update bbs "
				+ "    set title=?, content=? "
				+ "    where seq=? ";
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 updateBbs success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setInt(3, seq);
			System.out.println("2/3 updateBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 updateBbs success");
			
		} catch (SQLException e) {
			System.out.println("updateBbs fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}
		return count>0?true:false;
		
	}
	
	public boolean deleteBbs(int seq) {
		String sql = " update bbs "
				+ "    set del=1 "
				+ "    where seq=? ";
		
		Connection conn = null; // 데이터 베이스와 연결을 위한 객체
		PreparedStatement psmt = null; // SQL문을 데이터베이스에 보내기위한 객체
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/3 deleteBbs success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/3 deleteBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("3/3 deleteBbs success");
			
		} catch (SQLException e) {
			System.out.println("deleteBbs fail");
			e.printStackTrace();
		}finally {
			DBClose.close(conn, psmt, null);
		}		
		return count>0?true:false;
	}
	
}
