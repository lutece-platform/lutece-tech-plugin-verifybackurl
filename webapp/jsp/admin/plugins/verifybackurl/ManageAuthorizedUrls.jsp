<jsp:useBean id="manageverifybackurlAuthorizedUrl" scope="session" class="fr.paris.lutece.plugins.verifybackurl.web.AuthorizedUrlJspBean" />
<% String strContent = manageverifybackurlAuthorizedUrl.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
