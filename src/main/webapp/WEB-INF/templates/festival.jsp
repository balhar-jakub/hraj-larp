<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="text">
    <div class="grid4">
        <h1>Náš festival</h1>

        <p>Pilířem HRAJ LARP je festival komorních larpů, který od roku 2012 probíhá každou sezónu od podzimu do léta.
            Každý druhý měsíc jsou v rámci festivalu uvedeny čtyři komorní larpy.</p>

        <p>Kromě už zkušených hráčů bychom rádi motivovali zejména nováčky, aby odhodili strach a přišli si larp zahrát.
            Na
            hře se účastníci budou moci kdykoliv obrátit jak na autora larpu, tak na organizátora z řad HRAJ LARP, který
            bude na místě zajišťovat hladký průběh akce. Doufáme, že festival poskytne skvělou, a hlavně pravidelnou
            zábavu
            několika desítkám hráčů.</p>

        <h1>HRAJ LARP víkend</h1>

        <p>
            HRAJ LARP víkend pořádáme od 4. do 6. prosince.<br>
            Co to znamená?<br>
            Celkem 12 komorních her různých autorů i stylů, možnost přespání pro mimopražské účastníky a setkání s
            ostatními hráči na sobotní párty.
            Přečtěte si, <a href="/vikend">jak bude HRAJ LARP víkend probíhat</a> a <a href="/vikend/hry">vyberte si z našeho nabitého programu.</a>
        </p>

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
            číslo, které najdete v potvrzovacím mailu. Zda platba skutečně dorazila, si můžete sami ověřit na
            <a href="https://www.fio.cz/ib2/transparent?a=2600445512">online výpisu z
                účtu</a>. Upozorňujeme, že pokud se ze hry odhlásíte méně než 48 hodin před jejím začátkem, nemůžeme vám
            zaplacený vstupní poplatek vrátit.
        </p>

        <h2>Jak se přihlásit na hru?</h2>

        <p>Hry uvádíme každý sudý měsíc. Přihlašování začíná vždy prvního předchozího měsíce ve 20.00, a to na všechny
            hry, které budeme uvádět v měsíci následujícím.</p>

        <p>Za larpy, na které jste se přihlásili, je třeba zaplatit do desátého (příklad: za hry, které se konají v
            říjnu, je třeba poslat peníze do 10. září). Po tomto termínu došlé platby zkontrolujeme a hráče, od kterých
            nemáme peníze na účtu, odhlásíme. Pokud víte, že vaše platba nestihne dojít včas nebo máte nějaký jiný
            problém, napište nám e-mail.
            Na volná místa se samozřejmě lze přihlásit i později. V takovém případě s vámi budeme platbu řešit
            individuálně.</p>
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