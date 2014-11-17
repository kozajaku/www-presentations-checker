package org.presentation.utils;

import java.util.logging.Level;
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

    /**
     * <p>
     * produceHiddenLogger.</p>
     *
     * @param injectionPoiont a
     * {@link javax.enterprise.inject.spi.InjectionPoint} object.
     * @return a {@link java.util.logging.Logger} object.
     */
    @Produces
    @HiddenLogger
    public Logger produceHiddenLogger(InjectionPoint injectionPoiont) {
        Logger logger = Logger.getLogger("HiddenLogger");
        logger.setLevel(Level.OFF);
        return logger;
    }

}
