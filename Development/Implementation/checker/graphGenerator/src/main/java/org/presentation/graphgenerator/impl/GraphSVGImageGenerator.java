package org.presentation.graphgenerator.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.graphgenerator.GraphGenerator;
import org.presentation.graphgenerator.GraphResult;
import org.presentation.model.graph.TraversalGraph;

/**
 * This class can convert graph into SVG image.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class GraphSVGImageGenerator extends GraphGenerator {

    @Inject
    private GraphvizUtils graphvizUtils;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    @Override
    public GraphResult generateGraphResult(TraversalGraph traversalGraph) {
        String graphvizSource = graphvizUtils.generateSource(traversalGraph);
        if (graphvizSource == null) {
            LOG.severe("Generation of graphviz source failed!");
            return null;//no GraphResult will be saved in database
        }
        String svgSource = graphvizUtils.executeGraphviz(GraphvizUtils.GraphvizType.TWOPI, graphvizSource);
        //=============debug================
        File debug = new File("wrongGraphvizSource.txt");
        if (debug.exists()){
            debug.delete();
        }
        try (PrintStream ps = new PrintStream(debug)) {
            ps.println(graphvizSource);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphSVGImageGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        //=============/debug===============
        if (svgSource == null) {
            LOG.severe("Generation of SVG source from graphviz source failed!");
            return null;//no GraphResult will be saved in database
        }
        return new SVGImage(svgSource);
    }
}
