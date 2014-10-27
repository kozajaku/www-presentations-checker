//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.19 at 10:40:13 PM CEST 
//
package org.w3._2005._10.markup_validator;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.w3._2005._10.markup_validator package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 *
 * @author radio.koza
 * @version $Id: $Id
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Markupvalidationresponse_QNAME = new QName("http://www.w3.org/2005/10/markup-validator", "markupvalidationresponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of
     * schema derived classes for package: org.w3._2005._10.markup_validator
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of
     * {@link org.w3._2005._10.markup_validator.MarkupValidationResponse}
     *
     * @return a
     * {@link org.w3._2005._10.markup_validator.MarkupValidationResponse}
     * object.
     */
    public MarkupValidationResponse createMarkupValidationResponse() {
        return new MarkupValidationResponse();
    }

    /**
     * Create an instance of
     * {@link org.w3._2005._10.markup_validator.WarningList}
     *
     * @return a {@link org.w3._2005._10.markup_validator.WarningList} object.
     */
    public WarningList createWarningList() {
        return new WarningList();
    }

    /**
     * Create an instance of {@link org.w3._2005._10.markup_validator.Warning}
     *
     * @return a {@link org.w3._2005._10.markup_validator.Warning} object.
     */
    public Warning createWarning() {
        return new Warning();
    }

    /**
     * Create an instance of {@link org.w3._2005._10.markup_validator.ErrorList}
     *
     * @return a {@link org.w3._2005._10.markup_validator.ErrorList} object.
     */
    public ErrorList createErrorList() {
        return new ErrorList();
    }

    /**
     * Create an instance of
     * {@link org.w3._2005._10.markup_validator.ValidationErrors}
     *
     * @return a {@link org.w3._2005._10.markup_validator.ValidationErrors}
     * object.
     */
    public ValidationErrors createValidationErrors() {
        return new ValidationErrors();
    }

    /**
     * Create an instance of
     * {@link org.w3._2005._10.markup_validator.ValidationWarnings}
     *
     * @return a {@link org.w3._2005._10.markup_validator.ValidationWarnings}
     * object.
     */
    public ValidationWarnings createValidationWarnings() {
        return new ValidationWarnings();
    }

    /**
     * Create an instance of {@link org.w3._2005._10.markup_validator.Error}
     *
     * @return a {@link org.w3._2005._10.markup_validator.Error} object.
     */
    public Error createError() {
        return new Error();
    }

    /**
     * Create an instance of {@link org.w3._2005._10.markup_validator.Debug}
     *
     * @return a {@link org.w3._2005._10.markup_validator.Debug} object.
     */
    public Debug createDebug() {
        return new Debug();
    }

    /**
     * Create an instance of
     * {@link javax.xml.bind.JAXBElement}{@code <}{@link org.w3._2005._10.markup_validator.MarkupValidationResponse}{@code >}}
     *
     * @param value a
     * {@link org.w3._2005._10.markup_validator.MarkupValidationResponse}
     * object.
     * @return a {@link javax.xml.bind.JAXBElement} object.
     */
    @XmlElementDecl(namespace = "http://www.w3.org/2005/10/markup-validator", name = "markupvalidationresponse")
    public JAXBElement<MarkupValidationResponse> createMarkupvalidationresponse(MarkupValidationResponse value) {
        return new JAXBElement<MarkupValidationResponse>(_Markupvalidationresponse_QNAME, MarkupValidationResponse.class, null, value);
    }

}