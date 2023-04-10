package at.jku.swtesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpClientTest {

	@Mock
	private URL mockURL;

	@Test
	public void testGetContentOnline() throws MalformedURLException {
		HttpClient myClient = new HttpClient();
		String page = myClient.getContent(new URL("https://www.jku.at"));
		assertTrue(page.contains("<title>JKU - Johannes Kepler UniversitÃ¤t Linz</title>"));
	}

	@Test
	public void testGetContentMocked() throws IOException {
		HttpClient myClient = mock(HttpClient.class);
		this.mockURL = new URL("https://www.jku.at");
		when(myClient.getContent(this.mockURL)).thenReturn("It works");
		String mockContent = myClient.getContent(this.mockURL);
		assertEquals("It works", mockContent);
	}

	}
