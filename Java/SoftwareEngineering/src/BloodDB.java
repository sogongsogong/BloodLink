import java.sql.Connection;

import java.util.*;
import java.sql.DriverManager;
import java.util.Map;
import java.awt.List;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
public class BloodDB {


//	private final String URL = "jdbc:mysql://58.235.168.126:3306/plan?useUnicode=true&characterEncoding=utf8";
	
	public BloodDB()
	{
		
	}

	public void getC() throws Exception
	{
		URL url = new URL("http://javaking75.blog.me/rss");
        
        // ���ڿ��� URL ǥ��
        System.out.println("URL :" + url.toExternalForm());
        
        // HTTP Connection ���ϱ� 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        // ��û ��� ���� ( GET or POST or .. ������ �������������� GET ��� )
        conn.setRequestMethod("GET"); 
        
        // ���� Ÿ�Ӿƿ� ���� 
        conn.setConnectTimeout(3000); // 3�� 
        // �б� Ÿ�Ӿƿ� ���� 
        conn.setReadTimeout(3000); // 3�� 
        
        // ��û ��� ���ϱ�
        System.out.println("getRequestMethod():" + conn.getRequestMethod());
        // ���� ������ ���� ���ϱ�
        System.out.println("getContentType():" + conn.getContentType());
        // ���� �ڵ� ���ϱ�
        System.out.println("getResponseCode():"    + conn.getResponseCode());
        // ���� �޽��� ���ϱ�
        System.out.println("getResponseMessage():" + conn.getResponseMessage());
        
        
        // ���� ����� ������ ��� ���
        for (Map.Entry<String, java.util.List<String>> header : conn.getHeaderFields().entrySet()) {
            for (String value : header.getValue()) {
                System.out.println(header.getKey() + " : " + value);
            }
        }
        
        // ���� ����(BODY) ���ϱ�        
        try (InputStream in = conn.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            byte[] buf = new byte[1024 * 8];
            int length = 0;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            System.out.println(new String(out.toByteArray(), "UTF-8"));            
        }
        
        // ���� ����
        conn.disconnect();
	}
}
