//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.19 at 10:40:20 PM CEST 
//
package org.w3._2005._07.css_validator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Result complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Result"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="errors" type="{http://www.w3.org/2005/07/css-validator}ValidationErrors"/&gt;
 *         &lt;element name="warnings" type="{http://www.w3.org/2005/07/css-validator}ValidationWarnings"/&gt;
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
@XmlType(name = "Result", propOrder = {
    "errors",
    "warnings"
})
public class Result {

    @XmlElement(required = true)
    protected ValidationErrors errors;
    @XmlElement(required = true)
    protected ValidationWarnings warnings;

    /**
     * Gets the value of the errors property.
     *
     * @return a {@link org.w3._2005._07.css_validator.ValidationErrors} object.
     */
    public ValidationErrors getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     *
     * @param value allowed object is
     * {@link org.w3._2005._07.css_validator.ValidationErrors}
     */
    public void setErrors(ValidationErrors value) {
        this.errors = value;
    }

    /**
     * Gets the value of the warnings property.
     *
     * @return a {@link org.w3._2005._07.css_validator.ValidationWarnings}
     * object.
     */
    public ValidationWarnings getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     *
     * @param value allowed object is
     * {@link org.w3._2005._07.css_validator.ValidationWarnings}
     */
    public void setWarnings(ValidationWarnings value) {
        this.warnings = value;
    }

}
