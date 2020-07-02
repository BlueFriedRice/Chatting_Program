package chattingprogram;

import static chattingprogram.Window_join.jl;
import static chattingprogram.Window_join.tf;
import static chattingprogram.Window_join.tf1;
import static chattingprogram.Window_join.tf10;
import static chattingprogram.Window_join.tf11;
import static chattingprogram.Window_join.tf2;
import static chattingprogram.Window_join.tf3;
import static chattingprogram.Window_join.tf4;
import static chattingprogram.Window_join.tf5;
import static chattingprogram.Window_join.tf6;
import static chattingprogram.Window_join.tf7;
import static chattingprogram.Window_join.tf8;
import static chattingprogram.Window_join.tf9;
import static chattingprogram.Window_join.warn;

import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Jdbc.DBConnect;

public class Window_join extends JFrame implements ItemListener, ActionListener, FocusListener {
    // 버튼이 눌러지면 만들어지는 새 창을 정의한 클래스
	private JLabel title = new JLabel("---------------------- 회원가입창 ----------------------",JLabel.CENTER);
    private JLabel lb = new JLabel("아이디",JLabel.CENTER);
    private JLabel lb1 = new JLabel("비밀번호",JLabel.CENTER);
    private JLabel lb2 = new JLabel("비밀번호확인",JLabel.CENTER);
    private JLabel lb3 = new JLabel("이름",JLabel.CENTER);
    private JLabel lb4 = new JLabel("주민등록번호",JLabel.CENTER);
    private JLabel lb5 = new JLabel("핸 드 폰",JLabel.CENTER);
    private JLabel lb6 = new JLabel("E-Mail",JLabel.CENTER);
    private JLabel gol = new JLabel("@",JLabel.CENTER);
    private JLabel lb7 = new JLabel("주소",JLabel.CENTER);
    private JLabel lb8 = new JLabel("상세주소",JLabel.CENTER);
    private JLabel dasi = new JLabel("-",JLabel.CENTER);
    private JLabel dasi1 = new JLabel("-",JLabel.CENTER);
    private JLabel dasi2 = new JLabel("-",JLabel.CENTER);
    public static JLabel jl = new JLabel();
    public static String warn[] = {"반드시 차례대로 빈칸에 정보를 입력해주세요."};
    
    public static JTextField tf = new JTextField(10);
    public static JTextField tf1 = new JPasswordField(10);
    public static JTextField tf2 = new JPasswordField(10);
    public static JTextField tf3 = new JTextField(10);
    public static JTextField tf4 = new JTextField(6);
    public static JTextField tf5 = new JPasswordField(7);
    public static JTextField tf6 = new JTextField(4);
    public static JTextField tf7 = new JTextField(4);
    public static JTextField tf8 = new JTextField(4);
    public static JTextField tf9 = new JTextField(10);
    public static JTextField tf10 = new JTextField(10);
    public static JTextField tf11 = new JTextField(25);
    public static JTextField tf12 = new JTextField(20);
    
    private String []ar = {"naver.com","daum.net","gmail.com","yahoo.co.kr","hanmir.com","empal.com","netian.com",
    						"nate.com","dreamwiz.com","hanmail.net","직접입력"};
	private Choice ch = new Choice();
	
	public static JButton bt = new JButton("확인");
	private JButton bt1 = new JButton("취소");
	public static JButton bt2 = new JButton("중복확인"); //ID
	public static JButton bt3 = new JButton("비밀번호일치확인"); //PW
	public static JButton bt4 = new JButton("중복확인"); //P_ID
	
	public static JFrame jo = new JFrame("회원가입");

	private static ResultSet rs = null; // SELECT문을 통해서 데이터를 끌어온다면 ResultSet 객체에 그 데이터를 저장해야 한다
	private static Statement stmt = null; // 데이터베이스에 쿼리를 보내기 위해 필요한 객체
	
    public Window_join() {
        // 주의, 여기서 setDefaultCloseOperation() 정의를 하지 말아야 한다
        // 정의하게 되면 새 창을 닫으면 모든 창과 프로그램이 동시에 꺼진다
        jo.setSize(450,650);
        jo.setResizable(false);
        jo.setVisible(true);
        start();
        
        jo.add(title,"North");
        
		JPanel p = new JPanel(new GridLayout(11,2));
		jo.add(p);
    	
		//ID
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));		
		p.add(p1);
		p1.add(lb);
       	p1.add(tf);
       	p1.add(bt2);
       	
       	//PW
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));	
		p.add(p2);
    	p2.add(lb1);
    	p2.add(tf1);
    	((JPasswordField) tf1).setEchoChar('*');
    	
    	//PW Check
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));	
		p.add(p3);
    	p3.add(lb2);
    	p3.add(tf2);
    	((JPasswordField) tf2).setEchoChar('*');
    	p3.add(bt3);
    	
    	//Name
		JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));	
		p.add(p4);
    	p4.add(lb3);
    	p4.add(tf3);
    	
    	//P_ID
		JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));	
    	p.add(p5);
    	p5.add(lb4);
    	p5.add(tf4);
    	p5.add(dasi);
    	p5.add(tf5);
       	p5.add(bt4);
       	((JPasswordField) tf5).setEchoChar('*');
    	
    	//H.P
		JPanel p6 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));	
    	p.add(p6); 
    	p6.add(lb5);
    	p6.add(tf6);
    	p6.add(dasi1);
    	p6.add(tf7);
    	p6.add(dasi2);
    	p6.add(tf8);
    	
    	//E-Mail
		JPanel p7 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));	
    	p.add(p7); 
    	p7.add(lb6);
    	p7.add(tf9);
    	p7.add(gol);
    	p7.add(tf10);
    	tf10.setText(ar[0]);
    	for(int i=0;i<ar.length;i++) {
    	ch.add(ar[i]);
    	}
    	p7.add(ch);
    	
    	//Address
		JPanel p8 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));	
    	p.add(p8); 
    	p8.add(lb7);
    	p8.add(tf11);
    	
    	//Address2
		JPanel p9 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));	
    	p.add(p9);
    	p9.add(lb8);
    	p9.add(tf12);
    	
    	//LabelField
    	JPanel p10 = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
    	p.add(p10);
    	p10.add(jl);
    	jl.setText(warn[0]);
    	
    	//Button
    	JPanel p11 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
    	p.add(p11);
    	p11.add(bt);
    	p11.add(bt1);

    }

	public void start() {
		
		tf1.addFocusListener(this);
		tf2.addFocusListener(this);
		tf3.addFocusListener(this);
		tf4.addFocusListener(this);
		tf5.addFocusListener(this);
		tf6.addFocusListener(this);
		tf7.addFocusListener(this);
		tf8.addFocusListener(this);
		tf9.addFocusListener(this);
		tf10.addFocusListener(this);
		tf11.addFocusListener(this);
		
		ch.addItemListener(this);
		
		bt1.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
		        jo.setVisible(false);
			}
		});
		
		bt2.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
			    DB_join dj = new DB_join();
				dj.IDcheck();
			}
		});
		
		bt3.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
			    DB_join dj = new DB_join();
				dj.PWcheck();			
			}
		});
		
		bt4.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				
				if (tf4.getText().isEmpty() == true || tf5.getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(null, "주민등록번호를 입력해주세요.");
				}else {
			    DB_join dj = new DB_join();
				dj.PIDcheck();
				}
			}
		});
		
		bt.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				 if ((tf.getText().isEmpty()) == true || (tf1.getText().isEmpty()) == true
							|| (tf2.getText().isEmpty()) == true || (tf3.getText().isEmpty()) == true
							|| (tf4.getText().isEmpty()) == true || (tf5.getText().isEmpty()) == true
							|| (tf6.getText().isEmpty()) == true || (tf7.getText().isEmpty()) == true
							|| (tf8.getText().isEmpty()) == true || (tf9.getText().isEmpty()) == true
							|| (tf10.getText().isEmpty()) == true || (tf11.getText().isEmpty()) == true) {
						JOptionPane.showMessageDialog(null, "비어있는 칸이 존재합니다.");
				}else {
			    DB_join dj = new DB_join();
				dj.Finaljoin();
				}
			}
		});
		
	}
	public void paint(Graphics g) {}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() == ch) {
			String str = ch.getSelectedItem();
			tf10.setText(str);
			}
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	public void focusGained(FocusEvent e) {
		if (e.getSource() == tf1) {
			int x = tf.getText().trim().length();
			if (x == 0) {
				tf.requestFocus();
			}
		}else if (e.getSource() == tf2) {
			int x = tf1.getText().trim().length();
			if (x == 0) {
				tf1.requestFocus();
			}
		}else if (e.getSource() == tf3) {
			int x = tf2.getText().trim().length();
			if (x == 0) {
				tf2.requestFocus();
			}
		}else if (e.getSource() == tf4) {
			int x = tf3.getText().trim().length();
			if (x == 0) {
				tf3.requestFocus();
			}
		}else if (e.getSource() == tf5) {
			int x = tf4.getText().trim().length();
			if (x != 6) {
				tf4.setText("");
				tf4.requestFocus();
			}
		}else if (e.getSource() == tf6) {
			int x = tf5.getText().trim().length();
			if (x == 0) {
				tf5.requestFocus();
			}
		}else if (e.getSource() == tf7) {
			int x = tf6.getText().trim().length();
			if (x != 3) {
				tf6.setText("");
				tf6.requestFocus();
			}
		}else if (e.getSource() == tf8) {
			int x = tf7.getText().trim().length();
			if (x > 4 || x < 3) {
				tf7.setText("");
				tf7.requestFocus();
			}
		}else if (e.getSource() == tf9) {
			int x = tf8.getText().trim().length();
			if (x != 4) {
				tf8.setText("");
				tf8.requestFocus();
			}
		}else if (e.getSource() == tf11) {
			int x = tf9.getText().trim().length();
			if (x == 0) {
				tf9.requestFocus();
			}
		}else if (e.getSource() == tf12) {
			int x = tf11.getText().trim().length();
			if (x == 0) {
				tf11.requestFocus();
			}
		}
		
	}	

	@Override
	public void focusLost(FocusEvent e) {}
	
}