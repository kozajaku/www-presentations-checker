package org.presentation.parser;

/**
 * Enum represents which implementation type of
 * {@link org.presentation.parser.AbstractCode} is instance of.
 *
 * @author petrof
 * @version 1.0
 */
public enum CodeType {

    /**
     * @see HTMLCode
     */
    HTML_CODE,
    /**
     * @see CSSCode
     */
    CSS_CODE,
    /**
     * @see OtherCode
     */
    OTHER_CODE
}
