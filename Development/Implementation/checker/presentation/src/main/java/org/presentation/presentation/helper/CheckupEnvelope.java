/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

import java.io.Serializable;
import java.util.List;
import org.presentation.model.Domain;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.ChosenOption;

/**
 * helper class for display checkup data
 *
 * @author petrof
 * @version $Id: $Id
 */
public class CheckupEnvelope implements Serializable {

    protected Checkup ch;
    protected List<Domain> domainsAllowed;
    protected List<ChosenOption> desiredCheckups;

    /**
     * <p>Constructor for CheckupEnvelope.</p>
     *
     * @param ch a {@link org.presentation.persistence.model.Checkup} object.
     * @param domainsAllowed a {@link java.util.List} object.
     * @param desiredCheckups a {@link java.util.List} object.
     */
    public CheckupEnvelope(Checkup ch, List<Domain> domainsAllowed, List<ChosenOption> desiredCheckups) {
	this.ch = ch;
	this.domainsAllowed = domainsAllowed;
	this.desiredCheckups = desiredCheckups;
    }

    /**
     * <p>Getter for the field <code>ch</code>.</p>
     *
     * @return a {@link org.presentation.persistence.model.Checkup} object.
     */
    public Checkup getCh() {
	return ch;
    }

    /**
     * <p>Getter for the field <code>domainsAllowed</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Domain> getDomainsAllowed() {
	return domainsAllowed;
    }

    /**
     * <p>Getter for the field <code>desiredCheckups</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<ChosenOption> getDesiredCheckups() {
	return desiredCheckups;
    }
    
    
           
    
}
