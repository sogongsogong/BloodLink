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
        
        // 문자열로 URL 표현
        System.out.println("URL :" + url.toExternalForm());
        
        // HTTP Connection 구하기 
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지않으면 GET 방식 )
        conn.setRequestMethod("GET"); 
        
        // 연결 타임아웃 설정 
        conn.setConnectTimeout(3000); // 3초 
        // 읽기 타임아웃 설정 
        conn.setReadTimeout(3000); // 3초 
        
        // 요청 방식 구하기
        System.out.println("getRequestMethod():" + conn.getRequestMethod());
        // 응답 콘텐츠 유형 구하기
        System.out.println("getContentType():" + conn.getContentType());
        // 응답 코드 구하기
        System.out.println("getResponseCode():"    + conn.getResponseCode());
        // 응답 메시지 구하기
        System.out.println("getResponseMessage():" + conn.getResponseMessage());
        
        
        // 응답 헤더의 정보를 모두 출력
        for (Map.Entry<String, java.util.List<String>> header : conn.getHeaderFields().entrySet()) {
            for (String value : header.getValue()) {
                System.out.println(header.getKey() + " : " + value);
            }
        }
        
        // 응답 내용(BODY) 구하기        
        try (InputStream in = conn.getInputStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            byte[] buf = new byte[1024 * 8];
            int length = 0;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            System.out.println(new String(out.toByteArray(), "UTF-8"));            
        }
        
        // 접속 해제
        conn.disconnect();
	}
}
