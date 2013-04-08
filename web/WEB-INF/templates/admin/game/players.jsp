<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post">
<table class="players">
    <tr class="first">
        <td>Jméno hráče</td>
        <td>Telefon</td>
        <td>Email</td>
        <td>Pohlaví</td>
        <td></td>
    </tr>
    <c:forEach items="${requestScope.players}" var="player">
        <tr>
            <td>${player.name} ${player.lastName}</td>
            <td>${player.phone}</td>
            <td>${player.email}</td>
            <td>${player.genderTextual}</td>
            <td>
                <button type="submit" formaction="/admin/game/logout/${gameId}/${player.id}">Odhlásit</button>
            </td>
        </tr>
    </c:forEach>
</table>
</form>