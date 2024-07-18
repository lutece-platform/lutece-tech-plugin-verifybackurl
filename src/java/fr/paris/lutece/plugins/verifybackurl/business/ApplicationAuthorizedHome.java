/*
 * Copyright (c) 2002-2024, Mairie de Paris
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

import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.util.ReferenceList;

/**
 * 
 * ApplicationAuthorizedHome
 *
 */
public class ApplicationAuthorizedHome
{
    // Static variable pointed at the DAO instance
    private static IApplicationAuthorizedDAO _dao = SpringContextService.getBean( "verifybackurl.applicationAuthorizedDAO" );
    private static Plugin _plugin = PluginService.getPlugin( "verifybackurl" );

    /**
     * Private constructor - this class need not be instantiated
     */
    private ApplicationAuthorizedHome(  )
    {
    }

    /**
     * Create an instance of the applicationAuthorized class
     * @param applicationAuthorized The instance of the ApplicationAuthorized which contains the informations to store
     * @return The  instance of applicationAuthorized which has been created with its primary key.
     */
    public static ApplicationAuthorized create( ApplicationAuthorized applicationAuthorized )
    {
        _dao.insert( applicationAuthorized, _plugin );
        
        //Create url
        if( applicationAuthorized.getListAuthorizedUrl( ) != null )
        {
            for( AuthorizedUrl url :  applicationAuthorized.getListAuthorizedUrl( ) )
            {
                url.setIdApplication( applicationAuthorized.getId( ) );
                
                AuthorizedUrlHome.create( url );
            }
        }

        return applicationAuthorized;
    }

    /**
     * Update of the applicationAuthorized which is specified in parameter
     * @param applicationAuthorized The instance of the ApplicationAuthorized which contains the data to store
     * @return The instance of the  applicationAuthorized which has been updated
     */
    public static ApplicationAuthorized update( ApplicationAuthorized applicationAuthorized )
    {
        _dao.store( applicationAuthorized, _plugin );
        
        //Remove all authorized url
        AuthorizedUrlHome.removeByApplicationId( applicationAuthorized.getId( ) );
        
        //Create url
        if( applicationAuthorized.getListAuthorizedUrl( ) != null )
        {
            for( AuthorizedUrl url :  applicationAuthorized.getListAuthorizedUrl( ) )
            {
                url.setIdApplication( applicationAuthorized.getId( ) );
                
                AuthorizedUrlHome.create( url );
            }
        }

        return applicationAuthorized;
    }

    /**
     * Remove the applicationAuthorized whose identifier is specified in parameter
     * @param nKey The applicationAuthorized Id
     */
    public static void remove( int nKey )
    {
        _dao.delete( nKey, _plugin );
        
        //Remove all authorized url
        AuthorizedUrlHome.removeByApplicationId( nKey );
    }

    /**
     * Returns an instance of a applicationAuthorized whose identifier is specified in parameter
     * @param nKey The applicationAuthorized primary key
     * @return an instance of ApplicationAuthorized
     */
    public static ApplicationAuthorized findByPrimaryKey( int nKey )
    {
        ApplicationAuthorized applicationAuthorized = _dao.load( nKey, _plugin);
        
        //Load url
        if( applicationAuthorized != null )
        {
            applicationAuthorized.setListAuthorizedUrl( AuthorizedUrlHome.getAuthorizedUrlsListByApplicationId( nKey ) );
        }
        
        return applicationAuthorized;
    }
    
    /**
     * Returns an instance of a applicationAuthorized by application code is specified in parameter
     * @param strApplicationCode The application code
     * @return an instance of ApplicationAuthorized
     */
    public static ApplicationAuthorized findByApplicationCode( String strApplicationCode )
    {
        ApplicationAuthorized applicationAuthorized = _dao.loadByApplicationCode( strApplicationCode, _plugin);
        
        //Load url
        if( applicationAuthorized != null )
        {
            applicationAuthorized.setListAuthorizedUrl( AuthorizedUrlHome.getAuthorizedUrlsListByApplicationId( applicationAuthorized.getId( ) ) );
        }
        
        return applicationAuthorized;
    }

    /**
     * Load the data of all the applicationAuthorized objects and returns them as a list
     * @return the list which contains the data of all the applicationAuthorized objects
     */
    public static List<ApplicationAuthorized> getApplicationAuthorizedsList( )
    {
        List<ApplicationAuthorized> applicationAuthorizedList = _dao.selectApplicationAuthorizedsList( _plugin );
        
        //Load url
        if ( applicationAuthorizedList != null )
        {
            for ( ApplicationAuthorized app : applicationAuthorizedList )
            {
                app.setListAuthorizedUrl( AuthorizedUrlHome.getAuthorizedUrlsListByApplicationId( app.getId( ) ) );
            }
        }
        
        return applicationAuthorizedList;
    }
    
    /**
     * Load the id of all the applicationAuthorized objects and returns them as a list
     * @return the list which contains the id of all the applicationAuthorized objects
     */
    public static List<Integer> getIdApplicationAuthorizedsList( )
    {
        return _dao.selectIdApplicationAuthorizedsList( _plugin );
    }
    
    /**
     * Load the data of all the applicationAuthorized objects and returns them as a referenceList
     * @return the referenceList which contains the data of all the applicationAuthorized objects
     */
    public static ReferenceList getApplicationAuthorizedsReferenceList( )
    {
        return _dao.selectApplicationAuthorizedsReferenceList( _plugin );
    }
    
    
    /**
     * Load the data of all the avant objects and returns them as a list
     * @param listIds liste of ids
     * @return the list which contains the data of all the avant objects
     */
    public static List<ApplicationAuthorized> getApplicationAuthorizedsListByIds( List<Integer> listIds )
    {
        List<ApplicationAuthorized>  applicationAuthorizedList =_dao.selectApplicationAuthorizedsListByIds( _plugin, listIds );
        
        //Load url
        if ( applicationAuthorizedList != null )
        {
            for ( ApplicationAuthorized app : applicationAuthorizedList )
            {
                app.setListAuthorizedUrl( AuthorizedUrlHome.getAuthorizedUrlsListByApplicationId( app.getId( ) ) );
            }
        }
        
        return applicationAuthorizedList;
    }


}
