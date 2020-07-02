package chattingprogram;

import static chattingprogram.Window_join.tf;
import static chattingprogram.Window_join.tf4;
import static chattingprogram.Window_join.tf5;

import java.awt.*;
import javax.swing.*;
import Jdbc.DBConnect;

import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


class TestPanel extends JPanel {
	ImageIcon icon = new ImageIcon("myImage/cc.jpeg");
	Image img = icon.getImage();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, 490, 120, Color.BLUE, this);

		}
}

public class Window_login extends JFrame {

	public static void main(String[] arg) {
		Window_login al = new Window_login();
	}

	private static ResultSet rs = null; // SELECT문을 통해서 데이터를 끌어온다면 ResultSet 객체에 그 데이터를 저장해야 한다
	private static ResultSet rs2 = null; // SELECT문을 통해서 데이터를 끌어온다면 ResultSet 객체에 그 데이터를 저장해야 한다
	private static Statement stmt = null; // 데이터베이스에 쿼리를 보내기 위해 필요한 객체
	private JLabel gong = new JLabel(" ");
	private JLabel lb = new JLabel("Login Module", JLabel.CENTER);
	private JLabel lb1 = new JLabel("ID :", JLabel.CENTER);
	private JLabel lb2 = new JLabel("PW :", JLabel.CENTER);
	public static JTextField tf = new JTextField(15);
	public static JTextField tf1 = new JPasswordField(15);
	JButton bt = new JButton("회원가입");
	private JButton bt1 = new JButton("로그인");
	JFrame login = new JFrame("Chatting Program");
	String nickName;
	Socket socket;

	public Window_login() {

		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setSize(500, 300);
		login.setResizable(false);
		login.setVisible(true);
		start();

		JPanel p = new JPanel(new GridLayout(2, 1));
		login.add(p);
		p.add(new TestPanel());
		p.setBackground(Color.blue);

		JPanel p1 = new JPanel(new GridLayout(1, 2));
		p.add(p1);
		p1.add(gong);

		JPanel p2 = new JPanel(new GridLayout(4, 1, 10, 10));
		p1.add(p2);
		p2.add(lb);

		// ID
		JPanel p3 = new JPanel(new GridLayout(1, 2));
		p2.add(p3);
		p3.add(lb1);
		p3.add(tf);

		// PW
		JPanel p4 = new JPanel(new GridLayout(1, 2));
		p2.add(p4);
		p4.add(lb2);
		p4.add(tf1);
		((JPasswordField) tf1).setEchoChar('*');

		JPanel p5 = new JPanel(new GridLayout(1, 2));
		p2.add(p5);
		p5.add(bt);
		p5.add(bt1);

	}

	public void start() {
		bt.addActionListener(new ActionListener() {
			// 버튼이 눌러지면 발생하는 행동을 정의
			@Override
			public void actionPerformed(ActionEvent e) {
				new Window_join();
			}

		});

		bt1.addActionListener(new ActionListener() {
			// 버튼이 눌러지면 발생하는 행동을 정의
			@Override
			public void actionPerformed(ActionEvent e) {
				DB_login dl = new DB_login();
				int result = dl.checkIDPW(tf.getText().toString(), tf1.getText().toString());
				if (result == 0) {
					try {
						login.setVisible(false);// 로그인창은 숨기고
						Window_chatting winchat = new Window_chatting();
						nickName = tf.getText();
						socket = new Socket("127.0.0.1", 7000); // 127.0.0.1 => localhost는 컴퓨터 네트워크에서 사용하는 루프백
																// 호스트명으로, 자신의 컴퓨터를 의미한다. IPv4에서의 IP 주소는
																// 127.0.0.1[1]이며, IPv6에서는 ::1(0:0:0:0:0:0:0:1의
																// 약자)[2]로 변환된다. 로컬 컴퓨터를 원격 컴퓨터인것 처럼 통신할 수 있어 테스트
																// 목적으로 주로 사용된다.
						winchat.socket = socket;
						winchat.nickName = nickName;
						winchat.chat.setTitle(nickName + "님의 채팅창");
						new Thread(winchat).start(); // 클라이언트쪽 스레드 시작
						winchat.setVisible(true); // 채팅창이 보이도록 설정
						winchat.tf.requestFocus(); // 로그인시 커서를 textfield 로 이동
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				try {
					String sqlID, sqlPW;
					/////////////////////////////////
					DBConnect connect = new DBConnect(); // DBConnect클래스의 생성사 함수(메소드) 호출
					stmt = connect.getStatement();
					////////////// DB에 연결 /////////////
					sqlID = "select * from (select * from CHATTING where ID='" + tf.getText() + "')";
					rs = stmt.executeQuery(sqlID);

					if (result == 1) {

						if (tf.getText().toString().length() == 0 && tf1.getText().toString().length() == 0) {

							JOptionPane.showMessageDialog(null, "ID와 PW를 입력해주세요");

						} else if (tf.getText().toString().length() == 0) {

							JOptionPane.showMessageDialog(null, "ID를 입력해주세요");

						} else if (tf1.getText().toString().length() == 0) {

							JOptionPane.showMessageDialog(null, "PW를 입력해주세요");

						} else if (rs.next() == true) {

							if (rs.getString("PW").equals(tf1.getText()) == false) {

								JOptionPane.showMessageDialog(null, "비빌번호를 확인해주세요.");
								tf1.setText("");
							}
						} else if (rs.next() == false) {

							JOptionPane.showMessageDialog(null, "존재하지않는 ID입니다.");
							tf.setText("");
							tf1.setText("");
							
						}
					}
				} catch (SQLException e1) {
					System.err.println("에러발생");
				}
			}
		});
	}

}
