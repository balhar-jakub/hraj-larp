<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="band white clearfix">

    <div class="text">
        <h1>Administrace larpů</h1>
    </div>

    <c:if test="${requestScope.isAdmin}">
        <div class="text">
            <a href="/admin/game/actions"><h2>Správa akcí</h2></a>
        </div>
    </c:if>

    <ul class="nav nav-tabs">
        <li class="active"><a class="tabAnchor" id="tab" href="#tab1" data-toggle="tab">Nadcházející termíny</a></li>
        <li><a class="tabAnchor" href="#tab2" data-toggle="tab">Minulé termíny</a></li>
        <li><a class="tabAnchor <c:if test="${not empty requestScope.unvalidatedGames}">unfinished</c:if>" href="#tab3" data-toggle="tab">Neschválené hry</a></li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane active" id="tab1">
            <div class="text page-kalendar">
                <c:if test="${not empty requestScope.futureGames}">
                    <c:forEach items="${requestScope.futureGames}" var="game">
                        <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2></div>
                        <div class="termin clearfix">
                            <h3><a href="/game/edit?id=${game.id}" tabindex="-1">${game.name}</a></h3>
                            <div class="grid4">
                                <div>
                                    <p>${game.info}</p>
                                    <a href="/admin/game/players/${game.id}" class="biglink">Hráči</a>
                                    <input type="button" value="Smazat hru" onclick="location.href='/admin/game/confirmation/${game.id}'">
                                </div>
                            </div>
                        </div>
                        <div class="grid2 square1">
                            <a href="/game/detail?gameId=${game.id}" tabindex="-1"><img src="${game.image}" alt="${game.name}"/></a>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>

        <div class="tab-pane" id="tab2">
            <c:if test="${not empty requestScope.formerGames}">
                <c:forEach items="${requestScope.formerGames}" var="game">
                    <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2></div>
                    <div class="termin clearfix">
                        <h3><a href="/game/detail?gameId=${game.id}" tabindex="-1">${game.name}</a></h3>
                        <div class="grid4">
                            <div>
                                <p>${game.info}</p>
                                <a href="/admin/game/players/${game.id}" class="biglink">Hráči</a>
                            </div>
                        </div>
                        <div class="grid2 square1">
                            <a href="/game/detail?gameId=${game.id}" tabindex="-1"><img src="${game.image}" alt="${game.name}"/></a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

        <div class="tab-pane" id="tab3">
            <c:if test="${not empty requestScope.unvalidatedGames}">
                <c:forEach items="${requestScope.unvalidatedGames}" var="game">
                    <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2></div>
                    <div class="termin clearfix">
                        <h3><a href="/game/detail?gameId=${game.id}" tabindex="-1">${game.name}</a></h3>
                        <div class="grid4">
                            <div>
                                <p>${game.info}</p>
                                <a href="/admin/game/validate/${game.id}" class="biglink">Schválit</a>
                            </div>
                        </div>
                        <div class="grid2 square1">
                            <a href="/game/detail?gameId=${game.id}" tabindex="-1"><img src="${game.image}" alt="${game.name}"/></a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>