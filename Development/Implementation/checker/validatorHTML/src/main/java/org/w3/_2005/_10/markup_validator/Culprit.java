//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.19 at 10:40:13 PM CEST 
//


package org.w3._2005._10.markup_validator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Culprit complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Culprit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="line" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="col" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="explanation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="messageid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Culprit", propOrder = {
    "line",
    "col",
    "source",
    "explanation",
    "messageid",
    "message"
})
@XmlSeeAlso({
    Warning.class,
    Error.class
})
public abstract class Culprit {

    /**
     *
     */
    @XmlElement(required = true)
    protected String line;

    /**
     *
     */
    @XmlElement(required = true)
    protected String col;

    /**
     *
     */
    protected String source;

    /**
     *
     */
    protected String explanation;

    /**
     *
     */
    protected String messageid;

    /**
     *
     */
    protected String message;

    /**
     * Gets the value of the line property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getLine() {
        return line;
    }

    /**
     * Sets the value of the line property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setLine(String value) {
        this.line = value;
    }

    /**
     * Gets the value of the col property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCol() {
        return col;
    }

    /**
     * Sets the value of the col property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setCol(String value) {
        this.col = value;
    }

    /**
     * Gets the value of the source property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the explanation property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * Sets the value of the explanation property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setExplanation(String value) {
        this.explanation = value;
    }

    /**
     * Gets the value of the messageid property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMessageid() {
        return messageid;
    }

    /**
     * Sets the value of the messageid property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setMessageid(String value) {
        this.messageid = value;
    }

    /**
     * Gets the value of the message property.
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     *
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    public void setMessage(String value) {
        this.message = value;
    }

}