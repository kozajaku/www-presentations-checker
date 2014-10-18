/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import org.presentation.graphgenerator.GraphResult;

/**
 *
 * @author Adam Kugler
 */
public class SVGImage extends GraphResult{
    String image;

    public SVGImage(String image) {
        this.image = image;
    }

    @Override
    public String getResultAsCode() {
        return image;
    }

    @Override
    public String getResultId() {
        return "SVG graph";
    }
    
}
