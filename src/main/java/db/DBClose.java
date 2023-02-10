package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBClose {

	public static void close(Connection conn, Statement psmt, ResultSet rs) {
		try {
			//사용순서와 반대로 close 함
			if (conn != null) { 
				conn.close();
			}
			if (psmt != null) {
				psmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
