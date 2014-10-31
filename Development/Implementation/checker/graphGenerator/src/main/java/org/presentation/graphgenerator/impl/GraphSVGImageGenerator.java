/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import java.util.Map;
import org.presentation.graphgenerator.GraphGenerator;
import org.presentation.graphgenerator.GraphResult;
import org.presentation.model.graph.Node;
import org.presentation.model.graph.TraversalGraph;

/**
 * This class can convert graph into SVG image.
 *
 * @author Adam Kugler
 * @version 1.0-SNAPSHOT
 */
//@Dependent //TODO - uncomment after implementation
public class GraphSVGImageGenerator extends GraphGenerator {

    Map<Node, Integer> nodeNumbers;
    int nodeCounter;
    /**
     * {@inheritDoc}
     */
    @Override
    public GraphResult generateGraphResult(TraversalGraph graph) {
        nodeCounter = 0;
        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();
        writeNode(nodes, edges, graph.getRoot());
        return null;
    }
    private void writeNode(StringBuilder nodes, StringBuilder edges, Node node) {
        Integer nodeNumber = nodeNumbers.get(node);
        if (nodeNumber == null) {
            nodeCounter++;
            nodeNumbers.put(node, nodeCounter);
        }
    }
}
