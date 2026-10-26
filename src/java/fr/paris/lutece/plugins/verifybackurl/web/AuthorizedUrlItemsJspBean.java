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
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.RequestParam;
import fr.paris.lutece.portal.util.mvc.commons.annotations.ResponseBody;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.util.IPager;
import fr.paris.lutece.portal.web.util.Pager;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This class serves the pages of the authorized URLs list to the AJAX pagination
 */
@RequestScoped
@Named
@Controller( controllerJsp = "GetAuthorizedUrlItems.jsp", controllerPath = "jsp/admin/plugins/verifybackurl/", right = "VERIFYBACKURL_MANAGEMENT", securityTokenEnabled = true )
public class AuthorizedUrlItemsJspBean extends MVCAdminJspBean
{
    // Views
    private static final String VIEW_MANAGE_AUTHORIZEDURLS = "manageAuthorizedUrls";

    // Actions
    private static final String ACTION_GET_AUTHORIZEDURL_ITEMS = "getAuthorizedUrlItems";

    @Inject
    @Pager( name = AuthorizedUrlJspBean.PAGER_NAME, listBookmark = AuthorizedUrlJspBean.MARK_AUTHORIZEDURL_LIST, defaultItemsPerPage = AuthorizedUrlJspBean.PROPERTY_DEFAULT_LIST_ITEM_PER_PAGE )
    private IPager<AuthorizedUrl, Void> pager;

    /**
     * Redirects to the authorized URLs list
     *
     * @param request
     *            The HTTP request
     * @return null, the response is redirected
     */
    @View( value = VIEW_MANAGE_AUTHORIZEDURLS, defaultView = true )
    public String getManageAuthorizedUrls( HttpServletRequest request )
    {
        return redirect( request, AuthorizedUrlJspBean.JSP_MANAGE_AUTHORIZEDURLS );
    }

    /**
     * Retrieves a paginated list of authorized URLs for the specified page number.
     *
     * @param numPage
     *            The page number to retrieve.
     * @return A list of authorized URLs for the specified page.
     */
    @Action( value = ACTION_GET_AUTHORIZEDURL_ITEMS, securityTokenDisabled = true )
    @ResponseBody
    public List<AuthorizedUrl> getAuthorizedUrlItems( @RequestParam( "page" ) int numPage )
    {
        return pager.getPaginator( ).map( p -> p.getPageItems( numPage ) ).orElseGet( ArrayList::new );
    }
}
