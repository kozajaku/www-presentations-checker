package org.presentation.model.logging;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;

/**
 * Class that represents united container for all
 * {@link org.presentation.model.logging.MessageLogger}. If control wants to
 * produce {@link org.presentation.model.logging.Message} for user output, it
 * must first register its {@link org.presentation.model.logging.MessageLogger}
 * under its specific resource name to given
 * {@link org.presentation.model.logging.MessageLoggerContainer}. This
 * {@link org.presentation.model.logging.MessageLoggerContainer} can then
 * generates user {@link org.presentation.model.logging.MsgReport} for all its
 * {@link org.presentation.model.logging.MessageLogger}.
 *
 * @author Jindřich Máca
 * @version $Id: $Id
 */
@Dependent
public class MessageLoggerContainer {

    //Array list of message loggers
    private final List<MessageLogger> loggers = new ArrayList<>();

    /**
     * Register new instance of
     * {@link org.presentation.model.logging.MessageLogger} to this
     * {@link org.presentation.model.logging.MessageLoggerContainer} under given
     * resource name.
     *
     * @param resource {@link java.lang.String} resource name of the new
     * {@link org.presentation.model.logging.MessageLogger}
     * @return new instance of the
     * {@link org.presentation.model.logging.MessageLogger} registred to this
     * {@link org.presentation.model.logging.MessageLoggerContainer}
     */
    public MessageLogger createLogger(String resource) {
        MessageLogger logger = new MessageLogger(resource);
        this.loggers.add(logger);
        return logger;
    }

    /**
     * Returns generated {@link org.presentation.model.logging.MsgReport} from
     * all registred {@link org.presentation.model.logging.MessageLogger} of
     * this {@link org.presentation.model.logging.MessageLoggerContainer}.
     *
     * @return {@link org.presentation.model.logging.MsgReport} generated from
     * all registred {@link org.presentation.model.logging.MessageLogger}
     */
    public MsgReport generateMsgReport() {
        MsgReport report = new MsgReport();
        for (MessageLogger logger : loggers) {
            logger.pushToMsgReport(report);
        }
        return report;
    }
}
