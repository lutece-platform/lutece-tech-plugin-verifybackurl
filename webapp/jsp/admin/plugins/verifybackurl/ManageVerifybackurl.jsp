<%@ page errorPage="../../ErrorPage.jsp" %>

<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="manageverifybackurl" scope="session" class="fr.paris.lutece.plugins.verifybackurl.web.ManageVerifybackurlJspBean" />

<% manageverifybackurl.init( request, manageverifybackurl.RIGHT_MANAGEVERIFYBACKURL ); %>
<%= manageverifybackurl.getManageVerifybackurlHome ( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
