/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.graphgenerator.impl;

import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.presentation.graphgenerator.GraphGenerator;
import org.presentation.graphgenerator.GraphResult;
import org.presentation.model.graph.TraversalGraph;

/**
 * This class can convert graph into graphviz source code (no opossum can do
 * that!)
 *
 * @author petrof
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class FullGraphGVSGenerator extends GraphGenerator {

    @Inject
    private GraphvizUtils graphvizUtils;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    /**
     * {@inheritDoc}
     */
    @Override
    public GraphResult generateGraphResult(TraversalGraph graph) {
        String graphvizSource = graphvizUtils.generateSource(graph, false);
        if (graphvizSource == null) {
            LOG.severe("Generation of graphviz source failed!");
            return null;//no GraphResult will be saved in database
        }

        return new FullGraphGVSource(graphvizSource);
    }

}
