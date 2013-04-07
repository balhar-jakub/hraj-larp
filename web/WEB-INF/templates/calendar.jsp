<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="band white clearfix">

    <div class="text">
        <h1>Kalendář larpů</h1>
    </div>

  <c:if test="${isLogged}">
    <div class="text">
        <h1><a href="/game/add">Přidej hru</a></h1>
    </div>
  </c:if>

    <ul class="nav nav-tabs">
        <li class="active"><a class="tabAnchor" id="tab" href="#tab1" data-toggle="tab">Nadcházející termíny</a></li>
        <li><a class="tabAnchor" href="#tab2" data-toggle="tab">Minulé termíny</a></li>
        <li><a class="tabAnchor" href="#tab3" data-toggle="tab">Volné termíny</a></li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane active" id="tab1">
            <div class="text page-kalendar">
                <c:if test="${not empty requestScope.futureGames}">
                    <c:forEach items="${requestScope.futureGames}" var="game">
                        <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2></div>
                        <div class="termin clearfix">
                            <h3><a href="/game/detail?gameId=${game.id}" tabindex="-1">${game.name}</a></h3>
                            <div class="grid4">
                                <div>
                                    <p>${game.info}</p>
                                    <a href="/game/detail?gameId=${game.id}" class="biglink">podrobnosti o termínu &amp; přihlášení</a>
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
                                <a href="/game/detail?gameId=${game.id}" class="biglink">podrobnosti o termínu &amp; přihlášení</a>
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
            <c:if test="${not empty requestScope.availableGames}">
                <c:forEach items="${requestScope.availableGames}" var="game">
                    <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2></div>
                    <div class="termin clearfix">
                        <h3><a href="/game/detail?gameId=${game.id}" tabindex="-1">${game.name}</a></h3>
                        <div class="grid4">
                            <div>
                                <p>${game.info}</p>
                                <a href="/game/detail?gameId=${game.id}" class="biglink">podrobnosti o termínu &amp; přihlášení</a>
                            </div>
                        </div>
                        <div class="grid2 square1">
                            <a href="/game/detail?gameId=${game.id}" tabindex="-1"><img src="${game.image}" alt="${game.name}"/></a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty requestScope.availableGames}">
                <c:if test="${empty requestScope.isLogged}">
                    <p>Nepřihlášený uživatel nemá možnost přihlásit se na žádnou hru.
                        Tato záložka po přihlášení vypisuje všechny hry, na kterých uživatel zatím není přihlášen a je na nich volné místo.</p>
                </c:if>
                <c:if test="${not empty requestScope.isLogged}">
                    <p>V tuto chvíli pro Vás žádná další volná hra k dispozici není,
                    ale stále je možné přihlásit se jako náhradník na <a href="/kalendar/">nadcházející termíny</a>.</p>
                </c:if>
            </c:if>
        </div>
    </div>
</div>