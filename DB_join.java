package chattingprogram;

import static chattingprogram.Window_join.jo;
import static chattingprogram.Window_join.tf;
import static chattingprogram.Window_join.tf1;
import static chattingprogram.Window_join.tf2;
import static chattingprogram.Window_join.tf10;
import static chattingprogram.Window_join.tf11;
import static chattingprogram.Window_join.tf12;
import static chattingprogram.Window_join.tf3;
import static chattingprogram.Window_join.tf4;
import static chattingprogram.Window_join.tf5;
import static chattingprogram.Window_join.tf6;
import static chattingprogram.Window_join.tf7;
import static chattingprogram.Window_join.tf8;
import static chattingprogram.Window_join.tf9;
import static chattingprogram.Window_join.jl;
import static chattingprogram.Window_join.warn;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import Jdbc.DBConnect;

public class DB_join implements ActionListener {
	private ResultSet rs = null; // SELECT문을 통해서 데이터를 끌어온다면 ResultSet 객체에 그 데이터를 저장해야 한다
	private ResultSet rs2 = null; // SELECT문을 통해서 데이터를 끌어온다면 ResultSet 객체에 그 데이터를 저장해야 한다
	private Statement stmt = null; // 데이터베이스에 쿼리를 보내기 위해 필요한 객체

	static Dialog infoid = new Dialog(jo, "Information", false);
	static Dialog infopw = new Dialog(jo, "Information", false);
	static Dialog infopid = new Dialog(jo, "Information", false);

	static Button ok = new Button("확인"); // 중복된
	String sex = "";

	public DB_join() {

		///////////////////////////////////
		DBConnect connect = new DBConnect(); // DBConnect클래스의 생성사 함수(메소드) 호출
		stmt = connect.getStatement();
		////////////// DB에 연결 /////////////

	}

	public void IDcheck() {

		infoid.setSize(200, 110);
		infoid.setResizable(false);
		infoid.setLocation(50, 50);
		infoid.setLayout(new FlowLayout());

		try {
			String sql = "select* from CHATTING where ID = '" + tf.getText() + "'";

			stmt = DBConnect.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			rs.last(); // 커서의 위치를 제일 뒤로 이동
			int rowCount = rs.getRow(); // 현재 커서의 Row Index 값을 저장
			if (rowCount >= 1) {
				Label msgid = new Label("'" + tf.getText() + "'는 이미 있는 ID입니다.", Label.CENTER);
				infoid.add(msgid);
				infoid.add(ok);
				infoid.setVisible(true);
				tf.setText("");
				System.out.println("ID재입력바람");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						infoid.setVisible(false);
						infoid.dispose();
						msgid.setVisible(false);
					}
				});
			} else if (tf.getText().equals("")) {
				Label msgid = new Label("빈칸에 ID를 입력해주세요.", Label.CENTER);
				infoid.add(msgid);
				infoid.add(ok);
				infoid.setVisible(true);
				System.out.println("ID입력바람");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						infoid.setVisible(false);
						infoid.dispose();
						msgid.setVisible(false);
					}
				});
			} else {
				Label msgid = new Label("'" + tf.getText() + "'는 사용가능한 ID입니다.", Label.CENTER);
				infoid.add(msgid);
				infoid.add(ok);
				infoid.setVisible(true);
				System.out.println("중복확인완료");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						infoid.setVisible(false);
						infoid.dispose();
						msgid.setVisible(false);
					}
				});
			}

		} catch (SQLException e1) {
			System.err.println("확인 에러"); // 에러시 출력
		}
	}

	public void PWcheck() {

		infopw.setSize(200, 110);
		infopw.setResizable(false);
		infopw.setLocation(50, 50);
		infopw.setLayout(new FlowLayout());

		if (tf1.getText().isEmpty() == true || tf2.getText().isEmpty() == true ) {
			
			Label msgpw = new Label("Password를 입력 바랍니다.", Label.CENTER);
			infopw.add(msgpw);
			infopw.add(ok);
			infopw.setVisible(true);
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					infopw.setVisible(false);
					infopw.dispose();
					msgpw.setVisible(false);
				}
			});
			
		} else if(tf1.getText().equals(tf2.getText())) {
			
			Label msgpw = new Label("Password가 일치합니다.", Label.CENTER);
			infopw.add(msgpw);
			infopw.add(ok);
			infopw.setVisible(true);
			System.out.println("PW일치확인완료");
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					infopw.setVisible(false);
					infopw.dispose();
					msgpw.setVisible(false);
				}
			});
			
		}
		else {
			Label msgpw = new Label("Password를 다시 입력 바랍니다.", Label.CENTER);
			infopw.add(msgpw);
			infopw.add(ok);
			infopw.setVisible(true);
			tf2.setText("");
			ok.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					infopw.setVisible(false);
					infopw.dispose();
					msgpw.setVisible(false);
				}
			});
		}

	}

	public void PIDcheck() {
		infopid.setSize(220, 100);
		infopid.setResizable(false);
		infopid.setLocation(50, 50);
		infopid.setLayout(new FlowLayout());

		try {
			String sql = "select* from CHATTING where P_ID = '" + tf4.getText() + "-" + tf5.getText() + "'";
			stmt = DBConnect.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			rs.last(); // 커서의 위치를 제일 뒤로 이동
			int rowCount = rs.getRow(); // 현재 커서의 Row Index 값을 저장
			char gender = tf5.getText().charAt(0);
			
			if (rowCount >= 1) {
				Label msgpid = new Label("이미 가입되어 있습니다.", Label.CENTER);
				infopid.add(msgpid);
				infopid.add(ok);
				infopid.setVisible(true);
				tf4.setText("");
				tf5.setText("");
				System.out.println("PID재입력바람");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						infopid.setVisible(false);
						infopid.dispose();
						msgpid.setVisible(false);
					}
				});
//			} else if (tf4.getText().isEmpty() == true || tf5.getText().isEmpty() == true) {
//				Label msgpid = new Label("주민등록번호를 입력해주세요.", Label.CENTER);
//				infopid.add(msgpid);
//				infopid.add(ok);
//				infopid.setVisible(true);
//				System.out.println("PID입력바람");
//				ok.addActionListener(new ActionListener() {
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						infopid.setVisible(false);
//						infopid.dispose();
//						msgpid.setVisible(false);
//					}
//				});
				
			} else if(tf5.getText().toString().length() > 7) {
				Label msgpid = new Label("유효하지않은 주민등록번호 입니다.", Label.CENTER);
				infopid.add(msgpid);
				infopid.add(ok);
				infopid.setVisible(true);
				tf4.setText("");
				tf5.setText("");
				System.out.println("PID재입력바람");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						infopid.setVisible(false);
						infopid.dispose();
						msgpid.setVisible(false);
					}
				});
				
			} else if(gender > '4') {
				Label msgpid = new Label("유효하지않은 주민등록번호 입니다.", Label.CENTER);
				infopid.add(msgpid);
				infopid.add(ok);
				infopid.setVisible(true);
				tf4.setText("");
				tf5.setText("");
				System.out.println("PID재입력바람");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						infopid.setVisible(false);
						infopid.dispose();
						msgpid.setVisible(false);
					}
				});
			} else {
				Label msgpid = new Label("중복확인이 완료되었습니다.", Label.CENTER);
				infopid.add(msgpid);
				infopid.add(ok);
				infopid.setVisible(true);
				System.out.println("중복확인완료");
				ok.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						infopid.setVisible(false);
						infopid.dispose();
						msgpid.setVisible(false);
					}
				});
			}

		} catch (SQLException e1) {
			System.err.println("확인 에러"); // 에러시 출력
		}
	}

	public void Finaljoin() {
		String sql, sql2;
		
		try {
			
			char gender = tf5.getText().charAt(0);
	        
	        switch(gender){
	        case '1': case '3':
	            sex = "남자";
	            break;
	        case '2': case '4':
	            sex = "여자";
	            break;
	        default:
	        	tf4.setText("");
	        	tf5.setText("");
	        	JOptionPane.showMessageDialog(null, "유효하지않은 주민등록번호 입니다.");
	        }
	        
			/////////////////////////////////
			DBConnect connect = new DBConnect(); // DBConnect클래스의 생성사 함수(메소드) 호출
			stmt = connect.getStatement();
			////////////// DB에 연결 /////////////
			sql = "select* from CHATTING where ID = '" + tf.getText() + "'"; 
			stmt = DBConnect.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(sql);
			rs.last(); // 커서의 위치를 제일 뒤로 이동
			int rowCount = rs.getRow(); // 현재 커서의 Row Index 값을 저장

			sql2 = "select* from CHATTING where P_ID = '" + tf4.getText() + "-" + tf5.getText() + "'";
			rs2 = stmt.executeQuery(sql2);

			if (rowCount >= 1) {
				JOptionPane.showMessageDialog(null, "ID 중복확인이 필요합니다.");
			} else if (tf1.getText().equals(tf2.getText()) == false) {
				JOptionPane.showMessageDialog(null, "비밀번호 확인이 필요합니다.");
			} else if (rs2.next() == true) {
				JOptionPane.showMessageDialog(null, "주민등록번호 중복확인이 필요합니다.");
			} else if (tf5.getText().toString().length() > 7) {
				JOptionPane.showMessageDialog(null, "유효하지않은 주민등록번호 입니다.");
				tf4.setText("");
				tf5.setText("");
			} else if ((tf.getText().isEmpty()) == true || (tf1.getText().isEmpty()) == true
					|| (tf2.getText().isEmpty()) == true || (tf3.getText().isEmpty()) == true
					|| (tf4.getText().isEmpty()) == true || (tf5.getText().isEmpty()) == true
					|| (tf6.getText().isEmpty()) == true || (tf7.getText().isEmpty()) == true
					|| (tf8.getText().isEmpty()) == true || (tf9.getText().isEmpty()) == true
					|| (tf10.getText().isEmpty()) == true || (tf11.getText().isEmpty()) == true) {
				JOptionPane.showMessageDialog(null, "비어있는 칸이 존재합니다.");
			}else {
			try {
				stmt.executeQuery("insert into CHATTING values('" + tf.getText() + "','" + tf2.getText() + "','"
						+ tf3.getText() + "','" + tf4.getText() + "-" + tf5.getText() + "','" + tf6.getText() + "-"
						+ tf7.getText() + "-" + tf8.getText() + "','" + tf9.getText() + "@" + tf10.getText() + "','"
						+ tf11.getText() + ", " + tf12.getText() + "','" + sex + "')");
				// statement를 통해 쿼리문 데이터베이스로 전송
				stmt.executeQuery("commit"); // 전송 후 커밋
				System.out.println("회원가입을 축하합니다.");
				JOptionPane.showMessageDialog(null, "회원가입을 축하합니다!!!");
				jo.setVisible(false);
			} catch (Exception ex) {
				System.err.println("쿼리문 에러"); // 에러시 출력
			}
			}
		} catch (SQLException e1) {
			System.err.println("DB전송실패");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
