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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides Data Access methods for AuthorizedUrl objects
 */
public final class AuthorizedUrlDAO implements IAuthorizedUrlDAO
{
    // Constants
    private static final String SQL_QUERY_NEW_PK = "SELECT max( id_authorized_url ) FROM verifybackurl_authorized_url";
    private static final String SQL_QUERY_SELECT = "SELECT id_authorized_url, url, name,application_code FROM verifybackurl_authorized_url WHERE id_authorized_url = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO verifybackurl_authorized_url ( id_authorized_url, url, name ,application_code) VALUES ( ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM verifybackurl_authorized_url WHERE id_authorized_url = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE verifybackurl_authorized_url SET id_authorized_url = ?, url = ?, name = ?,application_code = ? WHERE id_authorized_url = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_authorized_url, url, name,application_code FROM verifybackurl_authorized_url";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_authorized_url FROM verifybackurl_authorized_url";
    private static final String SQL_QUERY_SELECTALL_BY_APPLICATION_CODE = SQL_QUERY_SELECTALL+" where application_code = ?";

    /**
     * Generates a new primary key
     * @param plugin The Plugin
     * @return The new primary key
     */
    public int newPrimaryKey( Plugin plugin)
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK , plugin  );
        daoUtil.executeQuery( );
        int nKey = 1;

        if( daoUtil.next( ) )
        {
            nKey = daoUtil.getInt( 1 ) + 1;
        }

        daoUtil.free();
        return nKey;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( AuthorizedUrl authorizedUrl, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin );
        authorizedUrl.setId( newPrimaryKey( plugin ) );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , authorizedUrl.getId( ) );
        daoUtil.setString( nIndex++ , authorizedUrl.getUrl( ) );
        daoUtil.setString( nIndex++ , authorizedUrl.getName( ) );
        daoUtil.setString( nIndex++ , authorizedUrl.getApplicationCode() );
        
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AuthorizedUrl load( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeQuery( );
        AuthorizedUrl authorizedUrl = null;

        if ( daoUtil.next( ) )
        {
            authorizedUrl = new AuthorizedUrl();
            int nIndex = 1;
            
            authorizedUrl.setId( daoUtil.getInt( nIndex++ ) );
            authorizedUrl.setUrl( daoUtil.getString( nIndex++ ) );
            authorizedUrl.setName( daoUtil.getString( nIndex++ ) );
            authorizedUrl.setApplicationCode( daoUtil.getString( nIndex++ ) );
            
        }

        daoUtil.free( );
        return authorizedUrl;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete( int nKey, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin );
        daoUtil.setInt( 1 , nKey );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void store( AuthorizedUrl authorizedUrl, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin );
        int nIndex = 1;
        
        daoUtil.setInt( nIndex++ , authorizedUrl.getId( ) );
        daoUtil.setString( nIndex++ , authorizedUrl.getUrl( ) );
        daoUtil.setString( nIndex++ , authorizedUrl.getName( ) );
        daoUtil.setString( nIndex++ , authorizedUrl.getApplicationCode() );
        
        daoUtil.setInt( nIndex , authorizedUrl.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AuthorizedUrl> selectAuthorizedUrlsList( Plugin plugin )
    {
        List<AuthorizedUrl> authorizedUrlList = new ArrayList<AuthorizedUrl>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            AuthorizedUrl authorizedUrl = new AuthorizedUrl(  );
            int nIndex = 1;
            
            authorizedUrl.setId( daoUtil.getInt( nIndex++ ) );
            authorizedUrl.setUrl( daoUtil.getString( nIndex++ ) );
            authorizedUrl.setName( daoUtil.getString( nIndex++ ) );
            authorizedUrl.setApplicationCode( daoUtil.getString( nIndex++ ) );

            authorizedUrlList.add( authorizedUrl );
        }

        daoUtil.free( );
        return authorizedUrlList;
    }
    
    

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AuthorizedUrl> selectAuthorizedUrlsByApplicationCode(String strApplicationCode,Plugin plugin )
    {
        List<AuthorizedUrl> authorizedUrlList = new ArrayList<AuthorizedUrl>(  );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_APPLICATION_CODE, plugin );
        daoUtil.setString(1, strApplicationCode);
        
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            AuthorizedUrl authorizedUrl = new AuthorizedUrl(  );
            int nIndex = 1;
            
            authorizedUrl.setId( daoUtil.getInt( nIndex++ ) );
            authorizedUrl.setUrl( daoUtil.getString( nIndex++ ) );
            authorizedUrl.setName( daoUtil.getString( nIndex++ ) );
            authorizedUrl.setApplicationCode( daoUtil.getString( nIndex++ ) );

            authorizedUrlList.add( authorizedUrl );
        }

        daoUtil.free( );
        return authorizedUrlList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdAuthorizedUrlsList( Plugin plugin )
    {
        List<Integer> authorizedUrlList = new ArrayList<Integer>( );
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            authorizedUrlList.add( daoUtil.getInt( 1 ) );
        }

        daoUtil.free( );
        return authorizedUrlList;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectAuthorizedUrlsReferenceList( Plugin plugin )
    {
        ReferenceList authorizedUrlList = new ReferenceList();
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            authorizedUrlList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
        }

        daoUtil.free( );
        return authorizedUrlList;
    }
}