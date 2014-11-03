package org.presentation.parser;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * Abstract class representing abstract code, that is used especially in
 * WholePageController module and its submodules.
 *
 * @author radio.koza
 * @version 1.0
 */
public abstract class AbstractCode {

    protected LinkURL pageLink;

    public LinkURL getLink() {
        return pageLink;
    }

    public abstract CodeType getType();

    /**
     * Factory method for creating new specific implementation of
     * {@link AbstractCode}. Specific implementation is resolved and decided by
     * {@link ContentType} passed as method parameter.
     *
     * @param type {@link ContentType} representing type of the code
     * @param linkURL Source address of the link
     * @param content Source content of the page itself
     * @return
     */
    public static AbstractCode createCode(ContentType type, LinkURL linkURL, PageContent content) {
        if (type.isHtml()) {
            return new HTMLCode(content, linkURL);
        }
        if (type.isCss()) {
            return new CSSCode(content, linkURL);
        }
        //else
        return new OtherCode(type, content, linkURL);
    }

}
