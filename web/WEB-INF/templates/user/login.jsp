<%-- <%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

<c:if test="${not empty info}">
    <font color="red">
    	<c:out value="${info}"/><br />
        <%-- <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>. --%>
    </font>
</c:if>

<form method="post" name="f" action="<c:url value='/j_spring_security_check'/>">    
	<div>
	<label style="width: 48%; float: left;" for="userName">Jméno: </label>
    <input style="width: 48%;" type="text" id="userName" 
    	name="j_username" 
    	value="<c:if test='${not empty info}'><c:out value='${SPRING_SECURITY_LAST_USERNAME}'/></c:if>" />
    </div>
    <div>
    <label style="width: 48%; float: left;" for="password">Heslo: </label>
    <input style="width: 48%;" type="password" id="password" 
    	name="j_password" value="" />
    </div>
    <div>
    <input type="checkbox" name="_spring_security_remember_me" 
    id="_spring_security_remember_me" /> Trvalé přihlášení
    </div>
    <div>
    <input type="submit" value="Přihlásit se">
    </div>
</form>