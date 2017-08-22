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
                <div class="termin clearfix">
                    <h3><a href="http://zizcon.cz/" tabindex="-1" target="_blank">ŽižCon 2017 (14.9.2017 – 17.9.2017)</a>
                    </h3>
                    <div class="grid4">
                        <div>
                            <p>Zveme Vás na každoroční zářijový festival rolových a deskových her, který se koná v netradičních prostorách jediného kostela Československé církve husitské na Žižkově. Komorní charakter a filosofie „udělej si sám“ jej odlišuje od jiných akcí tohoto druhu, což jí dává unikátní atmosféru. Návštěvníky čeká bohatý program plný rozličných aktivit a přednášek. ŽižCon je zážitek pro všechny milovníky netradičních akcí, deskových her a pro každého kdo si rád rozšíří obzory. Výtěžek festivalu jde na podporu Komunitního centra na Žižkově a jeho charitativních aktivit jako například vedení nízkoprahového klubu pro děti a mladistvé, nebo pomoc lidem bez domova. Věříme, že tento fakt dává festivalu další rozměr, který komerční akce obdobné typu nemívají, tak přijďte hrát za dobrou věc!</p>

                            <p>Na aktivity se můžete přihlašovat až po úspěšné rezervaci vstupenky a zaplacení její ceny. Více viz <a href="http://zizcon.cz/registration">http://zizcon.cz/registration</a>. Následně obdržíte přihlašovací údaje do systému a můžete se zapsat na vybrané hry. V případě překročení limitu účastníků budete evidováni jako náhradníci. Celkový program si můžete prohlédnout zde <a href="http://zizcon.cz/program/table">http://zizcon.cz/program/table</a>.</p>

                            <p>Akci můžete sledovat také na <a href="https://www.facebook.com/events/481150225562105/?fref=ts">Facebooku</a> (https://www.facebook.com/events/481150225562105/?fref=ts)</p>
                        </div>
                    </div>
                    <div class="grid2 square1">
                        <a href="http://zizcon.cz/" tabindex="-1" target="_blank">
                            <img src="/img/zizcon2017.jpg" alt="Žižcon"/>
                        </a>
                    </div>
                </div>

                <c:if test="${not empty requestScope.futureGames}">
                    <c:forEach items="${requestScope.futureGames}" var="game">
                        <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}
                        </h2></div>
                        <div class="termin clearfix">
                            <h3><a href="/game/detail?gameId=${game.id}" tabindex="-1">${game.name}</a></h3>
                            <div class="grid4">
                                <div>
                                    <p>${game.anotation}</p>
                                    <a href="/game/detail?gameId=${game.id}" class="biglink">podrobnosti o termínu &amp;
                                        přihlášení</a>
                                </div>
                            </div>
                            <div class="grid2 square1">
                                <a href="/game/detail?gameId=${game.id}" tabindex="-1"><img src="${game.image}"
                                                                                            alt="${game.name}"/></a>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>

        <div class="tab-pane" id="tab2">
            <c:if test="${not empty requestScope.formerGames}">
                <c:forEach items="${requestScope.formerGames}" var="game">
                    <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2>
                    </div>
                    <div class="termin clearfix">
                        <h3><a href="/game/detail?gameId=${game.id}" tabindex="-1">${game.name}</a></h3>
                        <div class="grid4">
                            <div>
                                <p>${game.anotation}</p>
                                <a href="/game/detail?gameId=${game.id}" class="biglink">podrobnosti o termínu &amp;
                                    přihlášení</a>
                            </div>
                        </div>
                        <div class="grid2 square1">
                            <a href="/game/detail?gameId=${game.id}" tabindex="-1"><img src="${game.image}"
                                                                                        alt="${game.name}"/></a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>

        <div class="tab-pane" id="tab3">
            <c:if test="${not empty requestScope.availableGames}">
                <c:forEach items="${requestScope.availableGames}" var="game">
                    <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2>
                    </div>
                    <div class="termin clearfix">
                        <h3><a href="/game/detail?gameId=${game.id}" tabindex="-1">${game.name}</a></h3>
                        <div class="grid4">
                            <div>
                                <p>${game.anotation}</p>
                                <a href="/game/detail?gameId=${game.id}" class="biglink">podrobnosti o termínu &amp;
                                    přihlášení</a>
                            </div>
                        </div>
                        <div class="grid2 square1">
                            <a href="/game/detail?gameId=${game.id}" tabindex="-1"><img src="${game.image}"
                                                                                        alt="${game.name}"/></a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty requestScope.availableGames}">
                <c:if test="${empty requestScope.isLogged}">
                    <p>Nepřihlášený uživatel nemá možnost přihlásit se na žádnou hru.
                        Tato záložka po přihlášení vypisuje všechny hry, na kterých uživatel zatím není přihlášen a je
                        na nich volné místo.</p>
                </c:if>
                <c:if test="${not empty requestScope.isLogged}">
                    <p>V tuto chvíli pro Vás žádná další volná hra k dispozici není,
                        ale stále je možné přihlásit se jako náhradník na <a href="/kalendar/">nadcházející termíny</a>.
                    </p>
                </c:if>
            </c:if>
        </div>
    </div>
</div>