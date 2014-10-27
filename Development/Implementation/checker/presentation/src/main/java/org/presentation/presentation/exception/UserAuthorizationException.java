/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.exception;

/**
 * This exception should be thrown when the system is unable to authorize /
 * identify user from session
 *
 * @author petrof
 * @version $Id: $Id
 */
public class UserAuthorizationException extends Exception {

    /**
     * <p>
     * Constructor for UserAuthorizationException.</p>
     *
     * @param str a {@link java.lang.String} object.
     */
    public UserAuthorizationException(String str) {
        super(str);
    }

}
