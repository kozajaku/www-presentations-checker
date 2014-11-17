package headertest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author radio.koza
 */
public class HeaderTest {

    public static void main(String[] args) throws MalformedURLException, IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("http://www.linkedin.com/pub/daniel-kolesa/38/b09/4b8").openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.connect();
        conn.getHeaderFields().entrySet().stream().forEach((i) -> {
            System.out.println(i.getKey() + " : " + i.getValue().toString());
        });
    }
    
}
