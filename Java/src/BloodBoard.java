import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

public class BloodBoard extends JFrame{
	
	private JTable table;//������ ���̺�
	private JScrollPane jscroll;//������ ���̺��� ���� scroll
	private DefaultTableModel mod;
	private JLabel title;//Ÿ��Ʋ "��������������"
	private JComboBox<String> comboStandard, comboStatus;//��ȸ������ ����ִ� �޺��ڽ�,��û���°� ����ִ� �޺��ڽ�
	private JTextField searchText;//�˻� �ؽ�Ʈ �ʵ�
	private JButton search,delete,approve;//����� ��ư��
	private String columnNames[]={ "������ȣ", "������̸�", "������̸���","��û����" };
	private String rowData[][],rowDataTemp[][];//���̺� �� �����͵�
	private CertificationList CertList;//���������
	private JFrame frame;
	private String id;
	
	public BloodBoard(String id)
	{
		super("BLOOD_Board");
		setLocation(360, 225);//��������ġ ����
		setSize(1200,730);// ������ũ�� ����
		setContentPane(new JLabel(new ImageIcon("icon\\pulse1.jpg")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close�ڽ�
		setLayout(null);//���̾ƿ� �ΰ�

		this.id=id;
		
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image img= tk.getImage("icon\\frameicon.png");
		setIconImage(img);
		
		title = new JLabel("�������� ����û ����");//title�ֱ�
		title.setBounds(85, 50, 280, 40);
		title.setFont(new Font("", 1, 25));
		add(title);
		
		SearchInfo();//���̺� ���� ��ȸ
		
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
					SearchInfo();
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
		//	else if(event.getSource())
		//	{
				
		//	}
		}
	}
	public void SearchInfo()//rowdata�ҷ����� �� ���ΰ�ħ
	{
		try
		{
			File is = new File("C:\\Users\\june\\Desktop\\BloodLink\\Java\\SoftwareEngineering\\table.txt");
			BufferedReader bf = new BufferedReader(new FileReader(is));
			String row;
			ArrayList<String[]> rData=new ArrayList<String[]>();
			int i=0;
			
			while ((row = bf.readLine()) != null) 
			{	 
				i++;	   
			}
			bf.close();
			bf=new BufferedReader(new FileReader(is));
			
			rowData=new String[i][4];
			i=0;
			while ((row = bf.readLine()) != null) 
			{	 
				rowData[i++]=row.split(" ");   
			}
			return ;
		}
		catch(Exception e)
		{
			
		}
	}
	public void SearchInfo(String a,int index,int sindex)//rowdata�ҷ����� �� ���ΰ�ħ
	{
		try
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
			
		}
	}
	public void deleteInfo(String number)
	{
		
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
}
