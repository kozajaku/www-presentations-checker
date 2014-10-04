package org.presentation.webcrawler.impl;

import org.presentation.model.ContentType;
import org.presentation.model.PageContent;

/**
 *
 * @author Jinřich Máca
 */
public class ReceiverResponse {

    private ContentType contentType;
    private PageContent sourceCode;
    private Integer stateCode;

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public PageContent getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(PageContent sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

}
