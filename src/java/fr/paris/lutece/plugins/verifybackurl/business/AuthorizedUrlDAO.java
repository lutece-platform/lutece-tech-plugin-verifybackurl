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
    private static final String SQL_QUERY_SELECT = "SELECT id_application, url FROM verifybackurl_authorized_url WHERE id_authorized_url = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO verifybackurl_authorized_url ( id_application, url ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_DELETE_BY_APPLICATION_ID = "DELETE FROM verifybackurl_authorized_url WHERE id_application = ? ";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_application, url FROM verifybackurl_authorized_url";
    private static final String SQL_QUERY_SELECTALL_BY_APPLICATION_ID = SQL_QUERY_SELECTALL+" where id_application = ?";

    /**
     * {@inheritDoc }
     */
    @Override
    public void insert( AuthorizedUrl authorizedUrl, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, plugin ) )
        {
            int nIndex = 1;
            
            daoUtil.setInt( nIndex++ , authorizedUrl.getIdApplication( ) );
            daoUtil.setString( nIndex++ , authorizedUrl.getUrl( ) );
            
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public AuthorizedUrl load( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1 , nKey );
            daoUtil.executeQuery( );
            AuthorizedUrl authorizedUrl = null;
    
            if ( daoUtil.next( ) )
            {
                authorizedUrl = new AuthorizedUrl();
                int nIndex = 1;
                
                authorizedUrl.setIdApplication( daoUtil.getInt( nIndex++ ) );
                authorizedUrl.setUrl( daoUtil.getString( nIndex++ ) );
                
            }
    
            return authorizedUrl;
        }
    }
    
    @Override
    public void deleteByApplicationId( int nApplicationId, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_APPLICATION_ID, plugin ) )
        {
            daoUtil.setInt( 1 , nApplicationId );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<AuthorizedUrl> selectAuthorizedUrlsList( Plugin plugin )
    {
        List<AuthorizedUrl> authorizedUrlList = new ArrayList<>(  );
        
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery(  );
    
            while ( daoUtil.next(  ) )
            {
                AuthorizedUrl authorizedUrl = new AuthorizedUrl(  );
                int nIndex = 1;
                
                authorizedUrl.setIdApplication( daoUtil.getInt( nIndex++ ) );
                authorizedUrl.setUrl( daoUtil.getString( nIndex++ ) );
    
                authorizedUrlList.add( authorizedUrl );
            }
    
            return authorizedUrlList;
        }
    }
    
    @Override
    public List<AuthorizedUrl> selectAuthorizedUrlsByApplicationId( Integer nApplicationId, Plugin plugin )
    {
        List<AuthorizedUrl> authorizedUrlList = new ArrayList<>(  );
        
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_BY_APPLICATION_ID, plugin ) )
        {
            daoUtil.setInt(1, nApplicationId );
            
            daoUtil.executeQuery(  );
    
            while ( daoUtil.next(  ) )
            {
                AuthorizedUrl authorizedUrl = new AuthorizedUrl(  );
                int nIndex = 1;
                
                authorizedUrl.setIdApplication( daoUtil.getInt( nIndex++ ) );
                authorizedUrl.setUrl( daoUtil.getString( nIndex++ ) );
    
                authorizedUrlList.add( authorizedUrl );
            }
    
            return authorizedUrlList;
        }
    }
    
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectAuthorizedUrlsReferenceList( Plugin plugin )
    {
        ReferenceList authorizedUrlList = new ReferenceList();
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery(  );
    
            while ( daoUtil.next(  ) )
            {
                authorizedUrlList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
            }
    
            return authorizedUrlList;
        }
    }

}