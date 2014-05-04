/*
 * Copyright (c) 2014, radio.koza
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.presentation.checker.presentation;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 *
 * @author radio.koza
 */
public class WebServer extends Server{
    public static int PORT = 8080;
    private static final Logger LOG = Logger.getLogger(WebServer.class.getName());
    
    
    public WebServer(){
        super(PORT);//call constructor of the super class with specified port number                         
        ContextHandler contextFileServer = new ContextHandler("/files");//URI beggining with /files
        contextFileServer.setHandler(new FileServer());
        ServletContextHandler servlets = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servlets.setContextPath("/");
        servlets.addServlet(new ServletHolder(new BasicCheckingServlet()), "/*");
        //add additional servlets here
        HandlerList handlers = new HandlerList();
        handlers.addHandler(contextFileServer);
        handlers.addHandler(servlets);
        this.setHandler(handlers);
    }
    
    public void startServer() throws Exception {
        this.start();
        LOG.log(Level.OFF, "Web server started on port: {0}", PORT);
    }
    
    public void stopServer() throws Exception{
        this.stop();
        LOG.log(Level.OFF, "Web server stopped");
    }
    
}
