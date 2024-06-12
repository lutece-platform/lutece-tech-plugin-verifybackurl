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

public class VerifyBackUrlConstants
{
    //Constants for back url domain checking
    public static final String PROPERTY_AUTHORIZED_DOMAINS_BACK_URL = "verifybackurl.backUrl.authorizedDomainsBackUrl";
    public static final String PROPERTY_AUTHORIZED_DOMAINS = "verifybackurl.backUrl.authorizedDomains";
    public static final String PROPERTY_PATTERN_UNAUTHORIZED_BACK_URL = "verifybackurl.backUrl.unauthorizedBackUrl";
    public static final String COMMA = ",";
    
    //Constants for unauthorized HTMl checking
    public static final String PROPERTY_AUTHORIZED_HTML = "verifybackurl.unauthorizedHTML";
    
    //Constants for getting the Name of the service
    public static final String PROPERTY_REGEXP_BASE_URL = "verifybackurl.backUrl.regex.baseUrl";
    
    //Parameters in requests
    public static final String PARAMETER_BACK_URL = "back_url";
    
    //Attribute in session
    public static final String SESSION_ATTRIBUTE_BACK_URL = "back_url";
    
    //Constants for unauthorized characters for domain
    public static final String PROPERTY_UNAUTHORIZED_CHARACTERS_DOMAIN = "verifybackurl.unauthorizedCharactersDomain";
    
    public static final String PROPERTY_ENABLE_BASE64_DECODE="verifybackurl.enableBase64Decode";
    public static final String PROPERTY_ENABLE_BASE64_DECODE_FOR_URL_PATTERN="verifybackurl.enableBase64DecodeForUrlPattern";
    
    
        
}
