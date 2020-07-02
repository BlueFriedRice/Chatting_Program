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
	private JLabel dea = new JLabel("��ȭâ",JLabel.CENTER);
	public static JTextField tf = new JTextField(50);
	private JButton bt = new JButton("����");
	private JButton bt1 = new JButton("�α׾ƿ�");
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
		// Event�� Threadó���� �κ�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}); // ---------- x ������ ���� �̺�Ʈ -----------
		tf.addActionListener(this);
		bt.addActionListener(this);
		bt1.addActionListener(this);
	}
	

	public void run() {
		// ��Ʈ��ũ io Class ���
		try {
			InputStreamReader is = new InputStreamReader(socket.getInputStream());
			br = new BufferedReader(is);
			pw = new PrintWriter(socket.getOutputStream());

			pw.write("1|" + nickName + "\n"); // ���� : ���帶������ \n�� �ݵ�� �־�� ���۵ȴ�.
			pw.flush(); // ������ ���� (�ݵ�� �ʿ�)

			while (true) {
				String line = br.readLine();
				StringTokenizer st = new StringTokenizer(line, "|");
				int num = Integer.parseInt(st.nextToken()); // �տ� ���ڸ� �д´�

				switch (num) {
				case 1: {
					String msg = st.nextToken(); // ���� �� �α����� ����� �г����� �д´�.
					comments.append(" > > " + msg + "���� ä�ÿ� �����߽��ϴ�.\r\n");
					// ��ȭ�濡 �ִ� �г��ӵ� �б�
					// ","�� �������� �̸��� ������ ����
					String names = st.nextToken();
					StringTokenizer st2 = new StringTokenizer(names, ",");
					// ����Ʈ �ʱ�ȭ
					list.removeAll(); //
					// ����Ʈ�� ��� �߰�
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
		// ������ �޼��� ������
		Object ob = e.getSource();
		// �޼��� �Է��� ���͸� �����ų� ���� ��ư ������ ����
		if (tf == ob || bt == ob) {
			String msg = tf.getText();
			pw.write("2|" + msg + "\r\n"); // �޸��� �ٹٲ��� ���� \r �� �߰��� -(�޸��忡���� �ٹٲ�)
			pw.flush(); // ������ ����
			tf.setText("");
			tf.requestFocus();
		}else if (ob == bt1) {
			chat.setVisible(false);
			Window_login Winlog = new Window_login();
		}
	}

}
