/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.verifybackurl.service.filter;

import fr.paris.lutece.plugins.verifybackurl.service.ProcessConstraintsService;
import fr.paris.lutece.plugins.verifybackurl.utils.VerifiyBackUrlUtils;
import fr.paris.lutece.plugins.verifybackurl.utils.VerifyBackUrlConstants;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * BackUrl filter
 */
public class BackUrlFilter implements Filter
{

    /**
     * {@inheritDoc}
     */
    @Override
    public void init( FilterConfig config ) throws ServletException
    {
    }
    

    /**
     * @param chain
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String url = request.getParameter( VerifyBackUrlConstants.PARAMETER_BACK_URL );
        
        boolean checkConstraints = false;
        if ( url!= null)
        {
            checkConstraints = ProcessConstraintsService.checkConstraints( url );
        }
        if ( url!= null && checkConstraints )
        {
            VerifiyBackUrlUtils.storeBackUrlInSession( httpRequest, url );
        }
        else if ( url!= null && !checkConstraints )
        {
            //this is for the security : if a service provide a back url,
            //but this url breaks constaints, then drop the service in session
            VerifiyBackUrlUtils.dropBackUrlInSession( httpRequest );
        }
        chain.doFilter( request, response );
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy( )
    {
       
    }
    
}
