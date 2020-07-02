package chattingprogram;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

public class ChattingServer extends Frame implements Runnable {
	
	public static void main(String[] args) {
		ChattingServer s =  new ChattingServer();
		new Thread(s).start(); // run �޼ҵ� ȣ��
	}
	
	TextArea chatarea = new TextArea(); 
	ServerSocket serverSocket;
	Socket socket;
	String nickName;
	
	//������ Ŭ���̾�Ʈ���� �����ص� ����
	Vector<Service> list = new Vector<>();
 
	private Dimension dimen, dimen1;
	private int xpos, ypos;
	private Label msgg = new Label(" ��ȭâ  ");
	
	public ChattingServer() {
		super("Server management");
		this.init();
		this.start();
		this.setSize(600, 500);
		dimen = Toolkit.getDefaultToolkit().getScreenSize();
		dimen1 = this.getSize();
		this.setLocation(500, 200);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void init() {
		
		Panel p7 = new Panel();
		p7.setSize(600,400);
		this.add(p7);
		BorderLayout border1 = new BorderLayout();
		p7.setLayout(border1);
		 
		Panel p8 = new Panel();
		p7.add("North",msgg);
		p7.add("Center",p8);
		p8.add(chatarea);
		chatarea.setPreferredSize(new Dimension(580, 390));
		
		Font font3 = new Font("����", Font.PLAIN, 15);
		msgg.setFont(font3);
	 
 
		
		
	}
	public void start() {
		// Event�� Threadó���� �κ�
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				}); // ---------- x ������ ���� �̺�Ʈ -----------
				
	}

	@Override
	public void run() { // 1�� ������
		try {
			serverSocket = new ServerSocket(7000);
			chatarea.append("-- �������� �����Ϸ� --\n");
			while(true) {
		    // �����ϴ� Ŭ���̾�Ʈ �������
			socket = serverSocket.accept();
			chatarea.append(socket.getInetAddress() + " ������.\n");
			// ���ӿ� ������ Ŭ���̾�Ʈ ����
			Service s = new Service(socket);
			// ���Ϳ� Ŭ���̾�Ʈ �߰�
			list.add(s);
			// ����Ŭ���� ������ ����
			s.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//����Ŭ���� - ������
	class Service extends Thread{
		Socket socket;
		InputStreamReader is;
		BufferedReader br;
		PrintWriter pw;
		String nickName;
		
		Service(Socket socket){
			this.socket = socket;
			try {
				is = new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(is);
				pw = new PrintWriter(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() { // 2�� ������ (ȥ���� �����ϰ���)
			while(true) {
				try {
					//Ŭ���̾�Ʈ�� ���� �޽��� �б�.
					String line = br.readLine();
					StringTokenizer st = new StringTokenizer(line,"|");
					int num = Integer.parseInt(st.nextToken());
					
					switch(num) {
					case 1:{
						nickName = st.nextToken();
						//��� Ŭ���̾�Ʈ������ �α����� �г��� ������
						//��ü ����� �г����� ���� ������.
						String allNames = "";
						for (int i = 0; i < list.size(); i++) {
							Service ss = list.get(i);
							allNames += ss.nickName+",";
						}
						
						//�������ĸ� ����
						allNames.substring(0,nickName.length()-1);
						
						for (int i = 0; i < list.size(); i++) {
							Service ss = list.get(i);
							ss.pw.write("1|" + nickName + "|" + allNames +"\n");
							ss.pw.flush();
						}
						break;
					}
					case 2: {
						String m = st.nextToken();
						//��� Ŭ���̾�Ʈ������ �α����� �г���.
						for(int i = 0; i<list.size(); i++) {
							Service ss = list.get(i);
							ss.pw.write("2|" + nickName + ">>" + m + "\n");
							ss.pw.flush();
						}
						break;
					}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
