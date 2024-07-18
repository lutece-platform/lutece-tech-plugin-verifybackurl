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

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * ApplicationAuthorizedDAO
 *
 */
public class ApplicationAuthorizedDAO implements IApplicationAuthorizedDAO
{
    private static final String SQL_QUERY_INSERT = "INSERT INTO verifybackurl_application ( application_code, name ) VALUES ( ?, ? ) ";
    private static final String SQL_QUERY_SELECT = "SELECT id_application, application_code, name FROM verifybackurl_application WHERE id_application = ?";
    private static final String SQL_QUERY_DELETE = "DELETE FROM verifybackurl_application WHERE id_application = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE verifybackurl_application SET application_code = ?, name = ? WHERE id_application = ?";
    private static final String SQL_QUERY_SELECTALL = "SELECT id_application, application_code, name FROM verifybackurl_application";
    private static final String SQL_QUERY_SELECTALL_ID = "SELECT id_application FROM verifybackurl_application";
    private static final String SQL_QUERY_SELECT_BY_APPLICATION_CODE = "SELECT id_application, application_code, name FROM verifybackurl_application WHERE application_code = ?";
    private static final String SQL_QUERY_SELECTALL_BY_IDS = "SELECT id_application, application_code, name FROM verifybackurl_application WHERE id_application IN (  ";

    @Override
    public void insert( ApplicationAuthorized applicationAuthorized, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;

            daoUtil.setString( nIndex++ , applicationAuthorized.getApplicationCode( ) );
            daoUtil.setString( nIndex++ , applicationAuthorized.getName( ) );
            
            daoUtil.executeUpdate( );
            
            if ( daoUtil.nextGeneratedKey( ) ) 
            {
                applicationAuthorized.setId( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
    }

    @Override
    public void store( ApplicationAuthorized applicationAuthorized, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            int nIndex = 1;
            
            daoUtil.setString( nIndex++ , applicationAuthorized.getApplicationCode( ) );
            daoUtil.setString( nIndex++ , applicationAuthorized.getName( ) );
            daoUtil.setInt( nIndex , applicationAuthorized.getId( ) );
    
            daoUtil.executeUpdate( );
        }
        
    }

    @Override
    public void delete( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1 , nKey );
            daoUtil.executeUpdate( );
        }
    }

    @Override
    public ApplicationAuthorized load( int nKey, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT, plugin ) )
        {
            daoUtil.setInt( 1 , nKey );
            daoUtil.executeQuery( );
            ApplicationAuthorized applicationAuthorized = null;
    
            if ( daoUtil.next( ) )
            {
                applicationAuthorized = new ApplicationAuthorized();
                int nIndex = 1;
                
                applicationAuthorized.setId( daoUtil.getInt( nIndex++ ) );
                applicationAuthorized.setApplicationCode( daoUtil.getString( nIndex++ ) );
                applicationAuthorized.setName( daoUtil.getString( nIndex++ ) );
            }
    
            return applicationAuthorized;
        }
    }

    @Override
    public ApplicationAuthorized loadByApplicationCode( String strApplicationCode, Plugin plugin )
    {
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_APPLICATION_CODE, plugin ) )
        {
            daoUtil.setString( 1 , strApplicationCode );
            daoUtil.executeQuery( );
            ApplicationAuthorized applicationAuthorized = null;
    
            if ( daoUtil.next( ) )
            {
                applicationAuthorized = new ApplicationAuthorized();
                int nIndex = 1;
                
                applicationAuthorized.setId( daoUtil.getInt( nIndex++ ) );
                applicationAuthorized.setApplicationCode( daoUtil.getString( nIndex++ ) );
                applicationAuthorized.setName( daoUtil.getString( nIndex++ ) );
            }
    
            return applicationAuthorized;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<ApplicationAuthorized> selectApplicationAuthorizedsList( Plugin plugin )
    {
        List<ApplicationAuthorized> applicationAuthorizedList = new ArrayList<>(  );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery(  );
    
            while ( daoUtil.next(  ) )
            {
                ApplicationAuthorized applicationAuthorized = new ApplicationAuthorized(  );
                int nIndex = 1;
                
                applicationAuthorized.setId( daoUtil.getInt( nIndex++ ) );
                applicationAuthorized.setApplicationCode( daoUtil.getString( nIndex++ ) );
                applicationAuthorized.setName( daoUtil.getString( nIndex++ ) );
    
                applicationAuthorizedList.add( applicationAuthorized );
            }
    
            return applicationAuthorizedList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<Integer> selectIdApplicationAuthorizedsList( Plugin plugin )
    {
        List<Integer> applicationAuthorizedList = new ArrayList<>( );
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL_ID, plugin ) )
        {
            daoUtil.executeQuery(  );
    
            while ( daoUtil.next(  ) )
            {
                applicationAuthorizedList.add( daoUtil.getInt( 1 ) );
            }
    
            return applicationAuthorizedList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public ReferenceList selectApplicationAuthorizedsReferenceList( Plugin plugin )
    {
        ReferenceList applicationAuthorizedList = new ReferenceList();
        try( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery(  );
    
            while ( daoUtil.next(  ) )
            {
                applicationAuthorizedList.addItem( daoUtil.getInt( 1 ) , daoUtil.getString( 2 ) );
            }
    
            return applicationAuthorizedList;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public List<ApplicationAuthorized> selectApplicationAuthorizedsListByIds( Plugin plugin, List<Integer> listIds ) {
        List<ApplicationAuthorized> applicationAuthorizedList = new ArrayList<>(  );
        
        StringBuilder builder = new StringBuilder( );

        if ( !listIds.isEmpty( ) )
        {
            for( int i = 0 ; i < listIds.size(); i++ ) {
                builder.append( "?," );
            }
    
            String placeHolders =  builder.deleteCharAt( builder.length( ) -1 ).toString( );
            String stmt = SQL_QUERY_SELECTALL_BY_IDS + placeHolders + ")";
            
            
            try ( DAOUtil daoUtil = new DAOUtil( stmt, plugin ) )
            {
                int index = 1;
                for( Integer n : listIds ) {
                    daoUtil.setInt(  index++, n ); 
                }
                
                daoUtil.executeQuery(  );
                while ( daoUtil.next(  ) )
                {
                    ApplicationAuthorized applicationAuthorized = new ApplicationAuthorized(  );
                    int nIndex = 1;
                    
                    applicationAuthorized.setId( daoUtil.getInt( nIndex++ ) );
                    applicationAuthorized.setApplicationCode( daoUtil.getString( nIndex++ ) );
                    applicationAuthorized.setName( daoUtil.getString( nIndex++ ) );
                    
                    applicationAuthorizedList.add( applicationAuthorized );
                }
        
                daoUtil.free( );
                
            }
        }
        return applicationAuthorizedList;
        
    }


}
