package org.presentation.graphgenerator.impl;

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

    @Override
    public GraphResult generateGraphResult(TraversalGraph traversalGraph) {
        String graphvizSource = graphvizUtils.generateSource(traversalGraph);
        if (graphvizSource == null) {
            return null;//no GraphResult will be saved in database
        }
        String svgSource = graphvizUtils.executeGraphviz(GraphvizUtils.GraphvizType.DOT, graphvizSource);
        if (svgSource == null){
            return null;//no GraphResult will be saved in database
        }
        return new SVGImage(svgSource);
    }
}
