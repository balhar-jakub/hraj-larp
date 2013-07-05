<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2><a href="/game/detail?gameId=${gameId}">Hra</a> byla upravena.</h2>
<c:if test="${not empty confirmedNow}">Hra byla také zařazena mezi schválené.</c:if>