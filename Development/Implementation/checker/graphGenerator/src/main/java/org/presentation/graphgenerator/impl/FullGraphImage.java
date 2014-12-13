/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import org.presentation.graphgenerator.GraphResult;

/**
 * This class represents SVG image from {@link FullGraphGenerator}.
 *
 * @author Adam Kugler
 * @version 1.0-SNAPSHOT
 */
public class FullGraphImage extends GraphResult {

    protected String image;

    /**
     * <p>
     * Constructor for SVGImage.</p>
     *
     * @param image a SVG image.
     */
    public FullGraphImage(String image) {
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
        return "Full SVG graph";
    }

}
