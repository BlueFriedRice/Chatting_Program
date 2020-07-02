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
		new Thread(s).start(); // run 메소드 호출
	}
	
	TextArea chatarea = new TextArea(); 
	ServerSocket serverSocket;
	Socket socket;
	String nickName;
	
	//접속한 클라이언트들을 저장해둘 벡터
	Vector<Service> list = new Vector<>();
 
	private Dimension dimen, dimen1;
	private int xpos, ypos;
	private Label msgg = new Label(" 대화창  ");
	
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
		
		Font font3 = new Font("돋움", Font.PLAIN, 15);
		msgg.setFont(font3);
	 
 
		
		
	}
	public void start() {
		// Event나 Thread처리할 부분
				this.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				}); // ---------- x 누르면 종료 이벤트 -----------
				
	}

	@Override
	public void run() { // 1번 쓰레드
		try {
			serverSocket = new ServerSocket(7000);
			chatarea.append("-- 서버소켓 생성완료 --\n");
			while(true) {
		    // 접속하는 클라이언트 접속허용
			socket = serverSocket.accept();
			chatarea.append(socket.getInetAddress() + " 접속함.\n");
			// 접속에 성공한 클라이언트 생성
			Service s = new Service(socket);
			// 벡터에 클라이언트 추가
			list.add(s);
			// 내부클래스 스레드 시작
			s.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//내부클레스 - 스레드
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
		public void run() { // 2번 쓰레드 (혼돈을 방지하고자)
			while(true) {
				try {
					//클라이언트가 보낸 메시지 읽기.
					String line = br.readLine();
					StringTokenizer st = new StringTokenizer(line,"|");
					int num = Integer.parseInt(st.nextToken());
					
					switch(num) {
					case 1:{
						nickName = st.nextToken();
						//모든 클라이언트들한테 로그인한 닉네임 보내기
						//전체 명단의 닉네임을 같이 보낸다.
						String allNames = "";
						for (int i = 0; i < list.size(); i++) {
							Service ss = list.get(i);
							allNames += ss.nickName+",";
						}
						
						//마지막컴마 제거
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
						//모든 클라이언트들한테 로그인한 닉네임.
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
