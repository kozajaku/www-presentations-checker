package org.presentation.graphgenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.presentation.model.graph.TraversalGraph;

/**
 * <p>
 * GraphGeneratorQueue class.</p>
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class GraphGeneratorQueue {

    @Inject
    @Any
    private Instance<GraphGenerator> graphGenerators;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    private final Lock queueLock = new ReentrantLock(true);

    /**
     * <p>
     * drawGraph.</p>
     *
     * @param graph a {@link org.presentation.model.graph.TraversalGraph}
     * object.
     * @return a {@link java.util.concurrent.Future} object.
     */
    @Asynchronous
    public Future<List<GraphResult>> drawGraph(TraversalGraph graph) {
        LOG.log(Level.INFO, "Adding new traversalGraph to graph drawing queue");
        List<GraphResult> results = new ArrayList<>();
        queueLock.lock();
        try {
            for (GraphGenerator i : graphGenerators) {
                results.add(i.generateGraphResult(graph));
            }
        } finally {
            queueLock.unlock();
        }
        return new AsyncResult<>(results);
    }
}
