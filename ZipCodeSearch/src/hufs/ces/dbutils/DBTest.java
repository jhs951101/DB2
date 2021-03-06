package hufs.ces.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class DBTest {

	public static void main(String[] args) {

		Connection conn = null;
		try {
			conn = new DBConn().getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String SQL = "SELECT zipcode, sido, sigungu, eubmyundong, doromyung "
				+ "FROM zipcode_kor2017 "
				+ "WHERE sido like '경기도%' "
				+ "LIMIT 100; ";

		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(SQL);

			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				String zipcode = rs.getString("zipcode");
				String sido = rs.getString("sido");
				String sigungu = rs.getString("sigungu");
				String eubmyundong = rs.getString("eubmyundong");
				String doromyung = rs.getString("doromyung");

				System.out.format("%s	%s	%s	%s	%s", zipcode, sido, sigungu, eubmyundong, doromyung);
				System.out.println();

			}            
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt!=null) pstmt.close();
				if (conn!=null) conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}	
	}

}
