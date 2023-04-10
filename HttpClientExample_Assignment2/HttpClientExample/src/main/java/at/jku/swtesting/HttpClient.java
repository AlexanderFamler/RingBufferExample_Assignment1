package at.jku.swtesting;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Example class for retrieving the content of a web page. Original code see: 
 * Catalin Tudose, JUnit in Action, Third Ed. Manning Publications, 2020. 
 */
public class HttpClient {
	
	public String getContent(URL url) {
		StringBuffer content = new StringBuffer();
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			InputStream is = connection.getInputStream();
			int count;
			while (-1 != (count = is.read())) {
				content.append(new String(Character.toChars(count)));
			}
		} catch (IOException e) {
			return null;
		}
		return content.toString();
	}

	public static void main(String[] args) {
		HttpClient myClient = new HttpClient();
		try {
			System.out.println(myClient.getContent(new URL("https://www.jku.at")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

}