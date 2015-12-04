<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post">
<h2><c:out value="${gameName}" /></h2>
<span class="printButton"><input type=button onclick="window.print()" value="Tisk seznamu"></span>
<table class="players">
    <tr class="first">
        <td>Přezdívka</td>
        <td>Jméno hráče</td>
        <td>Telefon</td>
        <td>Email</td>
        <td>Pohlaví</td>
        <td>Zaplaceno</td>
        <td></td>
        <td></td>
        <td>Variabilni symbol</td>
    </tr>
    <c:forEach items="${requestScope.players}" var="player">
        <tr>
            <td>${player.userAttended.userName}</td>
            <td>${player.userAttended.name} ${player.userAttended.lastName}</td>
            <td>${player.userAttended.phone}</td>
            <td>${player.userAttended.email}</td>
            <td>${player.userAttended.genderTextual}</td>
            <td>${player.payedTextual}</td>
            <td>
                <button type="submit" class="printButton" formaction="/admin/game/logout/${gameId}/${player.userAttended.id}">Odhlásit</button>
            </td>
            <td>
                <c:if test="${not player.automatic}">
                    <c:choose>
                        <c:when test="${not empty player.payed and player.payed}">
                            <button type="submit" class="printButton" formaction="/admin/user/payed/${gameId}/${player.userAttended.id}">Zrušit zaplacení</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="printButton" formaction="/admin/user/payed/${gameId}/${player.userAttended.id}">Zaplatil</button>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </td>
            <td>${player.variableSymbol}</td>
        </tr>
    </c:forEach>
    <c:if test="${not empty requestScope.substitutes}">
        <tr><td><b>Náhradníci:</b></td></tr>
        <c:forEach items="${requestScope.substitutes}" var="substitute">
            <tr>
                <td>${substitute.userAttended.userName}</td>
                <td>${substitute.userAttended.name} ${substitute.userAttended.lastName}</td>
                <td>${substitute.userAttended.phone}</td>
                <td>${substitute.userAttended.email}</td>
                <td>${substitute.userAttended.genderTextual}</td>
                <td>${substitute.payedTextual}</td>
                <td>
                    <button type="submit" class="printButton" formaction="/admin/game/logout/${gameId}/${substitute.userAttended.id}">Odhlásit</button>
                </td>
                <td>
                    <c:if test="${not substitute.automatic}">
                        <c:choose>
                            <c:when test="${not empty substitute.payed and substitute.payed}">
                                <button type="submit" class="printButton" formaction="/admin/user/payed/${gameId}/${substitute.userAttended.id}">Zrušit zaplacení</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="printButton" formaction="/admin/user/payed/${gameId}/${substitute.userAttended.id}">Zaplatil</button>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </td>
                <td>${player.variableSymbol}</td>
            </tr>
        </c:forEach>
    </c:if>
</table>
    <div>
        <c:if test="${not paymentFinished}">
            <button type="submit" class="printButton" formaction="/admin/game/finished/${gameId}">Uznat zaplacení</button>
        </c:if>
    </div>
</form>