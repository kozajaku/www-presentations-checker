package org.presentation.webcrawler;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.presentation.model.LinkURL;
import org.presentation.model.logging.MessageLogger;

/**
 *
 * @author Jindřich Máca
 */
public class PageReciever {

    //TODO: Required parameters:
    //Expiration time
    //Text coding UTF-8, Windows-1250 atd.
    //Accept-Language?
    //Cookies?
    //Content-Type to proced download content?
    
    private List<String> cookies;
    private HttpsURLConnection connection;
//    private final String USER_AGENT = "Mozilla/5.0";

    private MessageLogger messageLogger;

    public PageReciever(MessageLogger messageLogger) {
        this.messageLogger = messageLogger;
    }

    public ReceiverResponse getPage(LinkURL linkURL) throws MalformedURLException, IOException {
        URL url = new URL(linkURL.getUrl());
        connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("HEAD");
        connection.setUseCaches(false);
//        connection.setRequestProperty("User-Agent", USER_AGENT);
//        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        connection.setRequestProperty("Accept-Language", "cs,en;q=0.8,en-US;q=0.6");
        if (getCookies() != null) {
            for (String cookie : this.cookies) {
                connection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }

        if (connection.getResponseCode() != 200) {
            return null;
        }

        setCookies(connection.getHeaderFields().get("Set-Cookie"));
        return null;
    }

    private void setCookies(List<String> cookies) {
        this.cookies = cookies;
    }

    private List<String> getCookies() {
        return cookies;
    }

}
