/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import org.presentation.graphgenerator.GraphResult;

/**
 * <p>
 * SVGImage class.</p>
 *
 * @author Adam Kugler
 * @version 1.0-SNAPSHOT
 */
public class SVGImage extends GraphResult {

    String image;

    /**
     * <p>
     * Constructor for SVGImage.</p>
     *
     * @param image a {@link java.lang.String} object.
     */
    public SVGImage(String image) {
        this.image = image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultAsCode() {
        return image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResultId() {
        return "SVG graph";
    }

}
