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
		 String address = "공백/login"; // 서버주소
		    StringBuffer buffer = null;
		    try{
		        //연결, 송신
		        String parameter = "?"; //파라미터, 변수1=값1
		        String value = "account=" + URLEncoder.encode(id,"utf-8") + "&" + "password=" + URLEncoder.encode(pw,"utf-8"); // 값 넣을때는 URLEncoder로 인코딩해서 값 넣어줘야합니다.  
		        parameter += value; // 파라미터는 ?변수1=값1&변수2=값2... 이렇게 연결되고요, &로 파라미터(변수) 구분됩니다.
		        address += parameter; //실질적으로 서버주소에 파라미터값이 더해져서 요청하는 url이 완성됩니다. 주석된 부분은 수정하신다고 보시면 되요

		        URL url = new URL(address);
		        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		        connection.setRequestMethod("GET"); // POST, GET, PUT 중 택

		        //수신
		        BufferedReader reader = null;
		        int code = connection.getResponseCode();
		        if(code == 200) {
		            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        } else {
		            //연결안됨
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
	
	public String getRowData(String id)
	{
		 String address = "공백/search"; // 서버주소
		    StringBuffer buffer = null;
		    try{
		        //연결, 송신
		        String parameter = "?"; //파라미터, 변수1=값1
		        String value = "account=" + URLEncoder.encode(id,"utf-8"); // 값 넣을때는 URLEncoder로 인코딩해서 값 넣어줘야합니다.  
		        parameter += value; // 파라미터는 ?변수1=값1&변수2=값2... 이렇게 연결되고요, &로 파라미터(변수) 구분됩니다.
		        address += parameter; //실질적으로 서버주소에 파라미터값이 더해져서 요청하는 url이 완성됩니다. 주석된 부분은 수정하신다고 보시면 되요

		        URL url = new URL(address);
		        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		        connection.setRequestMethod("GET"); // POST, GET, PUT 중 택
		        
		        //수신
		        BufferedReader reader = null;
		        int code = connection.getResponseCode();
		        if(code == 200) {
		            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        } else {
		            //연결안됨
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
