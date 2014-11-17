/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

/**
 * <p>
 * PageLink class.</p>
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
public class PageLink {

    String view;
    String caption;

    /**
     * <p>
     * Constructor for PageLink.</p>
     *
     * @param view a {@link java.lang.String} object.
     * @param caption a {@link java.lang.String} object.
     */
    public PageLink(String view, String caption) {
        this.view = view;
        this.caption = (caption == null ? "" : caption);
    }

    /**
     * <p>
     * Getter for the field <code>view</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getView() {
        return view;
    }

    /**
     * <p>
     * Getter for the field <code>caption</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCaption() {
        return caption;
    }
}
