package org.presentation.parser;

import org.presentation.model.ContentType;
import org.presentation.model.LinkURL;
import org.presentation.model.PageContent;

/**
 * @author radio.koza
 * @version 1.0
 */
public abstract class AbstractCode {

    protected LinkURL pageLink;
    
    public LinkURL getLink(){
        return pageLink;
    }
    
    public abstract CodeType getType();
    
    public static AbstractCode createCode(ContentType type, LinkURL linkURL, PageContent content){
        if (type.isHtml()){
            return new HTMLCode(content, linkURL);
        }
        if (type.isCss()){
            return new CSSCode(content, linkURL);
        }
        //else
        return new OtherCode(type, content, linkURL);
    }

}
