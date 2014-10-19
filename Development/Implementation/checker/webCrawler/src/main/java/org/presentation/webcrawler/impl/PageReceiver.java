package org.presentation.webcrawler.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import org.presentation.model.ContentType;
import org.presentation.model.Header;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;
import org.presentation.model.logging.MessageLogger;
import org.presentation.model.logging.MessageLoggerContainer;
import org.presentation.model.logging.MessageProducer;
import org.presentation.model.logging.WarningMsg;

/**
 * Default implementation of PageReciever
 *
 * @author Jindřich Máca
 */
@Dependent
public class PageReceiver implements MessageProducer {

    /**
     * Inject logger.
     */
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    /**
     * Message logger for this resource.
     */
    private MessageLogger messageLogger;
    /**
     * Set of already marked hostnames with invalid certificate.
     */
    private final Set<String> hostnames = new HashSet<>();

    /**
     * Constant for GET method.
     */
    private static final String GET = "GET";
    /**
     * Constant for HEAD method.
     */
    private static final String HEAD = "HEAD";
    /**
     * Constant for HTTP protocol.
     */
    private static final String HTTP = "http";
    /**
     * Constant fot HTTPS protocol.
     */
    private static final String HTTPS = "https";

    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer) {
        messageLogger = messageLoggerContainer.createLogger("Page Receiver");
    }

    /**
     * Sending HEAD request on the page.
     *
     * @param linkURL URL address of the page.
     * @param addHeaders Headers attributes specified by user.
     * @return Response for HEAD request represented by ReceiverResponse.
     * @throws MalformedURLException If an unknown protocol is specified.
     * @throws IOException If request fails in any way.
     */
    public ReceiverResponse checkPage(LinkURL linkURL, List<Header> addHeaders) throws MalformedURLException, IOException {
        LOG.log(Level.INFO, "Starting checkPage(HEAD) on {0}", linkURL.getUrl());
        return connectToPage(linkURL, addHeaders, HEAD);
    }

    /**
     * Sending HEAD request and if it is HTML or CSS, sending GET request and
     * downloads page content.
     *
     * @param linkURL URL address of the page.
     * @param addHeaders Headers attributes specified by user.
     * @return Response for request represented by ReceiverResponse.
     * @throws MalformedURLException If an unknown protocol is specified.
     * @throws IOException If request fails in any way.
     */
    public ReceiverResponse getPage(LinkURL linkURL, List<Header> addHeaders) throws MalformedURLException, IOException {
        LOG.log(Level.INFO, "Starting getPage(HEAD) on {0}", linkURL.getUrl());
        ReceiverResponse response = connectToPage(linkURL, addHeaders, HEAD);
        if (response.getContentType().isHtml() || response.getContentType().isCss()) {
            LOG.log(Level.INFO, "Starting getPage(GET) on {0}", linkURL.getUrl());
            return connectToPage(linkURL, addHeaders, GET);
        }
        return response;
    }

    /**
     * Short version of connectToPage automaticly defining, if content should be
     * downloaded.
     *
     * @param linkURL URL address of the page.
     * @param addHeaders Headers attributes specified by user.
     * @param method Name of the selected method.
     * @return Response for request represented by ReceiverResponse.
     * @throws MalformedURLException If an unknown protocol is specified.
     * @throws IOException If request fails in any way.
     */
    private ReceiverResponse connectToPage(LinkURL linkURL, List<Header> addHeaders, String method) throws MalformedURLException, IOException {
        return connectToPage(linkURL, addHeaders, method, method.equals(GET));
    }

    /**
     * Sets our own certificate verification for the specified HTTPS connection,
     * which allows to go through any certificate, but invalid certificate sends
     * warning message.
     *
     * @param connection Https connection.
     */
    private void setHostnameVerifier(HttpsURLConnection connection) {
        final HostnameVerifier ver = connection.getHostnameVerifier();
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                if (!ver.verify(hostname, session)) {
                    if (!hostnames.contains(hostname)) {
                        LOG.log(Level.WARNING, "Invalid SSL certificate on domain https://{0}", hostname);
                        WarningMsg mes = new WarningMsg();
                        mes.setMessage("Invalid certificate");
                        mes.setPage(new LinkURL("https://" + hostname));
                        messageLogger.addMessage(mes);
                        hostnames.add(hostname);
                    }
                }
                return true;
            }
        };
        connection.setHostnameVerifier(allHostsValid);
    }

    /**
     * Sends request to server.
     *
     * @param linkURL URL address of the page.
     * @param addHeaders Headers attributes specified by user.
     * @param method Name of the selected method.
     * @param getContent Indentifier if method should get the page content.
     * @return Response for request represented by ReceiverResponse.
     * @throws MalformedURLException If an unknown protocol is specified.
     * @throws IOException If request fails in any way.
     */
    private ReceiverResponse connectToPage(LinkURL linkURL, List<Header> addHeaders, String method, Boolean getContent) throws MalformedURLException, IOException {
        URL url = new URL(linkURL.getUrl());
        HttpURLConnection connection;
        switch (url.getProtocol().toLowerCase()) {
            case HTTP: {
                connection = (HttpURLConnection) url.openConnection();
                LOG.info("Created HTTP connection.");
                break;
            }
            case HTTPS: {
                connection = (HttpsURLConnection) url.openConnection();
                setHostnameVerifier((HttpsURLConnection) connection);
                LOG.info("Created HTTPS connection.");
                break;
            }
            default: {
                LOG.log(Level.WARNING, "Protocol {0} is not supported.", url.getProtocol());
                throw new IOException("Protocol " + url.getProtocol() + " is not supported.");
            }
        }
        connection.setRequestMethod(method);
        connection.setUseCaches(false);
        for (Header addHeader : addHeaders) {
            connection.setRequestProperty(addHeader.getKey(), addHeader.getValue());
        }
        connection.connect();
        LOG.info("Request sent.");

        //parts of response
        int stateCode = connection.getResponseCode();
        ContentType contentType = new ContentType("");
        PageContent pageContent = new PageContent("");

        LOG.log(Level.INFO, "Response code is {0}", connection.getResponseCode());
        switch (connection.getResponseCode()) {
            case 200: {
                String contentTypeS = connection.getHeaderField("Content-Type");
                String[] split;
                split = contentTypeS.split(";\\s*(charset=)?");
                contentType = new ContentType(split[0]);
                LOG.log(Level.INFO, "Content-Type is {0}", split[0]);

                if (getContent && method.equals(GET)) {
                    String coding;
                    if (split.length == 2) {
                        coding = split[1];
                    } else {
                        coding = "UTF-8";
                    }
                    LOG.log(Level.INFO, "Coding is {0}", coding);
                    pageContent = new PageContent(recievePageContent(new BufferedReader(new InputStreamReader(connection.getInputStream(), coding))));
                }
                break;
            }
            case 301: {
                String location = connection.getHeaderField("Location");
                if (!(location.isEmpty() || location.equals(linkURL.getUrl()))) {
                    LOG.log(Level.INFO, "Redirect 301 to {0}", location);
                    return connectToPage(new LinkURL(location), addHeaders, method, getContent);
                }
                break;
            }
            case 302: {
                String location = connection.getHeaderField("Location");
                if (!(location.isEmpty() || location.equals(linkURL.getUrl()))) {
                    LOG.log(Level.INFO, "Redirect 302 to {0}", location);
                    return connectToPage(new LinkURL(location), addHeaders, method, getContent);
                }
                break;
            }
            case 405: {
                if (method.equals(HEAD)) {
                    LOG.info("Server refused HEAD, trying GET.");
                    return connectToPage(linkURL, addHeaders, GET, false);
                }
                break;
            }
        }

        LOG.log(Level.INFO, "Finished request on {0}", linkURL.getUrl());
        return new ReceiverResponse(contentType, pageContent, stateCode);
    }

    /**
     * Downloads page content.
     *
     * @param in
     * @return String
     * @throws IOException
     */
    private String recievePageContent(BufferedReader in) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        LOG.info("Content successfuly loaded.");
        return response.toString();
    }

}
