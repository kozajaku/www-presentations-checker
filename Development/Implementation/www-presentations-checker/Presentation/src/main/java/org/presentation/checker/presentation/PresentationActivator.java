package org.presentation.checker.presentation;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class PresentationActivator implements BundleActivator {

    private WebServer server; //temporary variable - probably will not be used in final release
    static BundleContext context;
    
    @Override
    public void start(BundleContext context) throws Exception {
        Logger.getLogger(this.getClass().getName()).log(Level.OFF, "starting bundle...");
        PresentationActivator.context = context;
        server = new WebServer(); // instanciation of web server
        server.startServer();
        // TODO add activation code here
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        Logger.getLogger(this.getClass().getName()).log(Level.OFF, "stopping bundle...");
        server.stopServer();
        // TODO add deactivation code here
    }

}
