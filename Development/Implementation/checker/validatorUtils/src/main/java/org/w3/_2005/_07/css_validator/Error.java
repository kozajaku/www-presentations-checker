//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.19 at 10:40:20 PM CEST 
//
package org.w3._2005._07.css_validator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Error complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Error"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.w3.org/2005/07/css-validator}Culprit"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="errortype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="errorsubtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="context" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="skippedstring" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Error", propOrder = {
    "errortype",
    "errorsubtype",
    "context",
    "skippedstring"
})
public class Error
        extends Culprit {

    protected String errortype;
    protected String errorsubtype;
    protected String context;
    protected String skippedstring;

    /**
     * Gets the value of the errortype property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getErrortype() {
        return errortype;
    }

    /**
     * Sets the value of the errortype property.
     *
     * @param value allowed object is {@link java.lang.String}
     */
    public void setErrortype(String value) {
        this.errortype = value;
    }

    /**
     * Gets the value of the errorsubtype property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getErrorsubtype() {
        return errorsubtype;
    }

    /**
     * Sets the value of the errorsubtype property.
     *
     * @param value allowed object is {@link java.lang.String}
     */
    public void setErrorsubtype(String value) {
        this.errorsubtype = value;
    }

    /**
     * Gets the value of the context property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     *
     * @param value allowed object is {@link java.lang.String}
     */
    public void setContext(String value) {
        this.context = value;
    }

    /**
     * Gets the value of the skippedstring property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSkippedstring() {
        return skippedstring;
    }

    /**
     * Sets the value of the skippedstring property.
     *
     * @param value allowed object is {@link java.lang.String}
     */
    public void setSkippedstring(String value) {
        this.skippedstring = value;
    }

}
