import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.html.HTMLDocument.Iterator;

public class BloodBoard extends JFrame{
	
	private JTable table;//데이터 테이블
	private JScrollPane jscroll;//데이터 테이블이 들어가는 scroll
	private DefaultTableModel mod;
	private JLabel title;//타이틀 "헌혈증서사용상태"
	private JComboBox<String> comboStandard, comboStatus;//조회기준이 들어있는 콤보박스,신청상태가 들어있는 콤보박스
	private JTextField searchText;//검색 텍스트 필드
	private JButton search,delete,approve;//사용할 버튼들
	private String columnNames[]={ "증서번호", "사용자이름", "사용자이메일","신청상태" };
	private String rowData[][],rowDataTemp[][],original[][];//테이블에 들어갈 데이터들
	private JFrame frame;
	private String id;
	private BloodDB db;
	
	public BloodBoard(String id)
	{
		super("BLOOD_Board");
		setLocation(360, 225);//프레임위치 설정
		setSize(1200,730);// 프레임크기 설정
		setContentPane(new JLabel(new ImageIcon("icon\\pulse1.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close박스
		setLayout(null);//레이아웃 널값
		
		this.id=id;//ID받아오기
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image img= tk.getImage("icon\\frameicon.png");
		setIconImage(img);
		
		title = new JLabel("헌혈증서 사용신청 상태");//title넣기
		title.setBounds(85, 50, 280, 40);
		title.setFont(new Font("", 1, 25));
		add(title);
		
		SearchInfo();//테이블 내용 조회 혹은 새로고침
		
        TableSet(rowData);//테이블 생성 및 설정
     
		ComboBoxSet();//콤보박스생성
		
		searchText = new JTextField();//search텍스트필트
		searchText.setBounds(645,50,200,40);
		searchText.setBackground(Color.WHITE);
		searchText.setFont(new Font("", 1, 15));
		add(searchText);
		
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
					search.doClick();//검색 칸에서 ENTER 키 입력시 "search"버튼 클릭
				}
				
			}
		};
		searchText.addKeyListener(k);
		Handler h = new Handler();
		
		ButtonSet();//버튼생성
		search.addActionListener(h);
		delete.addActionListener(h);
		approve.addActionListener(h);
		db=new BloodDB();
		setVisible(true);
	}
	private class Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource()==search)
			{
				if(searchText.getText().equals(""))//새로고침
				{
					if(comboStatus.getSelectedIndex()==0)
					{
						SearchInfo();
					}
					else
					{
						SearchInfo(searchText.getText(),comboStandard.getSelectedIndex(),comboStatus.getSelectedIndex());
					}
					jscroll.setVisible(false);
					TableSet(rowData);
				}
				else//검색
				{
					SearchInfo(searchText.getText(),comboStandard.getSelectedIndex(),comboStatus.getSelectedIndex());
					jscroll.setVisible(false);
					TableSet(rowDataTemp);
				}
			}
			else if(event.getSource()==delete)//삭제
			{
				if(table.getSelectedRow()!=-1)
				{
					
					int[] selectedList=table.getSelectedRows();
					deleteInfo(selectedList);
					SearchInfo();
					jscroll.setVisible(false);
					TableSet(rowData);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "선택된 셀이 없습니다.");
					return ;
				}
				
			}
			else if(event.getSource()==approve)//승인
			{
				if(table.getSelectedRow()!=-1)
				{
					int[] selectedList=table.getSelectedRows();
					approveInfo(selectedList);
					SearchInfo();
					jscroll.setVisible(false);
					TableSet(rowData);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "선택된 셀이 없습니다.");
					return ;
				}
			}
		}
	}
	public void SearchInfo()//rowdata불러오기 및 새로고침
	{
		try
		{
			//File is = new File("C:\\Users\\june\\Desktop\\BloodLink\\Java\\SoftwareEngineering\\table.txt");


			String address = "https://lit-escarpment-60921.herokuapp.com/mi/" + id + "/queue";
			URL url = new URL(address);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			BufferedReader bf = null;
			int code = connection.getResponseCode();
			if(code == 200) {
				bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				bf = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}
			java.util.List<String> list = new ArrayList<>();
			String string;
			while((string = bf.readLine()) != null) {
				list.add(string);
			}
			bf.close();
			connection.disconnect();

			rowData=new String[list.size()][4];

			java.util.Iterator<String> iterator = list.iterator();
			for(int i = 0;iterator.hasNext();i++) {
				rowData[i++]=iterator.next().split(" ");
			}
			for(int i=0;i<rowData.length;i++)//사용상태 바꾸기
			{
				if(rowData[i][3].equals("true"))
					rowData[i][3]="사용대기";
				else
					rowData[i][3]="사용완료";
			}
			original=rowData;
			return;
			/*BufferedReader bf = new BufferedReader(new FileReader(is));
			String row;
			ArrayList<String[]> rData=new ArrayList<String[]>();
			int i=0;
			
			while ((row = bf.readLine()) != null) 
			{	 
				i++;	   
			}
			bf.close();
			bf=new BufferedReader(new FileReader(is));*/


			//rowData=new String[i][4];
			/*i=0;
			while ((row = bf.readLine()) != null)
			{	 
				rowData[i++]=row.split(" ");   
			}*/

		}
		catch(Exception e)
		{
			
		}
	//	String result=db.getRowData(id);
		
	}
	public void SearchInfo(String a,int index,int sindex)//rowdata불러오기 및 새로고침
	{
		try
		{
			//File is = new File("C:\\Users\\june\\Desktop\\BloodLink\\Java\\SoftwareEngineering\\table.txt");

			String state="";
			String key="";
			if(sindex==0)
				state="all";//key=number,account,mail
			else if(sindex==1)
				state="wait";
			else 
				state="used";
			if(index==0)
				key="number";
			else if(index==1)
				key="name";
			else if(index==2)
				key="account";
			String param="?state="+state;
		//	System.out.println("a="+a);
		//	System.out.println("index="+index);
			if(!a.equals("")) {
				param +="&key="+key+"&value="+a;//all. wait, used ;
				System.out.println(a);
			}
			String address = "https://lit-escarpment-60921.herokuapp.com/mi/" + id + "/queue";
			URL url = new URL(address+param);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			BufferedReader bf = null;
			int code = connection.getResponseCode();
			if(code == 200) {
				bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				bf = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}
			java.util.List<String> list = new ArrayList<>();
			String string;
			while((string = bf.readLine()) != null) {
				list.add(string);
			}
			System.out.println(list.toString());
			bf.close();
			connection.disconnect();

			rowData=new String[list.size()][4];

			java.util.Iterator<String> iterator = list.iterator();
			for(int i = 0;iterator.hasNext();i++) {
				rowData[i++]=iterator.next().split(" ");
			}
			for(int i=0;i<rowData.length;i++)//사용상태 바꾸기
			{
				if(rowData[i][3].equals("true"))
					rowData[i][3]="사용대기";
				else
					rowData[i][3]="사용완료";
			}
			original=rowData;
			return;
			/*BufferedReader bf = new BufferedReader(new FileReader(is));
			String row;
			ArrayList<String[]> rData=new ArrayList<String[]>();
			int i=0;
			
			while ((row = bf.readLine()) != null) 
			{	 
				i++;	   
			}
			bf.close();
			bf=new BufferedReader(new FileReader(is));*/


			//rowData=new String[i][4];
			/*i=0;
			while ((row = bf.readLine()) != null)
			{	 
				rowData[i++]=row.split(" ");   
			}*/

		}
		catch(Exception e)
		{
			
		}
	//	String result=db.getRowData(id);
		/*String[][] dataTemp=null;
		int count=0;
		
		if(sindex==0)//신청상태(전체)
		{
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a))
					count++;
			}
			dataTemp=new String[count][4];
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a))
					dataTemp[count++]=original[i];
			}
			
		}
		else if(sindex==1)//사용대기
		{
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a)&&original[i][3].equals("사용대기"))
					count++;
			}
			dataTemp=new String[count][4];
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a)&&original[i][3].equals("사용대기"))
					dataTemp[count++]=original[i];
			}
			
		}
		else if(sindex==2)//사용완료
		{
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a)&&original[i][3].equals("사용완료"))
					count++;
			}
			dataTemp=new String[count][4];
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a)&&original[i][3].equals("사용완료"))
					dataTemp[count++]=original[i];
			}
		
		}
		rowData=new String[dataTemp.length][4];
		rowData=dataTemp;
		return;*/
		/*	try
		{
			File is = new File("C:\\Users\\june\\Desktop\\BloodLink\\Java\\SoftwareEngineering\\table.txt");

			BufferedReader bf = new BufferedReader(new FileReader(is));
			String row;
			ArrayList<String[]> rData=new ArrayList<String[]>();
			int i=0;
			int count=0;
			String status="";
			if(sindex==1)
				status="사용대기";
			else if(sindex==2)
				status="사용완료";
			
			while ((row = bf.readLine()) != null) 
			{	
				if(row.split(" ")[index].equals(a))
				{
					if(sindex!=0)
					{
						if(row.split(" ")[4].equals(status))
							count++;
					}
					else 
						count++;
				}
				i++;	   
			}
			bf.close();
			bf=new BufferedReader(new FileReader(is));
			rowDataTemp=new String[count][4];
			i=0;
			while ((row = bf.readLine()) != null) 
			{	
				if(row.split(" ")[index].equals(a))
				{
					if(sindex!=0)
					{
						if(row.split(" ")[4].equals(status))
							rowDataTemp[i++]=row.split(" ");   
					}
					else 
						rowDataTemp[i++]=row.split(" ");   
				}
			}
			return ;
		}
		catch(Exception e)
		{
			
		}*/
	}
	public void approveInfo(int[] list)//승인
	{
		String message = "";
		
		int i=0;
		int j=0;

		while(true)
		{
			String address = "https://lit-escarpment-60921.herokuapp.com/mi/" + id + "/call?number=";
			if(i==list[j])
			{
				if(rowData[i][3].equals("사용대기") || rowData[i][3].equals("true")) {
					address += rowData[i][0];
					HttpURLConnection connection = null;
					try {
						connection = (HttpURLConnection)new URL(address).openConnection();
						connection.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(j!=list.length-1)
						j++;
				}
			}
			i++;
			if(i==rowData.length)
				break;
		}

		/*
		while(true)
		{
			
			if(i!=list[j])
			{
				message+=rowData[i][0]+" "+rowData[i][1]+" "+rowData[i][2]+" "+rowData[i][3]+"\n";
			}
			else
			{
				if(rowData[i][3].equals("사용대기"))
					rowData[i][3]="사용완료";
				message+=rowData[i][0]+" "+rowData[i][1]+" "+rowData[i][2]+" "+rowData[i][3]+"\n";
				if(j!=list.length-1)
					j++;
				
			}
			i++;
			if(i==rowData.length)
				break;
		}
        System.out.println(message);
        File file = new File("table.txt");
        FileWriter writer = null;
        
        try {
            // 기존 파일의 내용에 이어서 쓰려면 true를, 기존 내용을 없애고 새로 쓰려면 false를 지정한다.
            writer = new FileWriter(file, false);
            writer.write(message);
            writer.flush();
            
            System.out.println("DONE");
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try {
                if(writer != null) writer.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }*/


	}
	public void deleteInfo(int[] list)//삭제
	{
		String message = "";
		
		int i=0;
		int j=0;

		while(true)
		{
			String address = "https://lit-escarpment-60921.herokuapp.com/mi/" + id + "/recall?number=";
			if(i==list[j])
			{
				if(rowData[i][3].equals("사용완료") || rowData[i][3].equals("false")) {
					address += rowData[i][0];
					HttpURLConnection connection = null;
					try {
						connection = (HttpURLConnection)new URL(address).openConnection();
						connection.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(j!=list.length-1)
						j++;
				}
			}
			i++;
			if(i==rowData.length)
				break;
		}


		/*
		System.out.println(list[0]);
		System.out.println(rowData.length);
		while(true)
		{
			
			if(i!=list[j])
			{
				message+=rowData[i][0]+" "+rowData[i][1]+" "+rowData[i][2]+" "+rowData[i][3]+'\n';	
			}
			else
			{
				if(j!=list.length-1)
					j++;
				
			}
			i++;
			System.out.println("i="+i);
			if(i==rowData.length)
				break;
		}
        System.out.println(message);
        File file = new File("table.txt");
        FileWriter writer = null;
        
        try {
            // 기존 파일의 내용에 이어서 쓰려면 true를, 기존 내용을 없애고 새로 쓰려면 false를 지정한다.
            writer = new FileWriter(file, false);
            writer.write(message);
            writer.flush();
            
            System.out.println("DONE");
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try {
                if(writer != null) writer.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }*/
	}
	public void ButtonSet()//button들 생성
	{
		search = new JButton("조회");//search버튼
		search.setBounds(855,50,80,40);
		search.setFont(new Font("", 1, 15));
		
		delete = new JButton("삭제");//delete 버튼
		delete.setBounds(945,50,80,40);
		delete.setFont(new Font("", 1, 15));

		approve = new JButton("승인");//approve 버튼
		approve.setBounds(1035,50,80,40);
		approve.setFont(new Font("", 1, 15));
		add(delete);
		add(search);
		add(approve);
	}
	public void ComboBoxSet()//combobox생성
	{
		String status[] = {"신청상태(전체)","사용대기","사용완료"};
		comboStatus= new JComboBox<String>(status);//사용상태 콤보박스생성
		comboStatus.setBounds(385,50,130,40);
		comboStatus.setBackground(Color.WHITE);
		comboStatus.setFont(new Font("", 1, 15));
		
		String standard[]= {"증서번호", "사용자이름", "사용자이메일" };
		comboStandard = new JComboBox<String>(standard);//comboStandardbox생성
		comboStandard.setBounds(525, 50, 110, 40);
		comboStandard.setBackground(Color.WHITE);
		comboStandard.setFont(new Font("", 1, 15));
		add(comboStatus);
		add(comboStandard);
		return ;
	}
	public void TableSet(String Data[][])
	{
		mod= new DefaultTableModel(Data,columnNames) {
			public boolean isCellEditable(int row,int column)//table 임의로 수정불가
			{
				return false;
			}
		};
		table = new JTable(mod);//table생성
		table.setSelectionMode(2);//다중 선택가능
		TableWidth();//table 간격 조절
        table.setFont(new Font("", 1, 15));//table font 크기조절
        table.setRowHeight(40);
        TableFontSort();//글씨 가운데 정렬
   
        jscroll = new JScrollPane(table);//scroll 생성
		jscroll.setBounds(85, 120, 1030, 500);//크기설정
		jscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//수직방향스크롤
		add(jscroll);
		return ;
	}
	public void TableWidth()//테이블 열 간격조절
	{
		table.getColumn("증서번호").setPreferredWidth(5);
		table.getColumn("사용자이름").setPreferredWidth(20);
		table.getColumn("사용자이메일").setPreferredWidth(110);
		table.getColumn("신청상태").setPreferredWidth(10);
	}
	public void TableFontSort()//table 글씨 정렬
	{
		// DefaultTableCellHeaderRenderer 생성 (가운데 정렬을 위한)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

				 
		// DefaultTableCellHeaderRenderer의 정렬을 가운데 정렬로 지정
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				
		// 정렬할 테이블의 ColumnModel을 가져옴
		TableColumnModel tcmSchedule = table.getColumnModel();

				 
		// 반복문을 이용하여 테이블을 가운데 정렬로 지정
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
	}
/*	public void parsingData(String original)
	{
		String test="BDC{" +
	            ", number='" + "2434" + '\'' +
	            ", type='" + "b" + '\'' +
	            ", name='" + "박준현" + '\'' +
	            ", birth=" + "1996-02-26" +
	            ", sex=" + "남" +
	            ", date=" + "2018-06-18" +
	            ", place='" + "경북대점" + '\'' +
	            ", owner='" + "박준현" + '\'' +
	            ", usage='" + "사용대기" + '\'' +
	            ", valid=" + "true" +
	            '}'
	            +"BDC{" +
	                    ", number='" + "2434" + '\'' +
	                    ", type='" + "b" + '\'' +
	                    ", name='" + "박준현" + '\'' +
	                    ", birth=" + "1996-02-26" +
	                    ", sex=" + "남" +
	                    ", date=" + "2018-06-18" +
	                    ", place='" + "경북대점" + '\'' +
	                    ", owner='" + "박준현" + '\'' +
	                    ", usage='" + "사용대기" + '\'' +
	                    ", valid=" + "true" +
	                    '}'
	                    +"BDC{" +
	                    ", number='" + "2434" + '\'' +
	                    ", type='" + "b" + '\'' +
	                    ", name='" + "박준현" + '\'' +
	                    ", birth=" + "1996-02-26" +
	                    ", sex=" + "남" +
	                    ", date=" + "2018-06-18" +
	                    ", place='" + "경북대점" + '\'' +
	                    ", owner='" + "박준현" + '\'' +
	                    ", usage='" + "사용대기" + '\'' +
	                    ", valid=" + "true" +
	                    '}';
		//System.out.println(test);
		String[] wow=test.split("BDC");
		System.out.println(wow.length);
		
		ArrayList<String> wow3=new ArrayList<>();
		//first parsing
		String  wow2[][]=new String[wow.length-1][4];//1,5,9,11
		System.out.println(wow2.length);
		String temp2;
		for(int i=0;i<wow.length;i++)
		{
			String[] temp=wow[i].split("'");
			System.out.println(temp.length);
			
			for(int j=0;j<temp.length;j++)
			{
				if(j==1)
				{
					temp2=temp[j];
					wow3.add(temp[j]);
				}
				if(j==5)
				{
					temp2=temp[j];
					wow3.add(temp[j]);
				}
				if(j==9)
				{	
					temp2=temp[j];
					wow3.add(temp[j]);
				}
				if(j==11)
				{
					temp2=temp[j];
					wow3.add(temp[j]);
				}
				System.out.println("j= "+j+" "+temp[j]);
			}
		}
		String[] a=new String[4];
		int k=0;
		int i=0;
		int j=0;
		for(k=0;k<wow3.size();k++)
		{
			a[i]=wow3.get(k);
			if(i==3)
			{
				i=0;
				wow2[j++]=a;
			}
			else
				i++;
		}
		for(i=0;i<wow2.length;i++)
		{
			for(j=0;j<wow2[i].length;j++)
			{
				System.out.println(wow2[i][j]);
			}
			System.out.println("i="+i);
		}
	}*/
}
