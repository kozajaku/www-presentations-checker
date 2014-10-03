/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation;

import javax.ejb.EJB;
import org.presentation.persistence.business.PersistenceFacadeImpl;

/**
 *
 * @author petrof
 */
public abstract class CommonBean {
    
    @EJB
    PersistenceFacadeImpl persistance;
    
    /**
     * Creates a new instance of CommonBean
     */
    public CommonBean() {
    }
    
}
