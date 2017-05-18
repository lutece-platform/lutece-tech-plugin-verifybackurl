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

import fr.paris.lutece.plugins.verifybackurl.business.AuthorizedUrl;
import fr.paris.lutece.plugins.verifybackurl.business.AuthorizedUrlHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.util.url.UrlItem;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * This class provides the user interface to manage AuthorizedUrl features ( manage, create, modify, remove )
 */
@Controller( controllerJsp = "ManageAuthorizedUrls.jsp", controllerPath = "jsp/admin/plugins/verifybackurl/", right = "VERIFYBACKURL_MANAGEMENT" )
public class AuthorizedUrlJspBean extends ManageVerifybackurlJspBean
{
    // Templates
    private static final String TEMPLATE_MANAGE_AUTHORIZEDURLS = "/admin/plugins/verifybackurl/manage_authorizedurls.html";
    private static final String TEMPLATE_CREATE_AUTHORIZEDURL = "/admin/plugins/verifybackurl/create_authorizedurl.html";
    private static final String TEMPLATE_MODIFY_AUTHORIZEDURL = "/admin/plugins/verifybackurl/modify_authorizedurl.html";

    // Parameters
    private static final String PARAMETER_ID_AUTHORIZEDURL = "id";

    // Properties for page titles
    private static final String PROPERTY_PAGE_TITLE_MANAGE_AUTHORIZEDURLS = "verifybackurl.manage_authorizedurls.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_MODIFY_AUTHORIZEDURL = "verifybackurl.modify_authorizedurl.pageTitle";
    private static final String PROPERTY_PAGE_TITLE_CREATE_AUTHORIZEDURL = "verifybackurl.create_authorizedurl.pageTitle";

    // Markers
    private static final String MARK_AUTHORIZEDURL_LIST = "authorizedurl_list";
    private static final String MARK_AUTHORIZEDURL = "authorizedurl";

    private static final String JSP_MANAGE_AUTHORIZEDURLS = "jsp/admin/plugins/verifybackurl/ManageAuthorizedUrls.jsp";

    // Properties
    private static final String MESSAGE_CONFIRM_REMOVE_AUTHORIZEDURL = "verifybackurl.message.confirmRemoveAuthorizedUrl";

    // Validations
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "verifybackurl.model.entity.authorizedurl.attribute.";

    // Views
    private static final String VIEW_MANAGE_AUTHORIZEDURLS = "manageAuthorizedUrls";
    private static final String VIEW_CREATE_AUTHORIZEDURL = "createAuthorizedUrl";
    private static final String VIEW_MODIFY_AUTHORIZEDURL = "modifyAuthorizedUrl";

    // Actions
    private static final String ACTION_CREATE_AUTHORIZEDURL = "createAuthorizedUrl";
    private static final String ACTION_MODIFY_AUTHORIZEDURL = "modifyAuthorizedUrl";
    private static final String ACTION_REMOVE_AUTHORIZEDURL = "removeAuthorizedUrl";
    private static final String ACTION_CONFIRM_REMOVE_AUTHORIZEDURL = "confirmRemoveAuthorizedUrl";

    // Infos
    private static final String INFO_AUTHORIZEDURL_CREATED = "verifybackurl.info.authorizedurl.created";
    private static final String INFO_AUTHORIZEDURL_UPDATED = "verifybackurl.info.authorizedurl.updated";
    private static final String INFO_AUTHORIZEDURL_REMOVED = "verifybackurl.info.authorizedurl.removed";
    
    // Session variable to store working values
    private AuthorizedUrl _authorizedurl;
    
    /**
     * Build the Manage View
     * @param request The HTTP request
     * @return The page
     */
    @View( value = VIEW_MANAGE_AUTHORIZEDURLS, defaultView = true )
    public String getManageAuthorizedUrls( HttpServletRequest request )
    {
        _authorizedurl = null;
        List<AuthorizedUrl> listAuthorizedUrls = AuthorizedUrlHome.getAuthorizedUrlsList(  );
        Map<String, Object> model = getPaginatedListModel( request, MARK_AUTHORIZEDURL_LIST, listAuthorizedUrls, JSP_MANAGE_AUTHORIZEDURLS );

        return getPage( PROPERTY_PAGE_TITLE_MANAGE_AUTHORIZEDURLS, TEMPLATE_MANAGE_AUTHORIZEDURLS, model );
    }

    /**
     * Returns the form to create a authorizedurl
     *
     * @param request The Http request
     * @return the html code of the authorizedurl form
     */
    @View( VIEW_CREATE_AUTHORIZEDURL )
    public String getCreateAuthorizedUrl( HttpServletRequest request )
    {
        _authorizedurl = ( _authorizedurl != null ) ? _authorizedurl : new AuthorizedUrl(  );

        Map<String, Object> model = getModel(  );
        model.put( MARK_AUTHORIZEDURL, _authorizedurl );

        return getPage( PROPERTY_PAGE_TITLE_CREATE_AUTHORIZEDURL, TEMPLATE_CREATE_AUTHORIZEDURL, model );
    }

    /**
     * Process the data capture form of a new authorizedurl
     *
     * @param request The Http Request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_CREATE_AUTHORIZEDURL )
    public String doCreateAuthorizedUrl( HttpServletRequest request )
    {
        populate( _authorizedurl, request );

        // Check constraints
        if ( !validateBean( _authorizedurl, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirectView( request, VIEW_CREATE_AUTHORIZEDURL );
        }

        AuthorizedUrlHome.create( _authorizedurl );
        addInfo( INFO_AUTHORIZEDURL_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_AUTHORIZEDURLS );
    }

    /**
     * Manages the removal form of a authorizedurl whose identifier is in the http
     * request
     *
     * @param request The Http request
     * @return the html code to confirm
     */
    @Action( ACTION_CONFIRM_REMOVE_AUTHORIZEDURL )
    public String getConfirmRemoveAuthorizedUrl( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_AUTHORIZEDURL ) );
        UrlItem url = new UrlItem( getActionUrl( ACTION_REMOVE_AUTHORIZEDURL ) );
        url.addParameter( PARAMETER_ID_AUTHORIZEDURL, nId );

        String strMessageUrl = AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_AUTHORIZEDURL, url.getUrl(  ), AdminMessage.TYPE_CONFIRMATION );

        return redirect( request, strMessageUrl );
    }

    /**
     * Handles the removal form of a authorizedurl
     *
     * @param request The Http request
     * @return the jsp URL to display the form to manage authorizedurls
     */
    @Action( ACTION_REMOVE_AUTHORIZEDURL )
    public String doRemoveAuthorizedUrl( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_AUTHORIZEDURL ) );
        AuthorizedUrlHome.remove( nId );
        addInfo( INFO_AUTHORIZEDURL_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_AUTHORIZEDURLS );
    }

    /**
     * Returns the form to update info about a authorizedurl
     *
     * @param request The Http request
     * @return The HTML form to update info
     */
    @View( VIEW_MODIFY_AUTHORIZEDURL )
    public String getModifyAuthorizedUrl( HttpServletRequest request )
    {
        int nId = Integer.parseInt( request.getParameter( PARAMETER_ID_AUTHORIZEDURL ) );

        if ( _authorizedurl == null || ( _authorizedurl.getId(  ) != nId ))
        {
            _authorizedurl = AuthorizedUrlHome.findByPrimaryKey( nId );
        }

        Map<String, Object> model = getModel(  );
        model.put( MARK_AUTHORIZEDURL, _authorizedurl );

        return getPage( PROPERTY_PAGE_TITLE_MODIFY_AUTHORIZEDURL, TEMPLATE_MODIFY_AUTHORIZEDURL, model );
    }

    /**
     * Process the change form of a authorizedurl
     *
     * @param request The Http request
     * @return The Jsp URL of the process result
     */
    @Action( ACTION_MODIFY_AUTHORIZEDURL )
    public String doModifyAuthorizedUrl( HttpServletRequest request )
    {
        populate( _authorizedurl, request );

        // Check constraints
        if ( !validateBean( _authorizedurl, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            return redirect( request, VIEW_MODIFY_AUTHORIZEDURL, PARAMETER_ID_AUTHORIZEDURL, _authorizedurl.getId( ) );
        }

        AuthorizedUrlHome.update( _authorizedurl );
        addInfo( INFO_AUTHORIZEDURL_UPDATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_AUTHORIZEDURLS );
    }
}