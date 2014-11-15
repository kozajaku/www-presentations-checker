package org.presentation.graphgenerator.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.model.graph.Edge;
import org.presentation.model.graph.Node;
import org.presentation.model.graph.TraversalGraph;

/**
 * This class can work with Graphviz, tool for generating graphs.
 *
 * @author radio.koza, Adam Kugler
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class GraphvizUtils {

    public enum GraphvizType {

        DOT,
        NEATO,
        TWOPI,
        CIRCO;

        public String execString() {
            switch (this) {
                case DOT:
                    return "dot -Tsvg";
                case NEATO:
                    return "neato -Tsvg";
                case TWOPI:
                    return "twopi -Tsvg";
                case CIRCO:
                    return "circo -Tsvg";
            }
            //else fail
            assert false : "Missing enum value";
            return null;
        }
    }

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;
    @Resource
    private ManagedExecutorService mes;

    //constants
    protected static final String VALID_COLOR = "\"green\"";
    protected static final String INVALID_COLOR = "\"red\"";
    protected static final String LINK_COLOR = "\"black\"";
    protected static final String IMG_COLOR = "\"blue\"";
    protected static final String CSS_COLOR = "\"yellow\"";
    protected static final String FROM_CSS_COLOR = "\"orange\"";
    protected static final String IMPORT_COLOR = "\"deeppink\"";
    protected static final String SCRIPT_COLOR = "\"purple\"";

    protected Map<Node, Integer> nodeNumbers;
    protected int nodeCounter;

    /**
     * Generates source code for Graphviz tool. {@link TraversalGraph} is
     * converted into nodes and edges. Does nothing and returns null if graph is
     * null.
     *
     * @param graph {@link TraversalGraph} to generate source code from.
     * @return Source code for Graphviz or null if graph is null.
     */
    public String generateSource(TraversalGraph graph) {
        if (graph == null) {
            return null;
        }
        nodeNumbers = new HashMap<>();
        nodeCounter = 0;
        StringBuilder codeGraph = new StringBuilder();
        StringBuilder nodes = new StringBuilder();
        StringBuilder edges = new StringBuilder();
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
        codeGraph.append("graph [overlap=false];\n"); //dont use splines=true with overlap=false
        codeGraph.append("root=1;\n");
        codeGraph.append(nodes);
        codeGraph.append(edges);
        codeGraph.append("}\n");
        return codeGraph.toString();
    }

    /**
     * Writes informations about node in Graphviz syntax.
     *
     * @param nodes {@link StringBuilder} where to append node informations.
     * Schould not be null.
     * @param node {@link Node} to write. Schould not be null.
     * @param nodeId Node unique identifier. Schould not be negative. Use
     * {@link #getNodeId(org.presentation.model.graph.Node)} before.
     */
    private void writeNode(StringBuilder nodes, Node node, int nodeId) {
        //graphviz escape
        String stringURL = node.getUrl().toString().replace("\"", "\\\""); //need \" in code
        nodes.append(nodeId)
                .append(" [fixedsize=true, shape=circle, style = \"filled\"")
                .append(", URL=\"").append(stringURL).append("\", tooltip=\"").append(stringURL).append('"')
                .append(", target=\"_blank\"");
        if (node.isValid()) {
            nodes.append(", fillcolor = ").append(VALID_COLOR);
        } else {
            nodes.append(", fillcolor = ").append(INVALID_COLOR);
        }
        nodes.append(", width=").append(String.format(Locale.US, "%.2f", countNodeSize(node)));
        nodes.append("]\n");//end
    }//width, color = \"green\", URL = \"http://www.seznam.cz/\", tooltip = \"http://www.seznam.cz/\", fillcolor = \"green\"]

    /**
     * Writes informations about edge in Graphviz syntax.
     *
     * @param edges {@link StringBuilder} where to append edge informations.
     * Schould not be null.
     * @param edge {@link Edge} to write. Schould not be null.
     * @param NodeId Unique identifier of node from where goes the edge. Schould
     * not be negative. Use
     * {@link #getNodeId(org.presentation.model.graph.Node)} before.
     */
    private void writeEdge(StringBuilder edges, Edge edge, int NodeId) {
        Node targetNode = edge.getNode();
        //graphviz escape
        String stringName = edge.getName().replace("\"", "\\\""); //need \" in code
        edges.append(NodeId).append(" -> ").append(getNodeId(targetNode));
        edges.append("[tooltip=\"").append(stringName).append('"');
        edges.append(", penwidth=2"); //edge width 
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

    /**
     * Gets unique identifier of node. If node is unknown it creates new greater
     * identifier else it returns previously created identifier.
     *
     * @param node {@link Node} that identifier is returned. Schould not be
     * null.
     * @return Unique identifier of node based on {@link #nodeCounter}.
     */
    private int getNodeId(Node node) {
        Integer nodeNumber = nodeNumbers.get(node);
        if (nodeNumber == null) {
            nodeCounter++;
            nodeNumber = nodeCounter;
            nodeNumbers.put(node, nodeCounter);
        }
        return nodeNumber;
    }

    /**
     * Counts size of node based on node input degree. Node size is growing
     * logarithmically.
     *
     * @param node {@link Node} that size is counted. Schould not be null.
     * @return Size of node.
     */
    private double countNodeSize(Node node) {
        return (Math.log(node.getInputDegree() + 1) + 1) / 2;
    }

    public String executeGraphviz(GraphvizType execType, String gvSource) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(execType.execString());
        } catch (IOException ex) {
            LOG.severe("Exception during calling graphviz process - is graphviz bin folder in your PATH?");
            return null;
        }
        //completely print source to newly created process
        try (PrintStream output = new PrintStream(p.getOutputStream())) {
            output.println(gvSource);
        }
        StringBuilder res = new StringBuilder();
        String tmp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        //create error stream consuming thread - stream must be consumed or waitFor will hang
        mes.submit(new ErrorStreamConsumer(p.getErrorStream()));
        boolean writeFlag = false;//unnecessary information could be in the beginning of output
        try {
            while ((tmp = reader.readLine()) != null) {
                if (tmp.matches("^<svg.+")) {//start reading from <svg tag
                    writeFlag = true;
                }
                if (writeFlag) {
                    res.append(tmp);
                }
            }
            if (p.waitFor() != 0) {
                //log wrong error output
                LOG.log(Level.SEVERE, "Grapviz process returned {0} error code", p.waitFor());
                return null;
            }
        } catch (InterruptedException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return null;
        }
        return res.toString();
    }

    private class ErrorStreamConsumer extends Thread {

        private final BufferedReader errorStream;

        public ErrorStreamConsumer(InputStream errStream) {
            this.errorStream = new BufferedReader(new InputStreamReader(errStream));
        }

        @Override
        public void run() {
            String line;
            try {
                while ((line = errorStream.readLine()) != null) {
                    LOG.log(Level.SEVERE, "graphviz error: {0}", line);
                }
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Exception while reading graphviz error stream", ex);
            } finally {
                try {
                    errorStream.close();
                } catch (IOException ex) {
                    //not necessary to inform
                }
            }
        }

    }

}
