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
package fr.paris.lutece.plugins.verifybackurl.service;

import fr.paris.lutece.plugins.verifybackurl.business.AuthorizedUrl;
import fr.paris.lutece.plugins.verifybackurl.utils.VerifiyBackUrlUtils;
import fr.paris.lutece.plugins.verifybackurl.utils.VerifyBackUrlConstants;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class AuthorizedUrlService
{
    private static List<AuthorizedUrl> _listAuthorizedUrl;
    
    private static AuthorizedUrlService _instance;
    
    /**
     * Get instance of AuthorizedUrlService
     * @return instance
     */
    public static AuthorizedUrlService getInstance( )
    {
        if ( _instance == null )
        {
            _instance = new AuthorizedUrlService( );
            
        }
        return _instance;
    }
    
    private AuthorizedUrlService( )
    {
        
    };
    
    /**
     * Return the name of the urlAuthorized
     * @param url
     * @return the name of the UrlAuthorized 
     */
    public String getName( String url )
    {
        _listAuthorizedUrl = new ArrayList<AuthorizedUrl>();
            
        for ( IAuthorizedUrlProvider provider : SpringContextService.getBeansOfType( IAuthorizedUrlProvider.class ) )
        {
            _listAuthorizedUrl.addAll( provider.getAuthorizedUrlsList( ) );
        }
        if ( !_listAuthorizedUrl.isEmpty( ) )
        {
            for ( AuthorizedUrl strAuthUrl : _listAuthorizedUrl )
            {
                if ( VerifiyBackUrlUtils.compareBaseUrl( strAuthUrl.getUrl( ), url ) )
                {
                    return strAuthUrl.getName( );
                }
            }
        } 
        return null;
    }
    
    /**
     * Return the name of the urlAuthorized
     * @param strApplicationCode the application Code
     * @param url
     * @return the name of the UrlAuthorized 
     */
    public String getNameByApplicationCode( String strApplicationCode,String url )
    {
    	  _listAuthorizedUrl = new ArrayList<AuthorizedUrl>();
          
          for ( IAuthorizedUrlProvider provider : SpringContextService.getBeansOfType( IAuthorizedUrlProvider.class ) )
          {
              _listAuthorizedUrl.addAll( provider.getAuthorizedUrlsByApplicationCode(strApplicationCode));
          }
          if ( !_listAuthorizedUrl.isEmpty( ) )
          {
              for ( AuthorizedUrl strAuthUrl : _listAuthorizedUrl )
              {
                  if ( VerifiyBackUrlUtils.compareBaseUrl( strAuthUrl.getUrl( ), url ) )
                  {
                      return strAuthUrl.getName( );
                  }
              }
          } 
          return null;
    }
    
    
    /** 
     * return the service back url if the url is authorized
     * @param request the request
     * @return the service back url if the url is authorized
     */
    public String getServiceBackUrl(HttpServletRequest request)
    {   	
    	return getServiceBackUrl(request, VerifyBackUrlConstants.PARAMETER_BACK_URL);
    }
    
    
    /** 
     * return the service back url if the url is authorized
     * @param request the request
     * @param strBackUrlParameter the parameter name of the service back url
     * @return the service back url if the url is authorized
     */
    public String getServiceBackUrl(HttpServletRequest request,String strBackUrlParameter)
    {   	
    	return getServiceBackUrl(request, VerifyBackUrlConstants.PARAMETER_BACK_URL,VerifyBackUrlConstants.SESSION_ATTRIBUTE_BACK_URL);
    }
    
    
    /** 
     * return the service back url if the url is authorized
     * @param request the request
     * @param strBackUrlParameter  the parameter name of the service back url
     * @param strBackUrlSessionName The session attribute name who is stored the back url
     * @return the service back url if the url is authorized
     */
    public String getServiceBackUrl(HttpServletRequest request,String strBackUrlParameter,String strBackUrlSessionName)
    {   	
    	 String strUrl= request.getParameter(strBackUrlParameter);
    	
    	 
         if ( strUrl!= null &&   ProcessConstraintsService.checkConstraints( strUrl ))
         {
        	 VerifiyBackUrlUtils.storeBackUrlInSession( request, strUrl,strBackUrlSessionName );

         }
         else if ( strUrl!= null  )
         {
             //this is for the security : if a service provide a back url,
             //but this url breaks constaints, then drop the service in session
             VerifiyBackUrlUtils.dropBackUrlInSession( request,strBackUrlSessionName );
         }
    	
    	return VerifiyBackUrlUtils.getBackUrlInSession(request,strBackUrlSessionName);
    }
    
    
}
