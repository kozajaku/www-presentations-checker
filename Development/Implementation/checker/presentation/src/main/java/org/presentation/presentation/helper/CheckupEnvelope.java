/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

import java.util.List;
import org.presentation.model.Domain;
import org.presentation.persistence.model.Checkup;
import org.presentation.persistence.model.ChosenOption;

/**
 *
 * @author petrof
 */
public class CheckupEnvelope {

    protected Checkup ch;
    protected List<Domain> domainsAllowed;
    protected List<ChosenOption> desiredCheckups;

    public CheckupEnvelope(Checkup ch, List<Domain> domainsAllowed, List<ChosenOption> desiredCheckups) {
	this.ch = ch;
	this.domainsAllowed = domainsAllowed;
	this.desiredCheckups = desiredCheckups;
    }

    public Checkup getCh() {
	return ch;
    }

    public List<Domain> getDomainsAllowed() {
	return domainsAllowed;
    }

    public List<ChosenOption> getDesiredCheckups() {
	return desiredCheckups;
    }
    
    
           
    
}
