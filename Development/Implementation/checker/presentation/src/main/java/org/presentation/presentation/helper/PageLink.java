/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

/**
 *
 * @author petrof
 */
public class PageLink {
    String view;
    String caption;

    public PageLink(String view, String caption) {
	this.view = view;
	this.caption = (caption == null ? "" : caption);
    }

    public String getView() {
	return view;
    }

    public String getCaption() {
	return caption;
    }        
}
