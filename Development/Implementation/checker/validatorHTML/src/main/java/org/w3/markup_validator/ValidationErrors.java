//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.19 at 09:39:27 PM CEST 
//


package org.w3.markup_validator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ValidationErrors complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidationErrors">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorcount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="errorlist" type="{http://www.w3.org/markup-validator}ErrorList"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidationErrors", propOrder = {
    "errorcount",
    "errorlist"
})
public class ValidationErrors {

    protected int errorcount;
    @XmlElement(required = true)
    protected ErrorList errorlist;

    /**
     * Gets the value of the errorcount property.
     * 
     */
    public int getErrorcount() {
        return errorcount;
    }

    /**
     * Sets the value of the errorcount property.
     * 
     */
    public void setErrorcount(int value) {
        this.errorcount = value;
    }

    /**
     * Gets the value of the errorlist property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorList }
     *     
     */
    public ErrorList getErrorlist() {
        return errorlist;
    }

    /**
     * Sets the value of the errorlist property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorList }
     *     
     */
    public void setErrorlist(ErrorList value) {
        this.errorlist = value;
    }

}
