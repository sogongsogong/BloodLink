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
	
	private JTable table;//������ ���̺�
	private JScrollPane jscroll;//������ ���̺��� ���� scroll
	private DefaultTableModel mod;
	private JLabel title;//Ÿ��Ʋ "��������������"
	private JComboBox<String> comboStandard, comboStatus;//��ȸ������ ����ִ� �޺��ڽ�,��û���°� ����ִ� �޺��ڽ�
	private JTextField searchText;//�˻� �ؽ�Ʈ �ʵ�
	private JButton search,delete,approve;//����� ��ư��
	private String columnNames[]={ "������ȣ", "������̸�", "������̸���","��û����" };
	private String rowData[][],rowDataTemp[][],original[][];//���̺� �� �����͵�
	private JFrame frame;
	private String id;
	private BloodDB db;
	
	public BloodBoard(String id)
	{
		super("BLOOD_Board");
		setLocation(360, 225);//��������ġ ����
		setSize(1200,730);// ������ũ�� ����
		setContentPane(new JLabel(new ImageIcon("icon\\pulse1.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close�ڽ�
		setLayout(null);//���̾ƿ� �ΰ�
		
		this.id=id;//ID�޾ƿ���
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image img= tk.getImage("icon\\frameicon.png");
		setIconImage(img);
		
		title = new JLabel("�������� ����û ����");//title�ֱ�
		title.setBounds(85, 50, 280, 40);
		title.setFont(new Font("", 1, 25));
		add(title);
		
		SearchInfo();//���̺� ���� ��ȸ Ȥ�� ���ΰ�ħ
		
        TableSet(rowData);//���̺� ���� �� ����
     
		ComboBoxSet();//�޺��ڽ�����
		
		searchText = new JTextField();//search�ؽ�Ʈ��Ʈ
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
					search.doClick();//�˻� ĭ���� ENTER Ű �Է½� "search"��ư Ŭ��
				}
				
			}
		};
		searchText.addKeyListener(k);
		Handler h = new Handler();
		
		ButtonSet();//��ư����
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
				if(searchText.getText().equals(""))//���ΰ�ħ
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
				else//�˻�
				{
					SearchInfo(searchText.getText(),comboStandard.getSelectedIndex(),comboStatus.getSelectedIndex());
					jscroll.setVisible(false);
					TableSet(rowDataTemp);
				}
			}
			else if(event.getSource()==delete)//����
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
					JOptionPane.showMessageDialog(null, "���õ� ���� �����ϴ�.");
					return ;
				}
				
			}
			else if(event.getSource()==approve)//����
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
					JOptionPane.showMessageDialog(null, "���õ� ���� �����ϴ�.");
					return ;
				}
			}
		}
	}
	public void SearchInfo()//rowdata�ҷ����� �� ���ΰ�ħ
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
			for(int i=0;i<rowData.length;i++)//������ �ٲٱ�
			{
				if(rowData[i][3].equals("true"))
					rowData[i][3]="�����";
				else
					rowData[i][3]="���Ϸ�";
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
	public void SearchInfo(String a,int index,int sindex)//rowdata�ҷ����� �� ���ΰ�ħ
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
			for(int i=0;i<rowData.length;i++)//������ �ٲٱ�
			{
				if(rowData[i][3].equals("true"))
					rowData[i][3]="�����";
				else
					rowData[i][3]="���Ϸ�";
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
		
		if(sindex==0)//��û����(��ü)
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
		else if(sindex==1)//�����
		{
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a)&&original[i][3].equals("�����"))
					count++;
			}
			dataTemp=new String[count][4];
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a)&&original[i][3].equals("�����"))
					dataTemp[count++]=original[i];
			}
			
		}
		else if(sindex==2)//���Ϸ�
		{
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a)&&original[i][3].equals("���Ϸ�"))
					count++;
			}
			dataTemp=new String[count][4];
			count=0;
			for(int i=0;i<original.length;i++)
			{
				if(original[i][index].equals(a)&&original[i][3].equals("���Ϸ�"))
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
				status="�����";
			else if(sindex==2)
				status="���Ϸ�";
			
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
	public void approveInfo(int[] list)//����
	{
		String message = "";
		
		int i=0;
		int j=0;

		while(true)
		{
			String address = "https://lit-escarpment-60921.herokuapp.com/mi/" + id + "/call?number=";
			if(i==list[j])
			{
				if(rowData[i][3].equals("�����") || rowData[i][3].equals("true")) {
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
				if(rowData[i][3].equals("�����"))
					rowData[i][3]="���Ϸ�";
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
            // ���� ������ ���뿡 �̾ ������ true��, ���� ������ ���ְ� ���� ������ false�� �����Ѵ�.
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
	public void deleteInfo(int[] list)//����
	{
		String message = "";
		
		int i=0;
		int j=0;

		while(true)
		{
			String address = "https://lit-escarpment-60921.herokuapp.com/mi/" + id + "/recall?number=";
			if(i==list[j])
			{
				if(rowData[i][3].equals("���Ϸ�") || rowData[i][3].equals("false")) {
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
            // ���� ������ ���뿡 �̾ ������ true��, ���� ������ ���ְ� ���� ������ false�� �����Ѵ�.
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
	public void ButtonSet()//button�� ����
	{
		search = new JButton("��ȸ");//search��ư
		search.setBounds(855,50,80,40);
		search.setFont(new Font("", 1, 15));
		
		delete = new JButton("����");//delete ��ư
		delete.setBounds(945,50,80,40);
		delete.setFont(new Font("", 1, 15));

		approve = new JButton("����");//approve ��ư
		approve.setBounds(1035,50,80,40);
		approve.setFont(new Font("", 1, 15));
		add(delete);
		add(search);
		add(approve);
	}
	public void ComboBoxSet()//combobox����
	{
		String status[] = {"��û����(��ü)","�����","���Ϸ�"};
		comboStatus= new JComboBox<String>(status);//������ �޺��ڽ�����
		comboStatus.setBounds(385,50,130,40);
		comboStatus.setBackground(Color.WHITE);
		comboStatus.setFont(new Font("", 1, 15));
		
		String standard[]= {"������ȣ", "������̸�", "������̸���" };
		comboStandard = new JComboBox<String>(standard);//comboStandardbox����
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
			public boolean isCellEditable(int row,int column)//table ���Ƿ� �����Ұ�
			{
				return false;
			}
		};
		table = new JTable(mod);//table����
		table.setSelectionMode(2);//���� ���ð���
		TableWidth();//table ���� ����
        table.setFont(new Font("", 1, 15));//table font ũ������
        table.setRowHeight(40);
        TableFontSort();//�۾� ��� ����
   
        jscroll = new JScrollPane(table);//scroll ����
		jscroll.setBounds(85, 120, 1030, 500);//ũ�⼳��
		jscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//�������⽺ũ��
		add(jscroll);
		return ;
	}
	public void TableWidth()//���̺� �� ��������
	{
		table.getColumn("������ȣ").setPreferredWidth(5);
		table.getColumn("������̸�").setPreferredWidth(20);
		table.getColumn("������̸���").setPreferredWidth(110);
		table.getColumn("��û����").setPreferredWidth(10);
	}
	public void TableFontSort()//table �۾� ����
	{
		// DefaultTableCellHeaderRenderer ���� (��� ������ ����)
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();

				 
		// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
				
		// ������ ���̺��� ColumnModel�� ������
		TableColumnModel tcmSchedule = table.getColumnModel();

				 
		// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
		tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
	}
/*	public void parsingData(String original)
	{
		String test="BDC{" +
	            ", number='" + "2434" + '\'' +
	            ", type='" + "b" + '\'' +
	            ", name='" + "������" + '\'' +
	            ", birth=" + "1996-02-26" +
	            ", sex=" + "��" +
	            ", date=" + "2018-06-18" +
	            ", place='" + "��ϴ���" + '\'' +
	            ", owner='" + "������" + '\'' +
	            ", usage='" + "�����" + '\'' +
	            ", valid=" + "true" +
	            '}'
	            +"BDC{" +
	                    ", number='" + "2434" + '\'' +
	                    ", type='" + "b" + '\'' +
	                    ", name='" + "������" + '\'' +
	                    ", birth=" + "1996-02-26" +
	                    ", sex=" + "��" +
	                    ", date=" + "2018-06-18" +
	                    ", place='" + "��ϴ���" + '\'' +
	                    ", owner='" + "������" + '\'' +
	                    ", usage='" + "�����" + '\'' +
	                    ", valid=" + "true" +
	                    '}'
	                    +"BDC{" +
	                    ", number='" + "2434" + '\'' +
	                    ", type='" + "b" + '\'' +
	                    ", name='" + "������" + '\'' +
	                    ", birth=" + "1996-02-26" +
	                    ", sex=" + "��" +
	                    ", date=" + "2018-06-18" +
	                    ", place='" + "��ϴ���" + '\'' +
	                    ", owner='" + "������" + '\'' +
	                    ", usage='" + "�����" + '\'' +
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
