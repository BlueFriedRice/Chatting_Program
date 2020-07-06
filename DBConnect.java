package chattingprogram;

import java.sql.*;

public class DBConnect {

	private Connection con; // 데이터베이스와 자바의 연결을 관리하는 connection 객체
	private Statement stmt; // 데이터베이스에 쿼리를 보내기 위해 필요한 객체

	private String url = "jdbc:oracle:thin:@localhost:1521:orcl"; 
	// oracl툴을 이용한 jdbc, localhost:1521 = 오라클 포트번호, orcl =
	private String ID = "oracle_id"; // 사용자이름,id
	private String PW = "password"; // 패스워드

	public DBConnect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // JDBC Driver Loading
			con = DriverManager.getConnection(url, ID, PW); // Connect 확득
			stmt = con.createStatement(); // 명령실행을 위한 Statement 획득
		} catch (Exception ex) {
			System.err.println(ex); // 오류시
		}
	}

	public Statement getStatement() {
		return stmt;
	}
}
