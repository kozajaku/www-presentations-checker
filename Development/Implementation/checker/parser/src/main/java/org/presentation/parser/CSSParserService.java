package org.presentation.parser;

import java.util.List;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * @author Adam Kugler
 * @version 1.0
 */
public interface CSSParserService {

    /**
     *
     * @param sourceCode
     * @param baseURL
     * @return
     */
    public List<ParsedLinkResponse> parseLinks(PageContent sourceCode, LinkURL baseURL);

    /**
     *
     * @param sourceCode
     */
	//public CSSSelectorTree parseWholeCSSPage(PageContent sourceCode);
}
