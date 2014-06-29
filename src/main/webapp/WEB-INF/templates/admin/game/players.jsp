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
    </tr>
    <c:forEach items="${requestScope.players}" var="player">
        <tr>
            <td>${player.userName}</td>
            <td>${player.fullName}</td>
            <td>${player.phone}</td>
            <td>${player.email}</td>
            <td>${player.gender}</td>
            <td>${player.payedText}</td>
            <td>
                <button type="submit" class="printButton" formaction="/admin/game/logout/${gameId}/${player.id}">Odhlásit</button>
            </td>
            <td>
                <c:if test="${not player.automatic}">
                    <c:choose>
                        <c:when test="${player.payed}">
                            <button type="submit" class="printButton" formaction="/admin/user/payed/${gameId}/${player.id}">Zrušit zaplacení</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="printButton" formaction="/admin/user/payed/${gameId}/${player.id}">Zaplatil</button>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${not empty requestScope.substitutes}">
        <tr><td><b>Náhradníci:</b></td></tr>
        <c:forEach items="${requestScope.substitutes}" var="substitute">
            <tr>
                <td>${substitute.userName}</td>
                <td>${substitute.fullName}</td>
                <td>${substitute.phone}</td>
                <td>${substitute.email}</td>
                <td>${substitute.gender}</td>
                <td>${substitute.payedText}</td>
                <td>
                    <button type="submit" class="printButton" formaction="/admin/game/logout/${gameId}/${substitute.id}">Odhlásit</button>
                </td>
                <td>
                    <c:if test="${not substitute.automatic}">
                        <c:choose>
                            <c:when test="${substitute.payed}">
                                <button type="submit" class="printButton" formaction="/admin/user/payed/${gameId}/${substitute.id}">Zrušit zaplacení</button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="printButton" formaction="/admin/user/payed/${gameId}/${substitute.id}">Zaplatil</button>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </td>
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