package chattingprogram;

import java.sql.*;

public class DBConnect {

	private Connection con; // �����ͺ��̽��� �ڹ��� ������ �����ϴ� connection ��ü
	private Statement stmt; // �����ͺ��̽��� ������ ������ ���� �ʿ��� ��ü

	private String url = "jdbc:oracle:thin:@localhost:1521:orcl"; 
	// oracl���� �̿��� jdbc, localhost:1521 = ����Ŭ ��Ʈ��ȣ, orcl =
	private String ID = "hr_hongsam"; // ������̸�,id
	private String PW = "asd1861511"; // �н�����

	public DBConnect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // JDBC Driver Loading
			con = DriverManager.getConnection(url, ID, PW); // Connect Ȯ��
			stmt = con.createStatement(); // ���ɽ����� ���� Statement ȹ��
		} catch (Exception ex) {
			System.err.println(ex); // ������
		}
	}

	public Statement getStatement() {
		return stmt;
	}
}