<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="displayName">
	<sec:authorize access="isAuthenticated()"> 
     	<sec:authentication property="principal.username" />
     </sec:authorize>
</c:set>

<c:if test="${empty displayName}">
	<div><a href="<c:url value="/user/login" />">Přihlášení</a> 
    <a href="<c:url value="/user/add" />">Registrace</a></div>
</c:if>

<c:if test="${not empty displayName}">
    <div><c:out value="${displayName}"/></div>
    <div><a href="/user/attended">Mé přihlášky</a>
    <a href="<c:url value="/j_spring_security_logout" />">Odhlášení</a></div>
    <div><a href="<c:url value="/user/edit" />">Editace údajů</a></div>
	<!-- <div><a href="/user/edit">Editace údajů</a></div> -->
</c:if>