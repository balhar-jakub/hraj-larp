<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Hry,kterých se budu účastnit</h2>
<table class="attended">
    <tr class="header">
        <td>Jméno hry</td>
        <td>Stav (Normální / Náhradník)</td>
    </tr>
    <c:forEach items="${futureGames}" var="future">
        <tr>
            <td>${future.attendedGame.name}</td>
            <td>${future.substituteText}</td>
        </tr>
    </c:forEach>
</table>
<h2>Hry,kterých jsem se zúčastnil</h2>
<table class="attended">
    <tr class="header">
        <td>Jméno hry</td>
        <td>Stav (Normální / Náhradník)</td>
    </tr>
    <c:forEach items="${formerGames}" var="former">
        <tr>
            <td>${former.attendedGame.name}</td>
            <td>${former.substituteText}</td>
        </tr>
    </c:forEach>
</table>