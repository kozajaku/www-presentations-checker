package org.presentation.model.logging;

import java.util.List;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Jindřich Máca
 */
@Dependent
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
