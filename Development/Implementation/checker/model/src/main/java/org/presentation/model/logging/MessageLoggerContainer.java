package org.presentation.model.logging;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;

/**
 * Class that represents united container for all {@link MessageLogger}. If
 * control wants to produce {@link Message} for user output, it must first
 * register its {@link MessageLogger} under its specific resource name to given
 * {@link MessageLoggerContainer}. This {@link MessageLoggerContainer} can then
 * generates user {@link MsgReport} for all its {@link MessageLogger}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
@Dependent
public class MessageLoggerContainer {

    //Array list of message loggers
    private final List<MessageLogger> loggers = new ArrayList<>();

    /**
     * Register new instance of {@link MessageLogger} to this
     * {@link MessageLoggerContainer} under given resource name.
     *
     * @param resource {@link String} resource name of the new
     * {@link MessageLogger}
     * @return new instance of the {@link MessageLogger} registred to this
     * {@link MessageLoggerContainer}
     */
    public MessageLogger createLogger(String resource) {
        MessageLogger logger = new MessageLogger(resource);
        this.loggers.add(logger);
        return logger;
    }

    /**
     * Returns generated {@link MsgReport} from all registred
     * {@link MessageLogger} of this {@link MessageLoggerContainer}.
     *
     * @return {@link MsgReport} generated from all registred
     * {@link MessageLogger}
     */
    public MsgReport generateMsgReport() {
        MsgReport report = new MsgReport();
        for (MessageLogger logger : loggers) {
            logger.pushToMsgReport(report);
        }
        return report;
    }
}
