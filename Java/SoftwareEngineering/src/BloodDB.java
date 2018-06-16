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
import org.apache.*;
import org.apache.http.*;
public class BloodDB {

	
	public BloodDB()
	{
		// /BDC/search ?parameter&parameter&parameter
		
	}

	public boolean getLogin() throws Exception
	{
		URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

	 	String input = "{\"qty\":100,\"name\":\"iPad 4\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
		
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
		throw new RuntimeException("Failed : HTTP error code : "
			+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		conn.disconnect();

		if(output.equals("true"))
			return true;
		else
			return false;
	}
}
