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
package fr.paris.lutece.plugins.verifybackurl.web;

import fr.paris.lutece.plugins.verifybackurl.business.ApplicationAuthorized;
import fr.paris.lutece.plugins.verifybackurl.business.ApplicationAuthorizedHome;
import fr.paris.lutece.plugins.verifybackurl.business.AuthorizedUrl;
import fr.paris.lutece.plugins.verifybackurl.business.AuthorizedUrlHome;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.security.SecurityTokenService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.html.AbstractPaginator;
import fr.paris.lutece.util.url.UrlItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage AuthorizedUrl features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageAuthorizedUrls.jsp", controllerPath = "jsp/admin/plugins/verifybackurl/", right = "VERIFYBACKURL_MANAGEMENT" )
public class AuthorizedUrlJspBean extends ManageVerifybackurlJspBean <Integer, ApplicationAuthorized>
{
    // Templates
    private static final String TEMPLATE_MANAGE_APPLICATIONAUTHORIZEDS = "/admin/plugins/verifybackurl/manage_applicationauthorizeds.html";
    private static final String TEMPLATE_CREATE_APPLICATIONAUTHORIZED = "/admin/plugins/verifybackurl/create_applicationauthorized.html";
    private static final String TEMPLATE_MODIFY_APPLICATIONAUTHORIZED = "/admin/plugins/verifybackurl/modify_applicationauthorized.html";
    
    // Parameters
    private static final String PARAMETER_ID_APPLICATIONAUTHORIZED = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_APPLICATIONAUTHORIZEDS = "verifybackurl.manage_applicationauthorizeds.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_APPLICATIONAUTHORIZED = "verifybackurl.modify_applicationauthorized.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_APPLICATIONAUTHORIZED = "verifybackurl.create_applicationauthorized.pageTitle";
    
    // Markers
    private static final String MARK_APPLICATIONAUTHORIZED_LIST = "applicationauthorized_list";
    private static final String MARK_APPLICATIONAUTHORIZED = "applicationauthorized";

    private static final String JSP_MANAGE_AUTHORIZEDURLS = "jsp/admin/plugins/verifybackurl/ManageAuthorizedUrls.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_APPLICATIONAUTHORIZED = "verifybackurl.message.confirmRemoveApplicationAuthorized";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "verifybackurl.model.entity.authorizedurl.attribute.";
    private static final String VALIDATION_ATTRIBUTES_PREFIX_APPLICATION = "verifybackurl.model.entity.applicationauthorized.attribute.";
    // Views
    private static final String VIEW_MANAGE_APPLICATIONAUTHORIZEDS = "manageApplicationAuthorizeds";
    private static final String VIEW_CREATE_APPLICATIONAUTHORIZED = "createApplicationAuthorized";
    private static final String VIEW_MODIFY_APPLICATIONAUTHORIZED = "modifyApplicationAuthorized";
    
    // Actions
    private static final String ACTION_CREATE_APPLICATIONAUTHORIZED = "createApplicationAuthorized";
    private static final String ACTION_MODIFY_APPLICATIONAUTHORIZED = "modifyApplicationAuthorized";
    private static final String ACTION_REMOVE_APPLICATIONAUTHORIZED = "removeApplicationAuthorized";
    private static final String ACTION_CONFIRM_REMOVE_APPLICATIONAUTHORIZED = "confirmRemoveApplicationAuthorized";


    // Infos
    private static final String INFO_APPLICATIONAUTHORIZED_CREATED = "verifybackurl.info.applicationauthorized.created";
    private static final String INFO_APPLICATIONAUTHORIZED_UPDATED = "verifybackurl.info.applicationauthorized.updated";
    private static final String INFO_APPLICATIONAUTHORIZED_REMOVED = "verifybackurl.info.applicationauthorized.removed";
    
    // Errors
    private static final String ERROR_RESOURCE_NOT_FOUND = "Resource not found";
    
    // Session variable to store working values
    private ApplicationAuthorized _applicationauthorized;
    private List<Integer> _listIdApplicationAuthorizeds;
    
    
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_APPLICATIONAUTHORIZEDS, defaultView = true )
    public String getManageApplicationAuthorizeds( HttpServletRequest request )
    {
        _applicationauthorized = null;
        
        if ( request.getParameter( AbstractPaginator.PARAMETER_PAGE_INDEX) == null || _listIdApplicationAuthorizeds.isEmpty( ) )
        {
            _listIdApplicationAuthorizeds = ApplicationAuthorizedHome.getIdApplicationAuthorizedsList(  );
        }
        
        Map<String, Object> model = getPaginatedListModel( request, MARK_APPLICATIONAUTHORIZED_LIST, _listIdApplicationAuthorizeds, JSP_MANAGE_AUTHORIZEDURLS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_APPLICATIONAUTHORIZEDS, TEMPLATE_MANAGE_APPLICATIONAUTHORIZEDS, model );
    }

    /**
     * Get Items from Ids list
     * @param listIds
     * @return the populated list of items corresponding to the id List
     */
    @Override
    List<ApplicationAuthorized> getItemsFromIds( List<Integer> listIds ) 
    {
        List<ApplicationAuthorized> listApplicationAuthorized = ApplicationAuthorizedHome.getApplicationAuthorizedsListByIds( listIds );
        
        // keep original order
        return listApplicationAuthorized.stream()
                 .sorted(Comparator.comparingInt( notif -> listIds.indexOf( notif.getId())))
                 .collect(Collectors.toList());
    }
    
    @Override
    int getPluginDefaultNumberOfItemPerPage( ) {
        return AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_LIST_ITEM_PER_PAGE, 50 );
    }
    
    /**
    * reset the _listIdApplicationAuthorizeds list
    */
    public void resetListId( )
    {
        _listIdApplicationAuthorizeds = new ArrayList<>( );
    }

    /**
     * Returns the form to create a applicationauthorized
     *
     * @param request The Http request
     * @return the html code of the applicationauthorized form
     */
    @View( VIEW_CREATE_APPLICATIONAUTHORIZED )
    public String getCreateApplicationAuthorized( HttpServletRequest request )
    {
        _applicationauthorized = ( _applicationauthorized != null ) ? _applicationauthorized : new ApplicationAuthorized(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_APPLICATIONAUTHORIZED, _applicationauthorized );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_CREATE_APPLICATIONAUTHORIZED ) );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_APPLICATIONAUTHORIZED, TEMPLATE_CREATE_APPLICATIONAUTHORIZED, model );
    }

    /**
     * Process the data capture form of a new applicationauthorized
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_CREATE_APPLICATIONAUTHORIZED )
    public String doCreateApplicationAuthorized( HttpServletRequest request ) throws AccessDeniedException
    {
        populate( _applicationauthorized, request, getLocale( ) );
        

        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_CREATE_APPLICATIONAUTHORIZED ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _applicationauthorized, VALIDATION_ATTRIBUTES_PREFIX_APPLICATION ) )
        {
            return redirectView( request, VIEW_CREATE_APPLICATIONAUTHORIZED );
        }
               
        _applicationauthorized.setListAuthorizedUrl( populateAuthorizedUrlList( request ) );
        ApplicationAuthorizedHome.create( _applicationauthorized );

        addInfo( INFO_APPLICATIONAUTHORIZED_CREATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_APPLICATIONAUTHORIZEDS );
    }

    /**
     * Manages the removal form of a applicationauthorized whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_APPLICATIONAUTHORIZED )
    public String getConfirmRemoveApplicationAuthorized( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATIONAUTHORIZED ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_APPLICATIONAUTHORIZED ) );
        url.addParameter( PARAMETER_ID_APPLICATIONAUTHORIZED, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_APPLICATIONAUTHORIZED, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a applicationauthorized
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage applicationauthorizeds
     */
    @Action( ACTION_REMOVE_APPLICATIONAUTHORIZED )
    public String doRemoveApplicationAuthorized( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATIONAUTHORIZED ) );
        
        
        ApplicationAuthorizedHome.remove( nId );
        addInfo( INFO_APPLICATIONAUTHORIZED_REMOVED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_APPLICATIONAUTHORIZEDS );
    }

    /**
     * Returns the form to update info about a applicationauthorized
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_APPLICATIONAUTHORIZED )
    public String getModifyApplicationAuthorized( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_APPLICATIONAUTHORIZED ) );

        if ( _applicationauthorized == null || ( _applicationauthorized.getId(  ) != nId ) )
        {
            ApplicationAuthorized optApplicationAuthorized = ApplicationAuthorizedHome.findByPrimaryKey( nId );
            
            if( optApplicationAuthorized != null )
            {
                _applicationauthorized = optApplicationAuthorized;
            }
            else
            {
                throw new AppException( ERROR_RESOURCE_NOT_FOUND );
            }
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_APPLICATIONAUTHORIZED, _applicationauthorized );
        model.put( SecurityTokenService.MARK_TOKEN, SecurityTokenService.getInstance( ).getToken( request, ACTION_MODIFY_APPLICATIONAUTHORIZED ) );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_APPLICATIONAUTHORIZED, TEMPLATE_MODIFY_APPLICATIONAUTHORIZED, model );
    }

    /**
     * Process the change form of a applicationauthorized
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     * @throws AccessDeniedException
     */
    @Action( ACTION_MODIFY_APPLICATIONAUTHORIZED )
    public String doModifyApplicationAuthorized( HttpServletRequest request ) throws AccessDeniedException
    {   
        populate( _applicationauthorized, request, getLocale( ) );
        
        
        if ( !SecurityTokenService.getInstance( ).validate( request, ACTION_MODIFY_APPLICATIONAUTHORIZED ) )
        {
            throw new AccessDeniedException ( "Invalid security token" );
        }

        // Check constraints
        if ( !validateBean( _applicationauthorized, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_APPLICATIONAUTHORIZED, PARAMETER_ID_APPLICATIONAUTHORIZED, _applicationauthorized.getId( ) );
        }

        //Populate authorized url
        _applicationauthorized.setListAuthorizedUrl( populateAuthorizedUrlList( request ) );
        ApplicationAuthorizedHome.update( _applicationauthorized );
        
        addInfo( INFO_APPLICATIONAUTHORIZED_UPDATED, getLocale(  ) );
        resetListId( );

        return redirectView( request, VIEW_MANAGE_APPLICATIONAUTHORIZEDS );
    }
    
    /**
     * Populate authorized url
     * @param request
     * @return list of authorized url
     */
    private List<AuthorizedUrl> populateAuthorizedUrlList( HttpServletRequest request )
    {
        //Populate url
        List<AuthorizedUrl> listAuthorizedUrl = new ArrayList<>();
        String[] listUrls = request.getParameterValues( "url" );
        if( listUrls != null )
        {
            for( String url : listUrls )
            {
                AuthorizedUrl authorizedUrl = new AuthorizedUrl( );
                authorizedUrl.setUrl( url );
                
                listAuthorizedUrl.add( authorizedUrl );
            }
        }
        return listAuthorizedUrl;
    }

}