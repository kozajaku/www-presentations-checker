package org.presentation.utils;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Logging producer for injectable logger
 *
 * @author radio.koza
 * @version 1.0-SNAPSHOT
 */
public class LoggerProducer {

    /**
     * <p>
     * produceLogger.</p>
     *
     * @param injectionPoint a
     * {@link javax.enterprise.inject.spi.InjectionPoint} object.
     * @return logger
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
