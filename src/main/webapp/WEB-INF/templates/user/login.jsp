<%-- <%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>

<c:if test="${not empty info}">
    <font color="red">
        <c:out value="${info}"/><br/>
            <%-- <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>. --%>
    </font>
</c:if>

<c:url value='/login' var="loginUrl"/>
<form method="post" action="${loginUrl}">
    <div>
        <label style="width: 48%; float: left;" for="userName">Jméno: </label>
        <input style="width: 48%;" type="text" id="userName"
               name="username"
               value="<c:if test='${not empty info}'><c:out value='${SPRING_SECURITY_LAST_USERNAME}'/></c:if>"/>
    </div>
    <div>
        <label style="width: 48%; float: left;" for="password">Heslo: </label>
        <input style="width: 48%;" type="password" id="password"
               name="password" value=""/>
    </div>
    <div>
        <input type="checkbox" name="remember-me"
               id="remember-me"/> Trvalé přihlášení
    </div>
    <div>
        <input type="submit" value="Přihlásit se">
    </div>
</form>

<p>Zapomněli jste heslo? Žádost o nové si můžete na mail nechat zaslat <a href="/user/forgotten/">zde</a></p>