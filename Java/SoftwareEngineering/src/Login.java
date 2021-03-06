import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;
import java.util.TreeSet;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;



public class Login extends JFrame{

	private JPanel loginPan,pan;
	private JButton LoginButton,ExitButton;
	private JTextField id;
	private JPasswordField password;
	private JButton title;
	private Container con;
	private JLabel idLabel,pwLabel;
	private BloodDB db;
	
	public Login()
	{
		super("BLOOD_LINK");
		URL label=Login.class.getClassLoader().getResource("heart.jpg");
		setContentPane(new JLabel(new ImageIcon(label)));
		setLocation(730, 300);//프레임위치 설정
		setSize(550,400);// 프레임크기 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close박스
		setLayout(null);//레이아웃 널값
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		URL icon=Login.class.getClassLoader().getResource("frameicon.png");
		Image img= tk.getImage(icon);
		setIconImage(img);
		
		URL logo=Login.class.getClassLoader().getResource("BloodLink로고.png");
		title=new JButton(new ImageIcon(logo));
		title.setBounds(68, 20, 415, 120);
		add(title);
	    buttonHide(title);
	    
	    id=new JTextField(10);
		id.setBackground(Color.white);
		
		idLabel= new JLabel("Enter the ID : ");
		idLabel.setFont(new Font("", 1, 18));
		idLabel.setBounds(100, 150, 135,40);
		
		
		password=new JPasswordField(10);
		password.setBackground(Color.white);
		KeyListener k = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					LoginButton.doClick();
				}
				
			}
		};
		password.addKeyListener(k);
		pwLabel=new JLabel("Enter the PW : ");
		pwLabel.setFont(new Font("", 1, 18));
		pwLabel.setBounds(100, 190, 135,40);
		
		loginPan=new JPanel(new GridLayout(2,1,5,5));
		loginPan.setBounds(240, 150, 150, 80);
		loginPan.setOpaque(false);
		add(loginPan);
		
		URL imageURL=Login.class.getClassLoader().getResource("loginbutton.png");
		LoginButton=new JButton(new ImageIcon(imageURL));
		LoginButton.setBackground(Color.WHITE);
		URL image2=Login.class.getClassLoader().getResource("exitbutton.png");
		ExitButton=new JButton(new ImageIcon(image2));
		ExitButton.setBackground(Color.WHITE);
	
		pan=new JPanel(new GridLayout(1, 2, 4, 4));
		pan.setBounds(100, 250, 290, 40);
		pan.setOpaque(false);
		System.out.println(LoginButton.getWidth());
		add(pan);
		
		add(idLabel);
		loginPan.add(id);
		add(pwLabel);
		loginPan.add(password);
		
		pan.add(LoginButton);
		pan.add(ExitButton);
		setVisible(true);//보이게
		
		Handler h=new Handler();
		LoginButton.addActionListener(h);
		ExitButton.addActionListener(h);
		password.addActionListener(h);
		
		db=new BloodDB();
	}
	
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==LoginButton)
			{
				if(findMember(id.getText(), password.getText()))
				{
					dispose();
					new BloodBoard(id.getText());
				}
				else
				{
					JOptionPane.showMessageDialog(null, "아이디가 없거나 비밀번호가 틀렸습니다");
					return ;
				}
			}
			else if(event.getSource()==ExitButton)
			{
				System.exit(0);
			}
		}
	}
	
	public boolean findMember(String id,String pw)//id와 패스워드 확인
	{
		boolean isLogin = false;
		try {
			String address = "https://lit-escarpment-60921.herokuapp.com/mi/login";
			URL url = new URL(address+"?account="+id+"&password="+pw);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			BufferedReader reader = null;
			int code = connection.getResponseCode();
			if(code == 200) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				System.out.println(code);
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}
			StringBuffer buffer = new StringBuffer();
			String string;
			while((string = reader.readLine()) != null) {
				buffer.append(string);
			}
			reader.close();
			connection.disconnect();
			if(buffer.toString().equals("true")) {
				isLogin = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isLogin;

		/*String member=new String(id+" "+pw);//id와 패스워드합침
		System.out.println(member);
		try
		{
			Scanner scan= new Scanner(new File("C:\\Users\\june\\Desktop\\BloodLink\\Java\\SoftwareEngineering\\member.txt"));
			while(scan.hasNextLine())
			{
				if(member.equals(scan.nextLine()))
					return true;
			}
		}
		catch(Exception e)
		{
			
		}
	//	String result=db.getLogin(id, pw);
	//	if(result.equals("true"))
	//	return true;
	//	else 
	//	return false;
		return false;*/
	}
	
	
	public void buttonHide(JButton button)//button처리
	{
		button.setBackground(new Color(255,255,255));
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false); 
		button.setFocusPainted(false);
	}
}
