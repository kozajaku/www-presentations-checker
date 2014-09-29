package org.presentation.checker.utils;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Logging producer for injectable logger
 *
 * @author radio.koza
 */
public class LoggerProducer {
   /**
    * @param injectionPoint
    * @return logger
    */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}
