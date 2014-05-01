package org.presentation.checker.singlepagecontroller;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class SinglePageControllerActivator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        Logger.getLogger(this.getClass().getName()).log(Level.OFF, "starting bundle...");
        // TODO add activation code here
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        Logger.getLogger(this.getClass().getName()).log(Level.OFF, "stopping bundle...");
        // TODO add deactivation code here
    }

}
