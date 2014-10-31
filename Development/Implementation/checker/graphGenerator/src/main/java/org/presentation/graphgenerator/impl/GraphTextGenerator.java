/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import org.presentation.graphgenerator.GraphGenerator;
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
     * This method appends text form of node to currently created graph and is
     * recursively called on all tree children nodes.
     *
     * @param tree Currently created text tree of graph
     * @param node New node to append
     */
    private void writeNode(StringBuilder tree, Node node) {
        tree.append("<li>");
        if (node.isValid()) {
            tree.append("<span class=\"validnode\">");
        } else {
            tree.append("<span class=\"invalidnode\">")
                    .append("<span class=\"error-code\">")
                    .append(node.getResponseCode().getCode().toString())
                    .append("</span>");
        }
        tree.append(node.getUrl().getUrl().replaceAll("&", "&amp;"))
                .append("</span>");
        if (node.getOrientedEdges().size() > 0) {
            tree.append("<ul>");
            for (Edge orientedEdge : node.getOrientedEdges()) {
                if (orientedEdge.isTreeEdge()) {
                    writeNode(tree, orientedEdge.getNode());
                }
            }
            tree.append("</ul>");
        }
        tree.append("</li>");
    }
}
