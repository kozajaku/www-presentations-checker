package org.presentation.model.logging;

import java.util.List;

/**
 *
 * @author Jindřich Máca
 */
public class MessageLoggerContainer {

    private List<MessageLogger> loggers;

    public MessageLogger createLogger(String resource) {
        MessageLogger logger = new MessageLogger(resource);
        this.loggers.add(logger);
        return logger;
    }

    public MsgReport generateMsgReport() {
        MsgReport report = new MsgReport();
        for (MessageLogger logger : loggers) {
            logger.addMsgReport(report);
        }
        return report;
    }
}
