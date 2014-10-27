package org.presentation.parser.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.model.PageContent;
import org.presentation.parser.HTMLParserService;
import org.presentation.parser.ParsedLinkResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.presentation.model.LinkURL;
import org.presentation.model.graph.LinkSourceType;

/**
 * Default implementation of HTML parser.
 *
 * @author Adam Kugler
 * @version 1.0
 */
@Dependent
public class HTMLParserServiceDefault implements HTMLParserService {

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ParsedLinkResponse> parseLinks(PageContent sourceCode, LinkURL baseURL) {
        LOG.info("parsing started");
        List<ParsedLinkResponse> parsedLinks = new ArrayList<>();
        if (sourceCode == null) {
            LOG.warning("sourceCode is null!");
            return parsedLinks;
        }
        Document doc = Jsoup.parse(sourceCode.getContent(), baseURL.getUrl());
        Elements links = doc.select("a[href]");
        Elements images = doc.select("img[src]");
        Elements scripts = doc.select("script[src]");
        Elements imports = doc.select("link[href]");
        for (Element link : links) {
            LinkURL destination = new LinkURL(removeHashFromURL(link.attr("abs:href")));
            //log
            //LOG.log(Level.INFO, "Link found: {0} A_HREF {1}", new Object[]{destination.getUrl(), link.text()});
            if (destination.checkURL()) {
                parsedLinks.add(new ParsedLinkResponse(destination, LinkSourceType.A_HREF, link.text()));
            }
        }
        for (Element link : images) {
            LinkURL destination = new LinkURL(removeHashFromURL(link.attr("abs:src")));
            //log
            //LOG.log(Level.INFO, "Link found: {0} IMG_SRC {1}", new Object[]{destination.getUrl(), link.attr("alt")});
            if (destination.checkURL()) {
                parsedLinks.add(new ParsedLinkResponse(destination, LinkSourceType.IMG_SRC, link.attr("alt")));
            }
        }
        for (Element link : scripts) {
            LinkURL destination = new LinkURL(removeHashFromURL(link.attr("abs:src")));
            //log
            //LOG.log(Level.INFO, "Link found: {0} SCRIPT_SRC script", destination.getUrl());
            if (destination.checkURL()) {
                parsedLinks.add(new ParsedLinkResponse(destination, LinkSourceType.SCRIPT_SRC, "script"));
            }
        }
        for (Element link : imports) {
            LinkURL destination = new LinkURL(removeHashFromURL(link.attr("abs:href")));
            //log
            //LOG.log(Level.INFO, "Link found: {0} SCRIPT_SRC import", destination.getUrl());
            if (destination.checkURL()) {
                parsedLinks.add(new ParsedLinkResponse(destination, LinkSourceType.SCRIPT_SRC, "import"));
            }
        }
        LOG.info("parsing finished");
        return parsedLinks;
    }

    /**
     * Removes # and everything behing it in URL.
     *
     * @param url an URL with #...
     * @return an URL without #...
     */
    private String removeHashFromURL(String url) {
        return url.split("#")[0];
    }
//	public HTMLTagTree parseWholeHTMLPage(PageContent sourceCode){
//		return null;
//	}

}
