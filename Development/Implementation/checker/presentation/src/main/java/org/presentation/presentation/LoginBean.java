/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.presentation.persistence.business.PersistenceFacadeImpl;

/**
 *
 * @author petrof
 */
@Named(value = "loginBean")
@Dependent
public class LoginBean {

    @Inject
    PersistenceFacadeImpl persistance;
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        
    }
    
}
