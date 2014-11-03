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
 * Class representing page reciever, which can send GET or HEAD HTTP request
 * with user specified {@link java.util.List} of HTTP
 * {@link org.presentation.model.Header} to web page given by
 * {@link org.presentation.model.LinkURL} and returns response in form of
 * {@link org.presentation.webcrawler.impl.ReceiverResponse}. It can also
 * download page content to {@link org.presentation.model.PageContent} if the
 * page Content-Type is HTML or CSS. It handles HTTP and HTTPS protocol.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
@Dependent
public class PageReceiver implements MessageProducer {

    //Inject logger
    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    //Message logger for this resource
    private MessageLogger messageLogger;
    //Set of already marked hostnames with invalid certificate
    private final Set<String> hostnames = new HashSet<>();

    //Constant for GET method
    private static final String GET = "GET";
    //Constant for HEAD method
    private static final String HEAD = "HEAD";
    //Constant for HTTP protocol
    private static final String HTTP = "http";
    //Constant fot HTTPS protocol
    private static final String HTTPS = "https";

    /**
     * {@inheritDoc}
     */
    @Override
    public void offerMsgLoggerContainer(MessageLoggerContainer messageLoggerContainer) {
        messageLogger = messageLoggerContainer.createLogger("Page Receiver");
    }

    /**
     * Sends HTTP HEAD request to {@link org.presentation.model.LinkURL}
     * address, using user specified {@link java.util.List} of HTTP
     * {@link org.presentation.model.Header}.
     *
     * @param linkURL {@link org.presentation.model.LinkURL} address of the web
     * page
     * @param addHeaders {@link java.util.List} of HTTP
     * {@link org.presentation.model.Header}, specified by user, used for the
     * request
     * @return {@link org.presentation.webcrawler.impl.ReceiverResponse} for the
     * HTTP HEAD request
     * @throws java.net.MalformedURLException If an unknown protocol is
     * specified.
     * @throws java.io.IOException If request fails in any way.
     */
    public ReceiverResponse checkPage(LinkURL linkURL, List<Header> addHeaders) throws MalformedURLException, IOException {
        LOG.log(Level.INFO, "Starting checkPage(HEAD) on {0}", linkURL.getUrl());
        return connectToPage(linkURL, addHeaders, HEAD);
    }

    /**
     * Sends HTTP HEAD request to {@link org.presentation.model.LinkURL}
     * address, using user specified {@link java.util.List} of HTTP
     * {@link org.presentation.model.Header} and if it is HTML or CSS file,
     * decided by its Content-Type HTTP attribute, sends also GET request and
     * downloads {@link org.presentation.model.PageContent}.
     *
     * @param linkURL {@link org.presentation.model.LinkURL} address of the web
     * page
     * @param addHeaders {@link java.util.List} of HTTP
     * {@link org.presentation.model.Header}, specified by user, used for the
     * request
     * @return {@link org.presentation.webcrawler.impl.ReceiverResponse} for the
     * HTTP request; may contain {@link org.presentation.model.PageContent}
     * @throws java.net.MalformedURLException If an unknown protocol is
     * specified.
     * @throws java.io.IOException If request fails in any way.
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
     * Short version of {@link #connectToPage(org.presentation.model.LinkURL, java.util.List, java.lang.String, java.lang.Boolean)
     * } automaticly deciding, if {@link PageContent} should be downloaded.
     *
     * @param linkURL {@link LinkURL} address of the web page
     * @param addHeaders {@link List} of HTTP {@link Header}, specified by user,
     * used for the request
     * @param method {@link String} name of the selected HTTP method
     * @return {@link ReceiverResponse} for the HTTP request; may contain
     * {@link PageContent}
     * @throws MalformedURLException If an unknown protocol is specified.
     * @throws IOException If request fails in any way.
     */
    private ReceiverResponse connectToPage(LinkURL linkURL, List<Header> addHeaders, String method) throws MalformedURLException, IOException {
        return connectToPage(linkURL, addHeaders, method, method.equals(GET));
    }

    /**
     * Sets own certificate verification for the specified HTTPS connection,
     * which allows to proceed through any certificate, but invalid certificate
     * sends warning message to {@link MessageLogger}.
     *
     * @param connection HTTPS connection to {@link LinkURL} address
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
     * Sends HEAD or GET HTTP request, using user specified {@link List} of HTTP
     * {@link Header}, to {@link LinkURL} address and returns its response in
     * {@link ReceiverResponse}, which may also content {@link PageContent}
     * depending on used method.
     *
     * @param linkURL {@link LinkURL} address of the web page
     * @param addHeaders {@link List} of HTTP {@link Header}, specified by user,
     * used for the request
     * @param method {@link String} name of the selected HTTP method
     * @param getContent {@link Boolean} indentifing if method should get the
     * {@link PageContent}
     * @return {@link ReceiverResponse} for the HTTP request; may contain
     * {@link PageContent}
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
     * Recieves {@link String} page content from {@link BufferedReader} open to
     * specified web page.
     *
     * @param in {@link BufferedReader} from which is content readed
     * @return {@link String} whole page content
     * @throws IOException If recieving of content fails in any way.
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
