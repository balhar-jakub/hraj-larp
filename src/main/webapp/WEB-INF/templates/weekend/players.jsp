<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="text">
    <form method="post">
    <c:forEach items="${requestScope.participants}" var="attendance">
        <table width="100%">
            <tr>
                <td><h3>${attendance.player.userName}</h3></td>
                <td>${attendance.player.name} ${attendance.player.lastName}</td>
                <td>${attendance.player.phone}</td>
                <td>${attendance.player.email}</td>
                <td>${attendance.player.genderTextual}</td>
                <td></td>
            </tr>
            <c:forEach items="${attendance.attendedGames}" var="game">
            <tr>
                <td colspan><a href="/game/detail?gameId=${game.game.id}" tabindex="-1">${game.game.name}</a></td>
                <td>${game.dateToShow}</td>
                <td>
                    <button type="submit" class="printButton" formaction="/admin/game/logout/weekend/${game.game.id}/${attendance.player.id}">Odhlásit</button>
                </td>
                <td>
                    <c:if test="${not game.automatic}">
                        <c:choose>
                            <c:when test="${not empty game.payed and game.payed}">
                                <button type="submit" class="printButton" formaction="/admin/user/payed/weekend/${game.game.id}/${attendance.player.id}">Zrušit zaplacení</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="printButton" formaction="/admin/user/payed/weekend/${game.game.id}/${attendance.player.id}">Zaplatil</button>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </td>
                <td>
                    <c:if test="${game.replacement}">
                        Náhradník
                    </c:if>
                </td>
                <td>
                    ${game.vs}
                </td>
            </tr>
            </c:forEach>
        </table>
    </c:forEach>
    </form>
</div>