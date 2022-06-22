/*
 * Copyright (c) 2002-2016, Mairie de Paris
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
 package fr.paris.lutece.plugins.verifybackurl.business;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

import java.util.List;

/**
 * This class provides instances management methods (create, find, ...) for AuthorizedUrl objects
 */
public final class AuthorizedUrlHome
{
    // Static variable pointed at the DAO instance
    private static IAuthorizedUrlDAO _dao = SpringContextService.getBean( "verifybackurl.authorizedUrlDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "verifybackurl" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private AuthorizedUrlHome(  )
    {
    }

    /**
     * Create an instance of the authorizedUrl class
     * @param authorizedUrl The instance of the AuthorizedUrl which contains the informations to store
     * @return The  instance of authorizedUrl which has been created with its primary key.
     */
    public static AuthorizedUrl create( AuthorizedUrl authorizedUrl )
    {
        _dao.insert( authorizedUrl, _plugin );

        return authorizedUrl;
    }

    /**
     * Update of the authorizedUrl which is specified in parameter
     * @param authorizedUrl The instance of the AuthorizedUrl which contains the data to store
     * @return The instance of the  authorizedUrl which has been updated
     */
    public static AuthorizedUrl update( AuthorizedUrl authorizedUrl )
    {
        _dao.store( authorizedUrl, _plugin );

        return authorizedUrl;
    }

    /**
     * Remove the authorizedUrl whose identifier is specified in parameter
     * @param nKey The authorizedUrl Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
    }

    /**
     * Returns an instance of a authorizedUrl whose identifier is specified in parameter
     * @param nKey The authorizedUrl primary key
     * @return an instance of AuthorizedUrl
     */
    public static AuthorizedUrl findByPrimaryKey( int nKey )
    {
        return _dao.load( nKey, _plugin);
    }

    /**
     * Load the data of all the authorizedUrl objects and returns them as a list
     * @return the list which contains the data of all the authorizedUrl objects
     */
    public static List<AuthorizedUrl> getAuthorizedUrlsList( )
    {
        return _dao.selectAuthorizedUrlsList( _plugin );
    }
    
    /**
     * Load the data of all the authorizedUrl objects and returns them as a list
     * @param strApplicationCode the application Code
     * @return the list which contains the data of all the authorizedUrl objects
     */
    public static List<AuthorizedUrl> getAuthorizedUrlsListByApplicationCode(String strApplicationCode )
    {
        return _dao.selectAuthorizedUrlsByApplicationCode(strApplicationCode, _plugin );
    }
    
    /**
     * Load the id of all the authorizedUrl objects and returns them as a list
     * @return the list which contains the id of all the authorizedUrl objects
     */
    public static List<Integer> getIdAuthorizedUrlsList( )
    {
        return _dao.selectIdAuthorizedUrlsList( _plugin );
    }
    
    /**
     * Load the data of all the authorizedUrl objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the authorizedUrl objects
     */
    public static ReferenceList getAuthorizedUrlsReferenceList( )
    {
        return _dao.selectAuthorizedUrlsReferenceList(_plugin );
    }
}

