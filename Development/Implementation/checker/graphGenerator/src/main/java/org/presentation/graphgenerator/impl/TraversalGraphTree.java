/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import org.presentation.graphgenerator.GraphResult;

/**
 * This class represents traversal graph tree by using HTML tags as nested
 * unordered lists.
 *
 * @author Adam Kugler
 */
public class TraversalGraphTree extends GraphResult {

    private final String tagTree;

    /**
     *
     * @param tagTree Tag tree created by graph text generator
     */
    public TraversalGraphTree(String tagTree) {
        this.tagTree = tagTree;
    }

    @Override
    public String getResultAsCode() {
        return tagTree;
    }

    @Override
    public String getResultId() {
        return "Traversal graph tag tree";
    }

}
