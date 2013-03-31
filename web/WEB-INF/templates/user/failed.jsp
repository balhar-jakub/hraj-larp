<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %>
<h2>Failed</h2>
<c:out value='${SPRING_SECURITY_LAST_USERNAME}'/><br />
<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>