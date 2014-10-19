package org.presentation.htmlvalidatortest;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author radio.koza
 */
public class Receiver {
    
    public InputStream getSoapResponse(String pageToTest) throws IOException{
        HttpURLConnection con = (HttpURLConnection) (new URL("http://validator.w3.org/check?output=soap12&uri=" + pageToTest).openConnection());
        return con.getInputStream();
    }
    
}
