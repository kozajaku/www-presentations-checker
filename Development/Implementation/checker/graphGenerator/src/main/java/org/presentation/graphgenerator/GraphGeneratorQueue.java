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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import org.presentation.model.graph.TraversalGraph;

/**
 * EJB Singleton class which serves as queue for creating graphical
 * representation of {@link org.presentation.model.graph.TraversalGraph}.
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GraphGeneratorQueue {

    @Inject
    @Any
    private Instance<GraphGenerator> graphGenerators;

    @Inject
    @SuppressWarnings("NonConstantLogger")
    private Logger LOG;

    private final Lock queueLock = new ReentrantLock(true);

    /**
     * Asynchronous method that is called with
     * {@link org.presentation.model.graph.TraversalGraph} argument to render
     * all possible implementations of
     * {@link org.presentation.graphgenerator.GraphResult} throught
     * {@link org.presentation.graphgenerator.GraphGenerator} implemented
     * classes. Note that graphs are generated sequentially.
     *
     * @param graph A {@link org.presentation.model.graph.TraversalGraph} object
     * which should be transformed to possible
     * {@link org.presentation.graphgenerator.GraphResult} implementations.
     * @return A {@link java.util.concurrent.Future} object which serves as
     * holder for information about asynchronous method execution. When
     * rendering is finished, list of
     * {@link org.presentation.graphgenerator.GraphResult} is added to the
     * {@link java.util.concurrent.Future} object and can be retreived by method
     * {@link java.util.concurrent.Future#get()}.
     * @see Future
     * @see Future#get()
     * @see Future
     * @see Future#get()
     * @see Future
     * @see Future#get()
     * @see Future
     * @see Future#get()
     * @see Future
     * @see Future#get()
     * @see Future
     * @see Future#get()
     * @see Future
     * @see Future#get()
     * @see Future
     * @see Future#get()
     */
    @Asynchronous
    public Future<List<GraphResult>> drawGraph(TraversalGraph graph) {
        LOG.log(Level.INFO, "Adding new traversalGraph to graph drawing queue");
        List<GraphResult> results = new ArrayList<>();
        queueLock.lock();
        try {
            GraphResult tmp;
            for (GraphGenerator i : graphGenerators) {
                tmp = i.generateGraphResult(graph);
                if (tmp != null) {
                    results.add(tmp);
                    LOG.log(Level.INFO, "{0} created", tmp.getResultId());
                }
            }
        } finally {
            queueLock.unlock();
        }
        return new AsyncResult<>(results);
    }
}
