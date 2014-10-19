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
    private ContentType contentType;
    /**
     * Content of the response.
     */
    private PageContent sourceCode;
    /**
     * HTTP response state code.
     */
    private Integer stateCode;

    /**
     * Construct receiver response.
     */
    public ReceiverResponse() {
        this.contentType = new ContentType(null);
        this.sourceCode = new PageContent(null);
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
     * Sets content type of this response.
     *
     * @param contentType Content type of this response.
     */
    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
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
     * Sets content of this response.
     *
     * @param sourceCode Content of this response.
     */
    public void setSourceCode(PageContent sourceCode) {
        this.sourceCode = sourceCode;
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
     * Sets state code of this response.
     *
     * @param stateCode State code of this response.
     */
    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
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
