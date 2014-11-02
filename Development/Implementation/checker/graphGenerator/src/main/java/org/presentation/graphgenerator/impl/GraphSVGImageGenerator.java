/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.presentation.graphgenerator.GraphGenerator;
import org.presentation.graphgenerator.GraphResult;
import org.presentation.model.graph.Edge;
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
    //constants
    static final String VALID_COLOR = "\"green\"";
    static final String INVALID_COLOR = "\"red\"";
    static final String LINK_COLOR = "\"black\"";
    static final String IMG_COLOR = "\"blue\"";
    static final String CSS_COLOR = "\"yellow\"";
    static final String FROM_CSS_COLOR = "\"orange\"";
    static final String IMPORT_COLOR = "\"deeppink\"";
    static final String SCRIPT_COLOR = "\"purple\"";
    
    Map<Node, Integer> nodeNumbers;
    int nodeCounter;

    /**
     * {@inheritDoc}
     */
    @Override
    public GraphResult generateGraphResult(TraversalGraph graph) {
        nodeNumbers = new HashMap<>();
        nodeCounter = 0;
        StringBuilder codeGraph = new StringBuilder();
        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();
        writeSubgraph(nodes, edges, graph.getRoot());
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(graph.getRoot());
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            int nodeId = getNodeId(node);
            writeNode(nodes, node, nodeId);
            List<Edge> nodeEdges = node.getOrientedEdges();
            for (Edge nodeEdge : nodeEdges) {
                writeEdge(edges, nodeEdge, nodeId);
                if (nodeEdge.isTreeEdge()) {
                    nodeQueue.add(nodeEdge.getNode());
                }
            }
        }
        codeGraph.append("digraph \"Traversal Graph\"{\n");
        codeGraph.append(nodes);
        codeGraph.append(edges);
        codeGraph.append("}\n");
        return new SVGImage(codeGraph.toString());
    }

    private void writeSubgraph(StringBuilder nodes, StringBuilder edges, Node node) {
        //writeNode(nodes, node);
    }

    private void writeNode(StringBuilder nodes, Node node, int nodeId) {
        nodes.append(nodeId)
                .append(" [fixedsize=true, shape=circle, style = \"filled\", ")
                .append("URL=\"").append(node.getUrl()).append("\", tooltip=\"").append(node.getUrl()).append('"');
        if (node.isValid()) {
            nodes.append(", fillcolor = ").append(VALID_COLOR);
        } else {
            nodes.append(", fillcolor = ").append(INVALID_COLOR);
        }
        nodes.append(", width=").append(countNodeSize(node));
        nodes.append("]\n");//end
    }//width, color = \"green\", URL = \"http://www.seznam.cz/\", tooltip = \"http://www.seznam.cz/\", fillcolor = \"green\"]

    private void writeEdge(StringBuilder edges, Edge edge, int NodeId) {
        Node targetNode = edge.getNode();
        edges.append(NodeId).append(" -> ").append(getNodeId(targetNode));
        edges.append("[tooltip=\"").append(edge.getName()).append('"');
        edges.append(", color=");
        if (!targetNode.isValid()) {
            edges.append(INVALID_COLOR);
            edges.append("]\n");//end
            return;
        }
        switch (edge.getSourceType()) {
            case A_HREF: {
                edges.append(LINK_COLOR);
                break;
            }
            case IMG_SRC: {
                edges.append(IMG_COLOR);
                break;
            }
            case INSIDE_CSS: {
                edges.append(FROM_CSS_COLOR);
                break;
            }
            case LINK_HREF: {
                edges.append(IMPORT_COLOR);
                break;
            }
            case LINK_HREF_CSS: {
                edges.append(CSS_COLOR);
                break;
            }
            case SCRIPT_SRC: {
                edges.append(SCRIPT_COLOR);
                break;
            }
        }
        edges.append("]\n");//end
    }

    private int getNodeId(Node node) {
        Integer nodeNumber = nodeNumbers.get(node);
        if (nodeNumber == null) {
            nodeCounter++;
            nodeNumber = nodeCounter;
            nodeNumbers.put(node, nodeCounter);
        }
        return nodeNumber;
    }

    private double countNodeSize(Node node) {
        return Math.log(node.getInputDegree()+1);
    }
}
