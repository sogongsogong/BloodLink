import java.sql.Connection;

import java.util.*;
import java.sql.DriverManager;
import java.util.Map;

import javax.xml.ws.Response;
import javax.xml.ws.soap.AddressingFeature.Responses;

import java.awt.List;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.net.ProtocolException;


public class BloodDB {

	
	public BloodDB()
	{
		// /BDC/search ?parameter&parameter&parameter
		
	}

	public String getLogin(String id,String pw)
	{
		 String address = "https://radiant-journey-86060.herokuapp.com/mi/login"; // �����ּ�
		    StringBuffer buffer = null;
		    try{
		        //����, �۽�
		        String parameter = "?"; //�Ķ����, ����1=��1
		        String value = "account=" + URLEncoder.encode(id,"utf-8") + "&" + "password=" + URLEncoder.encode(pw,"utf-8"); // �� �������� URLEncoder�� ���ڵ��ؼ� �� �־�����մϴ�.  
		        parameter += value; // �Ķ���ʹ� ?����1=��1&����2=��2... �̷��� ����ǰ��, &�� �Ķ����(����) ���е˴ϴ�.
		        address += parameter; //���������� �����ּҿ� �Ķ���Ͱ��� �������� ��û�ϴ� url�� �ϼ��˴ϴ�. �ּ��� �κ��� �����ϽŴٰ� ���ø� �ǿ�

		        URL url = new URL(address);
		        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		        connection.setRequestMethod("GET"); // POST, GET, PUT �� ��

		        //����
		        BufferedReader reader = null;
		        int code = connection.getResponseCode();
		        if(code == 200) {
		            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        } else {
		            //����ȵ�
		        	System.out.println("error");
		            //reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		        }
		        buffer = new StringBuffer();
		        String string;
		        while((string = reader.readLine()) != null) {
		            buffer.append(string);
		        }
		        reader.close();
		        connection.disconnect();
		    } catch (ProtocolException e) {
		        e.printStackTrace();
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (NullPointerException e) {
		        e.printStackTrace();
		    }
		    return buffer.toString();
	}
	
	public String getRowData(String id)//ó�� rowdata��������
	{
		 String address = "https://radiant-journey-86060.herokuapp.com/mi/"+id; // �����ּ�
		    StringBuffer buffer = null;
		    try{
		        //����, �۽�
	//	        String parameter = "?"; //�Ķ����, ����1=��1
	//	        String value = "account=" + URLEncoder.encode(id,"utf-8"); // �� �������� URLEncoder�� ���ڵ��ؼ� �� �־�����մϴ�.  
		//        parameter += value; // �Ķ���ʹ� ?����1=��1&����2=��2... �̷��� ����ǰ��, &�� �Ķ����(����) ���е˴ϴ�.
		 //       address += parameter; //���������� �����ּҿ� �Ķ���Ͱ��� �������� ��û�ϴ� url�� �ϼ��˴ϴ�. �ּ��� �κ��� �����ϽŴٰ� ���ø� �ǿ�

		        URL url = new URL(address);
		        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		        connection.setRequestMethod("GET"); // POST, GET, PUT �� ��
		        
		        //����
		        BufferedReader reader = null;
		        int code = connection.getResponseCode();
		        if(code == 200) {
		            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        } else {
		            //����ȵ�
		        	System.out.println("error");
		            //reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		        }
		        buffer = new StringBuffer();
		        String string;
		        while((string = reader.readLine()) != null) {
		            buffer.append(string);
		        }
		        reader.close();
		        connection.disconnect();
		    } catch (ProtocolException e) {
		        e.printStackTrace();
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (NullPointerException e) {
		        e.printStackTrace();
		    }
		    return buffer.toString();
	}
	public String updateValid(String number)//��ȿ�� ����
	{
		 String address = "https://radiant-journey-86060.herokuapp.com/bdc/"+number+"/update"; // �����ּ�
		    StringBuffer buffer = null;
		    try{
		        //����, �۽�
		    	String parameter = "?"; //�Ķ����, ����1=��1
		        String value = "valid=" + URLEncoder.encode("false","utf-8"); // �� �������� URLEncoder�� ���ڵ��ؼ� �� �־�����մϴ�.  
		        parameter += value; // �Ķ���ʹ� ?����1=��1&����2=��2... �̷��� ����ǰ��, &�� �Ķ����(����) ���е˴ϴ�.
		        address += parameter; //���������� �����ּҿ� �Ķ���Ͱ��� �������� ��û�ϴ� url�� �ϼ��˴ϴ�. �ּ��� �κ��� �����ϽŴٰ� ���ø� �ǿ�

		        URL url = new URL(address);
		        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		        connection.setRequestMethod("PUT"); // POST, GET, PUT �� ��
		        
		        //����
		        BufferedReader reader = null;
		        int code = connection.getResponseCode();
		        if(code == 200) {
		            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        } else {
		            //����ȵ�
		        	System.out.println("error");
		            //reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		        }
		        buffer = new StringBuffer();
		        String string;
		        while((string = reader.readLine()) != null) {
		            buffer.append(string);
		        }
		        reader.close();
		        connection.disconnect();
		    } catch (ProtocolException e) {
		        e.printStackTrace();
		    } catch (MalformedURLException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
		    } catch (NullPointerException e) {
		        e.printStackTrace();
		    }
		    return buffer.toString();
	}
}
