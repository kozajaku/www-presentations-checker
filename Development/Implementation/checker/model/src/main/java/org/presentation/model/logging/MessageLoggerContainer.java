package org.presentation.model.logging;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;

/**
 * Represents container, which associates all message loggers.
 *
 * @author Jindřich Máca
 * @version 1.0-SNAPSHOT
 */
@Dependent
public class MessageLoggerContainer {

    /**
     * Array list of message loggers.
     */
    private final List<MessageLogger> loggers = new ArrayList<>();

    /**
     * Register new message logger to this container.
     *
     * @param resource Name of resource of message logger.
     * @return New initialized MessageLogger instance.
     */
    public MessageLogger createLogger(String resource) {
        MessageLogger logger = new MessageLogger(resource);
        this.loggers.add(logger);
        return logger;
    }

    /**
     * Returns generated message report from all associated message loggers.
     *
     * @return Generated message report.
     */
    public MsgReport generateMsgReport() {
        MsgReport report = new MsgReport();
        for (MessageLogger logger : loggers) {
            logger.pushToMsgReport(report);
        }
        return report;
    }
}
