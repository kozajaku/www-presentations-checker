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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author radio.koza
 */
public class ShowResultsServlet extends WebServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("<html><head><title>Test result servlet</title></head><body><h1>No parameters</h1></body></html>");
        
    }

    private String generateSvg(String source) throws IOException, InterruptedException{
        Process p = Runtime.getRuntime().exec("dot -Tsvg");
        try (PrintStream output = new PrintStream(p.getOutputStream())) {
            output.println(source);
        }
        StringBuilder res = new StringBuilder();
        String tmp;
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        boolean writeFlag = false;
        while ((tmp = reader.readLine()) != null){
            if (tmp.matches("^<svg.+")){
                writeFlag = true;
            }
            if (writeFlag){
                res.append(tmp);
            }
        }
        if (p.waitFor() != 0){
            res.append("Error: return code ").append(p.exitValue());
            res.append("\n");
            reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((tmp = reader.readLine()) != null){
                res.append(reader.readLine());
                
            }  
        }
        return res.toString();
        
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        String graphvizSourceCode = req.getParameter("zdrojak");
        resp.getWriter().println("<html><head><title>Test result servlet</title></head><body>");
        resp.getWriter().println("<h1>Graphviz source code</h1>\n<code>");
        resp.getWriter().println(graphvizSourceCode.replaceAll("\n", "<br />\n").replaceAll("->", "-&gt;"));
        resp.getWriter().println("</code>\n<h1>Generated svg</h1>");
        try {        
            resp.getWriter().println(generateSvg(graphvizSourceCode));
        } catch (IOException | InterruptedException ex) {
            resp.getWriter().println("Graphviz is unavailable!<br />");
            resp.getWriter().println("Exception: " + ex.toString());
        }
        resp.getWriter().println("</body></html>");
        
    }
    
    
}
