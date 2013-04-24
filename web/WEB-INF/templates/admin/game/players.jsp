<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post">
<table class="players">
    <tr class="first">
        <td>Jméno hráče</td>
        <td>Telefon</td>
        <td>Email</td>
        <td>Pohlaví</td>
        <td>Zaplaceno</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach items="${requestScope.players}" var="player">
        <tr>
            <td>${player.userAttended.name} ${player.userAttended.lastName}</td>
            <td>${player.userAttended.phone}</td>
            <td>${player.userAttended.email}</td>
            <td>${player.userAttended.genderTextual}</td>
            <td>${player.payedTextual}</td>
            <td>
                <button type="submit" formaction="/admin/game/logout/${gameId}/${player.userAttended.id}">Odhlásit</button>
            </td>
            <td>
                <c:if test="${not player.automatic}">
                    <c:choose>
                        <c:when test="${not empty player.payed and player.payed}">
                            <button type="submit" formaction="/admin/user/payed/${gameId}/${player.userAttended.id}">Zrušit zaplacení</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" formaction="/admin/user/payed/${gameId}/${player.userAttended.id}">Zaplatil</button>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${not empty requestScope.substitutes}">
        <tr><td>Náhradníci:</td></tr>
        <c:forEach items="${requestScope.substitutes}" var="substitute">
            <tr>
                <td>${substitute.userAttended.name} ${substitute.userAttended.lastName}</td>
                <td>${substitute.userAttended.phone}</td>
                <td>${substitute.userAttended.email}</td>
                <td>${substitute.userAttended.genderTextual}</td>
                <td>${substitute.payedTextual}</td>
                <td>
                    <button type="submit" formaction="/admin/game/logout/${gameId}/${substitute.userAttended.id}">Odhlásit</button>
                </td>
                <td>
                    <c:if test="${not substitute.automatic}">
                        <c:choose>
                            <c:when test="${not empty substitute.payed and substitute.payed}">
                                <button type="submit" formaction="/admin/user/payed/${gameId}/${substitute.userAttended.id}">Zrušit zaplacení</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" formaction="/admin/user/payed/${gameId}/${substitute.userAttended.id}">Zaplatil</button>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</form>