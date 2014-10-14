/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator;

/**
 * This class represents traversal graph tree by using HTML tags as nested unordered lists.
 * @author Adam Kugler
 */
public class TraversalGraphTree extends GraphResult{
    private final String tagTree;

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
