<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="text">
    <div class="grid4">
        <h1>Náš festival</h1>

        <p>Pilířem HRAJ LARP je festival komorních larpů, který v probíhá pravidelně každou sezónu (září až červen) již
            od roku 2012. Každý týden bude v rámci festivalu uveden jeden komorní larp.</p>

        <p>Kromě už zkušených hráčů bychom rádi motivovali zejména nováčky, aby odhodili strach a přišli si larp zahrát.
            Na
            hře se účastníci budou moci kdykoliv obrátit jak na autora larpu, tak na organizátora z řad HRAJ LARP, který
            bude na místě zajišťovat hladký průběh akce. Doufáme, že festival poskytne skvělou, a hlavně pravidelnou
            zábavu
            několika desítkám hráčů.</p>

        <a name="pokyny" id="pokyny"></a>

        <h2>Pokyny pro akce</h2>

        <p>Kdy a kde se hry konají, se dozvíte v <a href=../kalendar/>Kalendáři</a>, je-li na hru potřeba nějaký kostým
            a
            všechny další potřebné informace na detailu hry (kam se dostanete z Kalendáře).</p>

        <p>Na většině larpů uváděných v rámci festivalu HRAJ LARP je přítomen fotograf. Přihlášením na hru souhlasíte s
            tím,
            že <strong>fotografie s vámi mohou být použity k propagačním účelům</strong>. Pokud si z jakéhokoliv důvodu
            nepřejete být foceni, dejte nám o tom prosím předem vědět na náš mail hraj@larp.cz. Individuálně se
            domluvíme,
            že vás fotograf nebude fotit nebo ho na hru vůbec nebudeme zvát.</p>

        <p>Každou neděli ve 20.00 je spuštěno přihlašování na hry, které se budou konat v přespříštím týdnu. Vždy je tak
            přihlašování spuštěno více jak sedm dní.</p>

        <p>Hry uváděné v rámci HRAJ LARP si můžete zahrát za <strong>jednotnou cenu 150 Kč</strong>.

        <p>Účastnický poplatek plaťte před hrou převodem na transparentní účet <strong>2300302640/2010</strong> vedený u
            Fio
            banky. Do popisu platby uvádějte prosím své jméno a název hry, na kterou se hlásíte. Jako variabilní symbol
            zadejte
            číslo, které najdete na konci potvrzovacího emailu. Zda platba skutečně dorazila, si můžete sami ověřit na
            <a
                    href="https://www.fio.cz/scgi-bin/hermes/dz-transparent.cgi?ID_ucet=2300302640">online výpisu z
                účtu</a>.
        </p>
    </div>
    <c:if test="${futureGames}">
        <div class="text right grid1 square1">
            <h1>Nejbližší hry</h1>

            <c:forEach items="${games}" var="game">
                <div class="game termin">
                    <div class="datum">
                        <span>${game.dateAsDM}</span>
                            ${game.dateAsDayName}
                    </div>
                    <h3><a href="/game/detail?gameId=${game.id}">${game.name}</a></h3>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>