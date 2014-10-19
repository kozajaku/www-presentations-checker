package org.presentation.webcrawler.impl;

import org.presentation.model.ContentType;
import org.presentation.model.PageContent;

/**
 * Represents page receiver response.
 *
 * @author Jinřich Máca
 */
public class ReceiverResponse {

    /**
     * HTTP head atribute Content-Type, defining type of the file.
     */
    private final ContentType contentType;
    /**
     * Content of the response.
     */
    private final PageContent sourceCode;
    /**
     * HTTP response state code.
     */
    private final Integer stateCode;

    /**
     * Construct receiver response.
     *
     * @param contentType HTTP head atribute Content-Type, defining type of the
     * file
     * @param sourceCode Content of the response
     * @param stateCode HTTP response state code
     */
    public ReceiverResponse(ContentType contentType, PageContent sourceCode, Integer stateCode) {
        this.contentType = contentType;
        this.sourceCode = sourceCode;
        this.stateCode = stateCode;
    }

    /**
     * Returns content type of this response.
     *
     * @return Content type of this response.
     */
    public ContentType getContentType() {
        return contentType;
    }

    /**
     * Returns content of this response.
     *
     * @return Content of this response.
     */
    public PageContent getSourceCode() {
        return sourceCode;
    }

    /**
     * Returns state code of this response.
     *
     * @return State code of this response.
     */
    public Integer getStateCode() {
        return stateCode;
    }

    /**
     * Indicates if page was succesfully received.
     *
     * @return True if state code is OK.
     */
    public boolean isOK() {
        return (200 <= stateCode && stateCode < 300);
    }
}
