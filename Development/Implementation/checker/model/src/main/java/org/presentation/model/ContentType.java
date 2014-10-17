package org.presentation.model;

/**
 *
 * @author Jindřich Máca
 */
public class ContentType {

    private final String contentType;

    public ContentType(String contentType){
        this.contentType = contentType;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public boolean isCss(){
        switch (contentType){
            case "text/css": return true;
        }
        return false;
    }
    
    public boolean isHtml(){
        switch (contentType){
            case "text/html": return true;
        }
        return false;
    }

}
