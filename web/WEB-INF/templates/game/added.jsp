<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty confirmed}">
    <h2><a href="/game/detail?gameId=${gameId}">Hra</a> byla úspěšně přidána.</h2>
</c:if>
<c:if test="${empty confirmed}">
    <h2>Hra byla přidána na seznam her ke schválení.</h2>
    V <a href="/kalendar">kalendáři</a> akcí se objeví ihned po schválení některým z administrátorů.
</c:if>