package org.presentation.webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.util.List;
import org.presentation.model.ContentType;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.MessageLogger;

/**
 * Default implementation of PageReciever
 *
 * @author Jindřich Máca
 * @version 1.0
 */
public class PageReceiver {

    private final MessageLogger messageLogger;

    private static final String GET = "GET";
    private static final String HEAD = "HEAD";
    private static final String HTTP = "http";
    private static final String HTTPS = "htpps";

    public PageReceiver(MessageLogger messageLogger) {
        this.messageLogger = messageLogger;
    }

    public ReceiverResponse checkPage(LinkURL linkURL, List<Header> addHeaders) throws MalformedURLException, IOException {
        return connectToPage(linkURL, addHeaders, HEAD, false);
    }

    public ReceiverResponse getPage(LinkURL linkURL, List<Header> addHeaders) throws MalformedURLException, IOException {
        return connectToPage(linkURL, addHeaders, GET, true);
    }

    private ReceiverResponse connectToPage(LinkURL linkURL, List<Header> addHeaders, String method, Boolean getContent) throws IOException {
        ReceiverResponse response = new ReceiverResponse();
        URL url = new URL(linkURL.getUrl());
        HttpURLConnection connection;
        switch (url.getProtocol().toLowerCase()) {
            case HTTP: {
                connection = (HttpURLConnection) url.openConnection();
                break;
            }
            case HTTPS: {
                connection = (HttpsURLConnection) url.openConnection();
                break;
            }
            default: {
                throw new IOException("Protocol " + url.getProtocol() + " is not supported.");
            }
        }
        connection.setRequestMethod(method);
        connection.setUseCaches(false);
        for (Header addHeader : addHeaders) {
            connection.setRequestProperty(addHeader.getKey(), addHeader.getValue());
        }
        connection.connect();

        response.setStateCode(connection.getResponseCode());
        switch (connection.getResponseCode()) {
            case 200: {
                String contentType = connection.getHeaderField("Content-Type");
                String[] split;
                split = contentType.split(";\\s*(charset=)?");
                response.setContentType(new ContentType(split[0]));

                if (getContent && method.equals(GET)) {
                    String coding;
                    if (split.length == 2) {
                        coding = split[1];
                    } else {
                        coding = "UTF-8";
                    }
                    response.setSourceCode(new PageContent(recievePageContent(new BufferedReader(new InputStreamReader(connection.getInputStream(), coding)))));
                }
                break;
            }
            case 301: {
                String location = connection.getHeaderField("Location");
                if (!(location.isEmpty() || location.equals(linkURL.getUrl()))) {
                    response = connectToPage(new LinkURL(location), addHeaders, method, getContent);
                }
                break;
            }
            case 302: {
                String location = connection.getHeaderField("Location");
                if (!(location.isEmpty() || location.equals(linkURL.getUrl()))) {
                    response = connectToPage(new LinkURL(location), addHeaders, method, getContent);
                }
                break;
            }
            case 405: {
                if (method.equals(HEAD)) {
                    response = connectToPage(linkURL, addHeaders, GET, false);
                }
                break;
            }
        }

        if (response.getContentType().getContentType() == null) {
            response.setContentType(new ContentType(""));
        }

        if (response.getSourceCode().getContent() == null) {
            response.setSourceCode(new PageContent(""));
        }

        return response;
    }

    private String recievePageContent(BufferedReader in) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        return response.toString();
    }

}
