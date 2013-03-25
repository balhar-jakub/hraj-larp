<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h2>Hry,kterých se budu účastnit</h2>
<table>
    <tr>
        <td>Jméno hry</td>
        <td>Stav (Normální / Náhradník)</td>
    </tr>
    <c:forEach items="${futureGames}" var="game">

    </c:forEach>
</table>
<h2>Hry,kterých jsem se zúčastnil</h2>
<table>
    <tr>
        <td>Jméno hry</td>
        <td>Stav (Normální / Náhradník)</td>
    </tr>
    <c:forEach items="${formerGames}" var="game">
    </c:forEach>
</table>
</body>
</html>