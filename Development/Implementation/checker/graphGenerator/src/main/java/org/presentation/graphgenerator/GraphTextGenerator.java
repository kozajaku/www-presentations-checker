/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator;

import org.presentation.model.graph.Edge;
import org.presentation.model.graph.InvalidNode;
import org.presentation.model.graph.Node;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.graph.ValidNode;

/**
 * This class generates graph as text.
 * @author Adam Kugler
 */
public class GraphTextGenerator extends GraphGenerator{

    @Override
    public GraphResult generateGraphResult(TraversalGraph graph) {
        StringBuilder tree = new StringBuilder();
        tree.append("<ul>");
        writeNode(tree, graph.getRoot());
        tree.append("<\\ul>");
        return new TraversalGraphTree(tree.toString());
    }
    
    private void writeNode(StringBuilder tree, Node node) {
        tree.append("<li>");
        if (node.isValid()) {
            writeValidNode(tree, (ValidNode)node);
        } else {
            writeInvalidNode(tree, (InvalidNode)node);
        }
        tree.append("<\\li>");
    }
    
    private void writeInvalidNode(StringBuilder tree, InvalidNode node) {
        tree.append("X ").append(node.getErrorCode().getCode().toString()).append(" ").append(node.getUrl().getUrl());
    }
    
    private void writeValidNode(StringBuilder tree, ValidNode node) {
        tree.append("V ").append(node.getUrl().getUrl());
        tree.append("<ul>");
        for (Edge orientedEdge : node.getOrientedEdges()) {
            if (orientedEdge.isTreeEdge()) {
                writeNode(tree, orientedEdge.getNode());
            }
        }
        tree.append("<\\ul>");
    }
}
