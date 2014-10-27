/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import javax.enterprise.context.Dependent;
import org.presentation.graphgenerator.GraphResult;
import org.presentation.model.graph.Edge;
import org.presentation.model.graph.InvalidNode;
import org.presentation.model.graph.Node;
import org.presentation.model.graph.TraversalGraph;
import org.presentation.model.graph.ValidNode;

/**
 * This class generates graph as text.
 *
 * @author Adam Kugler
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class GraphTextGenerator extends GraphGenerator {

    /**
     * {@inheritDoc}
     */
    @Override
    public GraphResult generateGraphResult(TraversalGraph graph) {
        StringBuilder tree = new StringBuilder();
        tree.append("<ul>");
        writeNode(tree, graph.getRoot());
        tree.append("</ul>");
        return new TraversalGraphTree(tree.toString());
    }

    /**
     * This method appends text form of node to currently created graph.
     *
     * @param tree Currently created text tree of graph
     * @param node New node to append
     */
    private void writeNode(StringBuilder tree, Node node) {
        tree.append("<li>");
        if (node.isValid()) {
            writeValidNode(tree, (ValidNode) node);
        } else {
            writeInvalidNode(tree, (InvalidNode) node);
        }
        tree.append("</li>");
    }

    /**
     * This method appends text form of invalid node to currently created graph.
     *
     * @param tree Currently created text tree of graph
     * @param node New invalid node to append
     */
    private void writeInvalidNode(StringBuilder tree, InvalidNode node) {
        tree.append("<span class=\"invalidnode\">")
                .append("<span class=\"error-code\">")
                .append(node.getErrorCode().getCode().toString())
                .append("</span>")
                //                .append(" ")
                .append(node.getUrl().getUrl().replaceAll("&", "&amp;"))
                .append("</span>");
    }

    /**
     * This method appends text form of valid node to currently created graph.
     *
     * @param tree Currently created text tree of graph
     * @param node New valid node to append
     */
    private void writeValidNode(StringBuilder tree, ValidNode node) {
        tree.append("<span class=\"validnode\">").append(node.getUrl().getUrl().replaceAll("&", "&amp;")).append("</span>");
        if (node.getOrientedEdges().size() > 0) {
            tree.append("<ul>");
            for (Edge orientedEdge : node.getOrientedEdges()) {
                if (orientedEdge.isTreeEdge()) {
                    writeNode(tree, orientedEdge.getNode());
                }
            }
            tree.append("</ul>");
        }
    }
}
