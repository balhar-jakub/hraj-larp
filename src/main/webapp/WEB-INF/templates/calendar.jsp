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
                    <h3><a href="http://www.vampire.cz/prague-by-larp/" tabindex="-1" target="_blank">Prague By Larp</a>
                    </h3>
                    <div class="grid4">
                        <div>
                            <p>Prague By Larp je reinkarnací Pražského larpvíkendu, tedy festivalem komorních larpů,
                                které Vám Prague by Night sesypalo na jednu hromadu.</p>

                            <p>Pražský larpvíkend vznikl již před řádkou let a za přispění mnoha tvůrců z larpové
                                komunity Vám přináší spoustu neopakovatelných zážitků.</p>

                            <p>Ať už jste zkušení hráči nebo jste larp zatím nehráli, přijďte ztvárnit zajímavou roli a
                                prožít jedinečné příběhy.</p>

                            <p>Letos uvádíme: Prom, Equilibrium, Cesta za snem, Kvarteto, Jiní, Čí sny sníš, Stezky
                                šamanů, Hra, Iure, Konečná, vystupovat!, Never let me go, Figurky, Buřti</p>

                            <p>Přihlašování se spustí na stránkách festivalu v sobotu 25.3.2017, VIP vstupenky si můžete
                                rezervovat už nyní. Více informací najdete na webu <a
                                        href="http://www.vampire.cz/prague-by-larp/" tabindex="-1" target="_blank">praguebylarp.cz</a>
                            </p>
                        </div>
                    </div>
                    <div class="grid2 square1">
                        <a href="http://www.vampire.cz/prague-by-larp/" tabindex="-1" target="_blank">
                            <img src="/img/PragueByLarp.jpg" alt="Prague By Larp"/>
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