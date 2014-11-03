package org.presentation.parser;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * Implementation of {@link org.presentation.parser.AbstractCode} representing
 * code that is neither html, nor css.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class OtherCode extends AbstractCode {

    protected ContentType contentType;
    protected PageContent pageContent;

    /**
     * <p>
     * Constructor for OtherCode.</p>
     *
     * @param contentType a {@link org.presentation.model.ContentType} object.
     * @param pageContent a {@link org.presentation.model.PageContent} object.
     * @param link a {@link org.presentation.model.LinkURL} object.
     */
    protected OtherCode(ContentType contentType, PageContent pageContent, LinkURL link) {
        this.pageLink = link;
        this.contentType = contentType;
        this.pageContent = pageContent;
    }

    /**
     * <p>
     * Getter for the field <code>contentType</code>.</p>
     *
     * @return a {@link org.presentation.model.ContentType} object.
     */
    public ContentType getContentType() {
        return contentType;
    }

    /**
     * <p>
     * Getter for the field <code>pageContent</code>.</p>
     *
     * @return a {@link org.presentation.model.PageContent} object.
     */
    public PageContent getPageContent() {
        return pageContent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CodeType getType() {
        return CodeType.OTHER_CODE;
    }

}
