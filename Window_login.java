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

	private static ResultSet rs = null; // SELECT���� ���ؼ� �����͸� ����´ٸ� ResultSet ��ü�� �� �����͸� �����ؾ� �Ѵ�
	private static ResultSet rs2 = null; // SELECT���� ���ؼ� �����͸� ����´ٸ� ResultSet ��ü�� �� �����͸� �����ؾ� �Ѵ�
	private static Statement stmt = null; // �����ͺ��̽��� ������ ������ ���� �ʿ��� ��ü
	private JLabel gong = new JLabel(" ");
	private JLabel lb = new JLabel("Login Module", JLabel.CENTER);
	private JLabel lb1 = new JLabel("ID :", JLabel.CENTER);
	private JLabel lb2 = new JLabel("PW :", JLabel.CENTER);
	public static JTextField tf = new JTextField(15);
	public static JTextField tf1 = new JPasswordField(15);
	JButton bt = new JButton("ȸ������");
	private JButton bt1 = new JButton("�α���");
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
			// ��ư�� �������� �߻��ϴ� �ൿ�� ����
			@Override
			public void actionPerformed(ActionEvent e) {
				new Window_join();
			}

		});

		bt1.addActionListener(new ActionListener() {
			// ��ư�� �������� �߻��ϴ� �ൿ�� ����
			@Override
			public void actionPerformed(ActionEvent e) {
				DB_login dl = new DB_login();
				int result = dl.checkIDPW(tf.getText().toString(), tf1.getText().toString());
				if (result == 0) {
					try {
						login.setVisible(false);// �α���â�� �����
						Window_chatting winchat = new Window_chatting();
						nickName = tf.getText();
						socket = new Socket("127.0.0.1", 7000); // 127.0.0.1 => localhost�� ��ǻ�� ��Ʈ��ũ���� ����ϴ� ������
																// ȣ��Ʈ������, �ڽ��� ��ǻ�͸� �ǹ��Ѵ�. IPv4������ IP �ּҴ�
																// 127.0.0.1[1]�̸�, IPv6������ ::1(0:0:0:0:0:0:0:1��
																// ����)[2]�� ��ȯ�ȴ�. ���� ��ǻ�͸� ���� ��ǻ���ΰ� ó�� ����� �� �־� �׽�Ʈ
																// �������� �ַ� ���ȴ�.
						winchat.socket = socket;
						winchat.nickName = nickName;
						winchat.chat.setTitle(nickName + "���� ä��â");
						new Thread(winchat).start(); // Ŭ���̾�Ʈ�� ������ ����
						winchat.setVisible(true); // ä��â�� ���̵��� ����
						winchat.tf.requestFocus(); // �α��ν� Ŀ���� textfield �� �̵�
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				try {
					String sqlID, sqlPW;
					/////////////////////////////////
					DBConnect connect = new DBConnect(); // DBConnectŬ������ ������ �Լ�(�޼ҵ�) ȣ��
					stmt = connect.getStatement();
					////////////// DB�� ���� /////////////
					sqlID = "select * from (select * from CHATTING where ID='" + tf.getText() + "')";
					rs = stmt.executeQuery(sqlID);

					if (result == 1) {

						if (tf.getText().toString().length() == 0 && tf1.getText().toString().length() == 0) {

							JOptionPane.showMessageDialog(null, "ID�� PW�� �Է����ּ���");

						} else if (tf.getText().toString().length() == 0) {

							JOptionPane.showMessageDialog(null, "ID�� �Է����ּ���");

						} else if (tf1.getText().toString().length() == 0) {

							JOptionPane.showMessageDialog(null, "PW�� �Է����ּ���");

						} else if (rs.next() == true) {

							if (rs.getString("PW").equals(tf1.getText()) == false) {

								JOptionPane.showMessageDialog(null, "�����ȣ�� Ȯ�����ּ���.");
								tf1.setText("");
							}
						} else if (rs.next() == false) {

							JOptionPane.showMessageDialog(null, "���������ʴ� ID�Դϴ�.");
							tf.setText("");
							tf1.setText("");
							
						}
					}
				} catch (SQLException e1) {
					System.err.println("�����߻�");
				}
			}
		});
	}

}
