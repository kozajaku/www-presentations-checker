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

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 *
 * @author radio.koza
 */
public class FileServer extends AbstractHandler{

    private void handleTestImg(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("image/png");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);        
        URL url = PresentationActivator.context.getBundle().getResource("test.png");
        ReadableByteChannel file = Channels.newChannel(url.openStream());
        
//        ReadableByteChannel file = Channels.newChannel(this.getClass().getClassLoader().getResourceAsStream("resources/test.png"));
        WritableByteChannel output = Channels.newChannel(response.getOutputStream());
        buffer.clear();
        while (file.read(buffer) > 0){
            buffer.flip();
            output.write(buffer);
            buffer.clear();
        }        
    }
    
    @Override
    public void handle(String string, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (string.equals("/test.png")){
            handleTestImg(baseRequest, request, response);
            return;
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().println("<h1>This is test page for File Server</h1>");
        response.getWriter().println("Requesting: " + string);
        response.getWriter().flush();
    }
    
}
