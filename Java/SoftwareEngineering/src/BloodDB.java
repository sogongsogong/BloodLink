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

import org.apache.*;
import org.apache.http.*;
public class BloodDB {

	
	public BloodDB()
	{
		// /BDC/search ?parameter&parameter&parameter
		
	}

	public String getLogin()
	{
		 String address = ""; // �����ּ�
		    StringBuffer buffer = null;
		    try{
		        //����, �۽�
		        String parameter = "?"; //�Ķ����, ����1=��1
		        String value = "����1=" + URLEncoder.encode("��1","utf-8"); // �� �������� URLEncoder�� ���ڵ��ؼ� �� �־�����մϴ�.  
		        parameter += value; // �Ķ���ʹ� ?����1=��1&����2=��2... �̷��� ����ǰ��, &�� �Ķ����(����) ���е˴ϴ�.
		        address += parameter; //���������� �����ּҿ� �Ķ���Ͱ��� �������� ��û�ϴ� url�� �ϼ��˴ϴ�. �ּ��� �κ��� �����ϽŴٰ� ���ø� �ǿ�

		        URL url = new URL(address);
		        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		        connection.setRequestMethod(""); // POST, GET, PUT �� ��

		        //����
		        BufferedReader reader = null;
		        int code = connection.getResponseCode();
		        if(code == 200) {
		            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        } else {
		            //����ȵ�
		            //reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		        }
		        buffer = new StringBuffer();
		        String string;
		        while((string = reader.readLine()) != null) {
		            buffer.append(string);
		        }
		        reader.close();
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
