package org.presentation.parser;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * Implementation of {@link AbstractCode} representing code that is neither html, nor css.
 * @author radio.koza
 */
public class OtherCode extends AbstractCode {

    protected ContentType contentType;
    protected PageContent pageContent;

    protected OtherCode(ContentType contentType, PageContent pageContent, LinkURL link) {
        this.pageLink = link;
        this.contentType = contentType;
        this.pageContent = pageContent;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public PageContent getPageContent() {
        return pageContent;
    }

    @Override
    public CodeType getType() {
        return CodeType.OTHER_CODE;
    }

}
