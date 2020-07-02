package chattingprogram;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.security.Provider.Service;
import java.util.*;
import java.awt.*;
import java.awt.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Window_chatting extends JFrame implements ActionListener, Runnable { //WindowListener, Runnable
	private JLabel gong = new JLabel(" ");
	private JLabel gong1 = new JLabel(" ");
	private JLabel dea = new JLabel("대화창",JLabel.CENTER);
	public static JTextField tf = new JTextField(50);
	private JButton bt = new JButton("전송");
	private JButton bt1 = new JButton("로그아웃");
    private TextArea comments = new TextArea(20,30);
    Socket socket;
	BufferedReader br;
	PrintWriter pw;
	static String nickName;
	List list;

	JFrame chat = new JFrame();
	
	public static void main(String[] args) {
		Window_chatting winchat = new Window_chatting(); 
	}
	
	public Window_chatting() {
		
	    chat.setSize(650,450);
        chat.setResizable(false);
        chat.setVisible(true);
  
        JPanel p = new JPanel(new GridLayout(1,4));
        chat.add(p,"North");
        p.add(gong);
        p.add(dea);
        p.add(gong1);
        p.add(bt1);
        
        BorderLayout border = new BorderLayout(5,5);
        JPanel p1 = new JPanel();
        p1.setLayout(border);
        p1.add(comments,"Center");
        list = new List();
        p1.add(list,"East");
        chat.add(p1,"Center");

        
		JPanel p3 = new JPanel(new FlowLayout());
		chat.add(p3,"South");
		p3.add(tf);
		p3.add(bt);
		
		this.start();
		
	}
	
	public void start() {
		// Event나 Thread처리할 부분
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}); // ---------- x 누르면 종료 이벤트 -----------
		tf.addActionListener(this);
		bt.addActionListener(this);
		bt1.addActionListener(this);
	}
	

	public void run() {
		// 네트워크 io Class 얻기
		try {
			InputStreamReader is = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(is);
			pw = new PrintWriter(socket.getOutputStream());

			pw.write("1|" + nickName + "\n"); // 주의 : 문장마지막에 \n을 반드시 넣어야 전송된다.
			pw.flush(); // 서버로 전송 (반드시 필요)

			while (true) {
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line, "|");
				int num = Integer.parseInt(st.nextToken()); // 앞에 숫자를 읽는다

				switch (num) {
				case 1: {
					String msg = st.nextToken(); // 숫자 뒤 로그인한 사람의 닉네임을 읽는다.
					comments.append(" > > " + msg + "님이 채팅에 참여했습니다.\r\n");
					// 대화방에 있는 닉네임들 읽기
					// ","로 여러명의 이름을 서버가 보냄
					String names = st.nextToken();
					StringTokenizer st2 = new StringTokenizer(names, ",");
					// 리스트 초기화
					list.removeAll(); //
					// 리스트에 명단 추가
					while (st2.hasMoreTokens()) {
						list.add(st2.nextToken());
					}
					break;
				}
				case 2: {
					String msg = st.nextToken();
					comments.append(msg + "\n");
					break;
				}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 서버로 메세지 보내기
		Object ob = e.getSource();
		// 메세지 입력후 엔터를 누르거나 전송 버튼 누르면 실행
		if (tf == ob || bt == ob) {
			String msg = tf.getText();
			pw.write("2|" + msg + "\r\n"); // 메모장 줄바꿈을 위해 \r 을 추가함 -(메모장에서의 줄바꿈)
			pw.flush(); // 서버로 전송
			tf.setText("");
			tf.requestFocus();
		}else if (ob == bt1) {
			chat.setVisible(false);
			Window_login Winlog = new Window_login();
		}
	}

}
