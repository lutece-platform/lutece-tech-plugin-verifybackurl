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
package fr.paris.lutece.plugins.verifybackurl.utils;

import fr.paris.lutece.portal.service.util.AppPropertiesService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;



public class VerifiyBackUrlUtils
{
    
    public final static PathMatcher PATH_MATCHER = new AntPathMatcher( );
    
    /**
     * Test if the BacUrl is a valid url
     * 
     * @param strBackUrl
     *            the back url to test
     * @return Test if the BackUrl is a valid url
     */
    public static boolean isValidBackUrl( String strBackUrl )
    {
        boolean isValid = true;
        String strAuthorizedDomains = AppPropertiesService.getProperty( VerifyBackUrlConstants.PROPERTY_AUTHORIZED_DOMAINS_BACK_URL );
   
        // test on domains url
        if ( !StringUtils.isEmpty( strAuthorizedDomains ) )
        {
            isValid = false;

            String [ ] tabAuthorizedDomains = strAuthorizedDomains.split( VerifyBackUrlConstants.COMMA );

            for ( int i = 0; i < tabAuthorizedDomains.length; i++ )
            {
                if ( PATH_MATCHER.match( tabAuthorizedDomains [i], strBackUrl ) )
                {
                    isValid = true;

                    break;
                }
            }
        }

        return isValid;
    }
    
    /**
     * Test if the BacUrl is a valid url
     * 
     * @param strBackUrl
     *            the back url to test
     * @return Test if the BackUrl is a valid url
     */
    public static boolean containsNoUnauthorizedHTML( String strBackUrl )
    {
        boolean isValid = true;
        String strPatterUnAuthorizedHTML = AppPropertiesService.getProperty( VerifyBackUrlConstants.PROPERTY_AUTHORIZED_HTML );
        isValid = !strBackUrl.matches( strPatterUnAuthorizedHTML );

        return isValid;
    }
    
    /**
     * Compare to base url
     * @param url1 
     * @param url2
     * @return a boolean if base url of the two input Strings are the same an no empty.
     */
    public static boolean compareBaseUrl( String url1, String url2 )
    {
        String strBaseUrl1 = getBaseUrl( url1 );
        String strBaseUrl2 = getBaseUrl( url2 );
        if ( !StringUtils.isEmpty( strBaseUrl1 ) && strBaseUrl1.equals( strBaseUrl2 ) )
        {
            return true;
        }
        return false;
    }
    
    /**
     * Get base url for input Url : http://site.paris.mdp/webapp/jsp ==> site.paris.mdp/webapp
     * @param strUrl
     * @return the base Url
     */
    public static String getBaseUrl ( String strUrl )
    {
        String strRegexpBaseUrl = AppPropertiesService.getProperty( VerifyBackUrlConstants.PROPERTY_REGEXP_BASE_URL );
        Pattern regex = Pattern.compile( strRegexpBaseUrl );
        Matcher regexMatcher = regex.matcher( strUrl );
        if (regexMatcher.find( ) ) {
            return regexMatcher.group( 2 );
        }
        return "";
    }
    
    /**
     * Store service in session
     * @param request the HttpServletRequest
     * @param strBackUrl the backUrl to store in session
     * @param strSessionAttributeName the strSessionAttributeName who must store the backurl
     * 
     */
    public static void storeBackUrlInSession ( HttpServletRequest request, String strBackUrl,String strSessionAttributeName )
    {
        HttpSession session = request.getSession( true ); 
        session.setAttribute( strSessionAttributeName , strBackUrl );
    }
    
    /**
     * Drop Service in session
     * @param request the HttpServletrequest 
     * @param strSessionAttributeName the strSessionAttributeName who must store the backurl
     */
    
    public static void dropBackUrlInSession ( HttpServletRequest request,String strSessionAttributeName )
    {
        HttpSession session = request.getSession( true );
        session.removeAttribute( strSessionAttributeName );
        
    }
    
    
    /**
     * Return the back url in session
     * @param strSessionAttributeName the strSessionAttributeName who must store the backurl
     * @param request the HttpServletRequest
     */
    public static String getBackUrlInSession ( HttpServletRequest request,String strSessionAttributeName)
    {
        HttpSession session = request.getSession( true ); 
        return (String) session.getAttribute( strSessionAttributeName);
    }
    
    
    
}
