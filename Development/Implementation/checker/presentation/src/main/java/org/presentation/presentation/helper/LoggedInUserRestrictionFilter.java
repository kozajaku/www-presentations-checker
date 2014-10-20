/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.presentation.presentation.helper;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This helper prevents logged users from accessing pages for not logged users
 *
 * @author petrof
 * @version $Id: $Id
 */
@WebFilter(urlPatterns = {"/public/login.xhtml", "/publicc/signUp.xhtml"})
public class LoggedInUserRestrictionFilter implements Filter {

    /** {@inheritDoc} */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
	
    }

    /** {@inheritDoc} */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse resp = (HttpServletResponse) response;
	HttpSession sess = req.getSession(false);
	
	if(sess != null){
	    if(sess.getAttribute("user") != null) {
		resp.sendRedirect("../protected/user/");
	    }
	}
	
	chain.doFilter(request, response);
    }

    /** {@inheritDoc} */
    @Override
    public void destroy() {
	
    }
  
}
