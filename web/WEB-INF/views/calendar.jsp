<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Matheo
  Date: 13.3.13
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Simple jsp page</title></head>
<body>

    <%-- navigation panel --%>
    <c:import url="/WEB-INF/templates/nav.jsp" />


<div class="band white clearfix">

<div class="text">
    <h1>Kalendář larpů</h1>
</div>
<ul class="nav nav-tabs">
    <li class="active"><a href="#tab1" data-toggle="tab">Nadcházející termíny</a></li>
    <li><a href="#tab2" data-toggle="tab">Minulé termíny</a></li>
</ul>
<div class="tab-content">
<div class="tab-pane active" id="tab1">
<div class="text page-kalendar">

    <c:forEach items="requestScope.futureGames" var="game">
         <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2></div>

        <div class="termin clearfix">
            <h3><a href="/game/detail?id='${game.id}'" tabindex="-1">${game.name}</a></h3>
            <div class="grid4">
                <div>
                    <p>${game.info}</p>
                    <a href="/game/detail?id='${game.id}'" class="biglink">podrobnosti o termínu &amp; přihlášení</a>
                </div>
            </div>
            <div class="grid2 square1">
                <a href="/game/detail?id='${game.id}'" tabindex="-1"><img src="${game.image}" alt="${game.name}"/></a>
            </div>
        </div>
    </c:forEach>



        <div class="clearfix den"><h2 class="datum"><span>13.3.</span> středa</h2></div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/30/" tabindex="-1">Terapie tmou</a></h3>
            <div class="grid4"><div>
                <p>Hra je volně inspirovaná skutečnou terapií tmou, kdy pacient tráví týden o samotě potmě, pro účely larpu se ale jedná o krátkodobou a hromadnou terapii, kterou poskytuje komerčně zaměřené terapeutické středisko.</p>                <a href="http://hrajlarp.cz/termin/30/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/30/" tabindex="-1"><img src="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcScH5eI4aJlnIXIDv8w8EqW5BlI0EYlOnSZ2ODZ5b2AgQd6WJ3P" alt="ilustrační obrázek"/></a>
            </div>
        </div>

        <div class="clearfix den"><h2 class="datum"><span>24.3.</span> neděle</h2></div>


        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/31/" tabindex="-1">Perseus</a></h3>
            <div class="grid4"><div>
                <p>Perseus je oddechový, výhradně mužský larp plný vtipu a nadsázky. Jde o hru o vztazích, všedních problémech na vesnici a hlavně o fotbale.</p>                <a href="http://hrajlarp.cz/termin/31/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/31/" tabindex="-1"><img src="http://fk-perseus.borec.cz/perseus.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>24.3.</span> neděle</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/32/" tabindex="-1">Perseus - ženský běh</a></h3>
            <div class="grid4"><div>
                <p>Perseus je oddechový, výhradně mužský larp plný vtipu a nadsázky. Jde o hru o vztazích, všedních problémech na vesnici a hlavně o fotbale, který si v tomto případě mohou vyzkoušet ženy!</p>                <a href="http://hrajlarp.cz/termin/32/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/32/" tabindex="-1"><img src="http://fk-perseus.borec.cz/perseus.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>26.3.</span> úterý</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/33/" tabindex="-1">Sen noci svatojánské</a></h3>
            <div class="grid4"><div>
                <p>Blíží se nejkratší noc roku a v Athénách se chystá velká slavnost. Vévoda Theseus se bude ženit. Jeho nastávající je krásná Hippolyta, královna Amazonek. Svatební slavnost bude trvat po tři dny a dvě noci a bude na ní každý, kdo v Athénách něco znamená. Přítomen však bude i král elfů Oberon se svou chotí Titanií a jejich družinou. Slavnost bude jistě velkolepá a unikátní. Přeci na ní nechcete chybět?</p>                <a href="http://hrajlarp.cz/termin/33/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/33/" tabindex="-1"><img src="http://larpovadatabaze.cz/img/games/141ebd54a149e0372d6c824b4a5000f2.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>30.3.</span> sobota</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/34/" tabindex="-1">I speak only da TRUTH</a></h3>
            <div class="grid4"><div>
                <p>Přístavní čtvrť je nejtvrdší místo v celém New Orleans. Bylo tomu tak i před hurikánem, ale po něm zmizela třetina obyvatel a život v New Orleans je ještě nebezpečnější než dříve.</p>

<p>Propojení organizovaného zločinu a špiček policie už kritizují dokonce i někteří borci z ghetta. Vraždy, drogy a ilegálové tlačí obyčejné lidi do rohu. Říká se, že New Orleans už pomůže jenom zázrak.</p>
                <a href="http://hrajlarp.cz/termin/34/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/34/" tabindex="-1"><img src="http://hraj.larp.cz/download/image.picture.92c632d877ee7653.54525554482e6a7067.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>3.4.</span> středa</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/35/" tabindex="-1">Derniera</a></h3>
            <div class="grid4"><div>
                <p>Derniéra je krátký komorní larp z divadelního prostředí plného pověr a tabu. Hráči v tomto larpu ztvární dvojí roli — jednoho z herců improvizační skupiny Naruby, ale také jednu z postav divadelní hry Abrakadabra.</p>                <a href="http://hrajlarp.cz/termin/35/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/35/" tabindex="-1"><img src="http://www.praguebynight.eu/lv/img/derniera_logo.png" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>7.4.</span> neděle</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/36/" tabindex="-1">Cesta dlouhým dnem do noci</a></h3>
            <div class="grid4"><div>
                <p>Další večer. Znovu padla mlha. Znovu ta strašná mlhová siréna. Znovu se všichni opijí. A všichni budete sami. Zoufalí. A v rodinném kruhu.
Další den, co spěje dlouhou cestou do noci. Aby byl následován dalším, stejně bezútěšným.</p>                <a href="http://hrajlarp.cz/termin/36/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/36/" tabindex="-1"><img src="http://www.praguebynight.eu/lv/img/cesta.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>

</div>
</div>
<div class="tab-pane" id="tab2">
            <div class="clearfix den"><h2 class="datum"><span>7.3.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/29/" tabindex="-1">Sanatorium</a></h3>
            <div class="grid4"><div>
                <p>V sanatoriu profesora Levina na uzavřeném oddělení se setkává šest pacientů. Kdo je šílený? Kdo ne? A může mít vlastně šílenec pravdu? Každá z postav bude v průběhu příběhu konfrontována s realitou, která pro ni vůbec nemusí být příznivá.</p>                <a href="http://hrajlarp.cz/termin/29/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/29/" tabindex="-1"><img src="http://minifestival.zelenelarpy.cz/images/sanatorium.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>3.3.</span> neděle</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/28/" tabindex="-1">Telenovela</a></h3>
            <div class="grid4"><div>
                <p>Láska. Nenávist. Štěstí. Strach. Přátelství. Závist. A láska. Nejlepší venezuelská telenovela všech dob nese název Dny našich životů. Je plná emocí a zvratů. V centru dění se nachází dvě znesvářené rodiny - Lanzovi a Pereirovi. Příběhy jejich členů se již po desetiletí splétají a vytvářejí situace, které tak dobře známe z vlastních životů. Jen větší a lepší. Vraždy, osudové lásky, nevěry, nevlastní děti a šťastné náhody. Dny našich životů jsou i dny vašich životů! </p>                <a href="http://hrajlarp.cz/termin/28/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/28/" tabindex="-1"><img src="" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>21.2.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/27/" tabindex="-1">Zrcadlová místnost</a></h3>
            <div class="grid4"><div>
                <p>Během okamžiku dezorientace jste všichni přemístěni do místnosti bez dveří a oken, zato plné zrcadel. V místnosti je dostatek jídla, pití i dalších nezbytností. Vše naznačuje, že si zde nějakou dobu pobudete.</p>                <a href="http://hrajlarp.cz/termin/27/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/27/" tabindex="-1"><img src="http://www.pilirionos.org/_/rsrc/1333296501633/projekty/probehle-akce/zrcadlova-mistnost/zrcadloLogo.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>15.2.</span> pátek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/24/" tabindex="-1">Never let me go</a></h3>
            <div class="grid4"><div>
                <i><p>Píše se rok 1994 a díky průlomu v medicíně dosáhl průměrný věk člověka 115 let.</p></i>
<p></p>
<p>Komorní larp dle stejnojmenné knihy Kazua Ishigura od tvůrčí dvojice Petr Peldric Urban a Madla Urbanová. Pro jeho hraní naprosto není nutné znát knižní předlohu ani film</p>                <a href="http://hrajlarp.cz/termin/24/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/24/" tabindex="-1"><img src="http://www.yourkloset.com/wp-content/uploads/2011/11/neverletmego.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>4.2.</span> pondělí</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/25/" tabindex="-1">Moon</a></h3>
            <div class="grid4"><div>
                <p>Moon je krátký komorní LARP vycházející volně z prostředí světa známého sci-fi seriálu Firefly. Staví na propracovaných příbězích každé z postav a důležitých rozhodnutích, která musí učinit za ostatní obyvatele osady. Věříme, že hra hráčům poskytne materiál k zamyšlení a hledání odpovědí na různé otázky a že se stane víc než "jenom" zábavou.</p>                <a href="http://hrajlarp.cz/termin/25/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/25/" tabindex="-1"><img src="http://choutkovakristyna.sweb.cz/viewer.png" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>31.1.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/26/" tabindex="-1">Sanatorium</a></h3>
            <div class="grid4"><div>
                <p>V sanatoriu profesora Levina na uzavřeném oddělení se setkává šest pacientů. Kdo je šílený? Kdo ne? A může mít vlastně šílenec pravdu? Každá z postav bude v průběhu příběhu konfrontována s realitou, která pro ni vůbec nemusí být příznivá.</p>                <a href="http://hrajlarp.cz/termin/26/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/26/" tabindex="-1"><img src="http://minifestival.zelenelarpy.cz/images/sanatorium.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>23.1.</span> středa</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/23/" tabindex="-1">Cesta dlouhým dnem do noci</a></h3>
            <div class="grid4"><div>
                <p>Další večer. Znovu padla mlha. Znovu ta strašná mlhová siréna. Znovu se všichni opijí. A všichni budete sami. Zoufalí. A v rodinném kruhu.
Další den, co spěje dlouhou cestou do noci. Aby byl následován dalším, stejně bezútěšným.</p>                <a href="http://hrajlarp.cz/termin/23/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/23/" tabindex="-1"><img src="http://www.praguebynight.eu/lv/img/cesta.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>14.1.</span> pondělí</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/22/" tabindex="-1">Derniera</a></h3>
            <div class="grid4"><div>
                <p>Derniéra je krátký komorní larp z divadelního prostředí plného pověr a tabu. Hráči v tomto larpu ztvární dvojí roli — jednoho z herců improvizační skupiny Naruby, ale také jednu z postav divadelní hry Abrakadabra.</p>                <a href="http://hrajlarp.cz/termin/22/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/22/" tabindex="-1"><img src="http://www.praguebynight.eu/lv/img/derniera_logo.png" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>10.1.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/21/" tabindex="-1">Vlna</a></h3>
            <div class="grid4"><div>
                <p>Komorní LARP Vlna se odehrává na pozadí postapokalyptické totalitní společnosti roku 2051. Hráči během LARPu ztvární členy odbojového hnutí, známého jako Vlna, kteří se dostali do situace, kdy jejich rozhodnutí může ovlivnit budoucnost lidstva. Do situace, která vyzkouší jejich přesvědčení i ochotu obětovat nejen sebe samé.</p>                <a href="http://hrajlarp.cz/termin/21/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/21/" tabindex="-1"><img src="http://img707.imageshack.us/img707/8013/vlnaq.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>15.12.</span> sobota</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/15/" tabindex="-1">I speak only da TRUTH</a></h3>
            <div class="grid4"><div>
                <p>Přístavní čtvrť je nejtvrdší místo v celém New Orleans. Bylo tomu tak i před hurikánem, ale po něm zmizela třetina obyvatel a život v New Orleans je ještě nebezpečnější než dříve.</p>

<p>Propojení organizovaného zločinu a špiček policie už kritizují dokonce i někteří borci z ghetta. Vraždy, drogy a ilegálové tlačí obyčejné lidi do rohu. Říká se, že New Orleans už pomůže jenom zázrak.</p>                <a href="http://hrajlarp.cz/termin/15/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/15/" tabindex="-1"><img src="http://hraj.larp.cz/download/image.picture.92c632d877ee7653.54525554482e6a7067.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>8.12.</span> sobota</h2>
                            <div class="akce"><span>Court of Moravia</span>tematický víkend</div>
                        </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/16/" tabindex="-1">Rodinná oslava</a></h3>
            <div class="grid4"><div>
                <p>Larp o malých mezilidských bolístkách i hlubokých šrámech na duši. Larp příliš reálný na to, aby byl zábavný.</p>                <a href="http://hrajlarp.cz/termin/16/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/16/" tabindex="-1"><img src="http://www.courtofmoravia.com/Img/oslava-znacka.png" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>8.12.</span> sobota</h2>
                            <div class="akce"><span>Court of Moravia</span> tematický víkend</div>
                        </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/17/" tabindex="-1">Klub sebevrahů</a></h3>
            <div class="grid4"><div>
                <p>Členství v tomto uzavřeném klubu je výhradně doživotní. Larp o dekadenci a ceně lidského života.</p>                <a href="http://hrajlarp.cz/termin/17/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/17/" tabindex="-1"><img src="http://www.courtofmoravia.com/Img/klub_sebevrahu-znacka.png" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>8.12.</span> sobota</h2>
                            <div class="akce"><span>Court of Moravia</span>tematický víkend</div>
                        </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/18/" tabindex="-1">Kořist</a></h3>
            <div class="grid4"><div>
                <p>Drsný larp plný černého humoru a referencí na filmy Quentina Tarantina.</p>                <a href="http://hrajlarp.cz/termin/18/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/18/" tabindex="-1"><img src="http://www.courtofmoravia.com/Img/korist-small.png" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>8.12.</span> sobota</h2>
                            <div class="akce"><span>Court of Moravia</span>tematický víkend</div>
                        </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/19/" tabindex="-1">Sestup</a></h3>
            <div class="grid4"><div>
                <p>Argumentační larp o vypjatých konfliktech na palubě miniponorky.</p>                <a href="http://hrajlarp.cz/termin/19/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/19/" tabindex="-1"><img src="http://www.courtofmoravia.com/Img/sestup-znacka.gif" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>6.12.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/14/" tabindex="-1">Hra</a></h3>
            <div class="grid4"><div>
                <p>Vážená paní / Vážený pane, přijměte, prosím, laskavé pozvání na jeden mimořádný večírek s mimořádnými hosty. Věřím, že na tento večírek budete vzpomínat do konce Vašeho života. Rozhodnete-li se moji nabídku přijmout, dostavte se, prosím, na adresu 1727 Salisbury Street, St. Louis dne...</p>                <a href="http://hrajlarp.cz/termin/14/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/14/" tabindex="-1"><img src="http://choutkovakristyna.sweb.cz/hra-logo.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>29.11.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/13/" tabindex="-1">Odznak</a></h3>
            <div class="grid4"><div>
                <p>Zvláštní zásahová jednotka mající na starost boj s organizovaným zločinem v losangelském Farmingtonu přišla o jednoho ze svých členů. Staňte se jedním z policistů, kteří by také dost dobře mohli být postavami kriminálních seriálů. Odznak je hra vystavěná na konverzaci, napětí a atmosféře.</p>                <a href="http://hrajlarp.cz/termin/13/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/13/" tabindex="-1"><img src="../../temp/img/larp/odznak.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>20.11.</span> úterý</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/12/" tabindex="-1">Bodlák</a></h3>
            <div class="grid4"><div>
                <p>Hra se snaží pracovat jak s lidskými emocemi, tak s uměleckými prvky, přičemž prvkem nejvýraznějším je jednoznačně absurdnost. Ta se projevuje především v neurčenosti prostředí a času, kdy se děj odehrává, ale objevuje se také v některých událostech během hry.</p>                <a href="http://hrajlarp.cz/termin/12/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/12/" tabindex="-1"><img src="http://img824.imageshack.us/img824/5286/bodlaklogo.png" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>14.11.</span> středa</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/11/" tabindex="-1">Cesta dlouhým dnem do noci</a></h3>
            <div class="grid4"><div>
                <p>Další večer. Znovu padla mlha. Znovu ta strašná mlhová siréna. Znovu se všichni opijí. A všichni budete sami. Zoufalí. A v rodinném kruhu.
Další den, co spěje dlouhou cestou do noci. Aby byl následován dalším, stejně bezútěšným.</p>                <a href="http://hrajlarp.cz/termin/11/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/11/" tabindex="-1"><img src="http://www.praguebynight.eu/lv/img/cesta.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>8.11.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/20/" tabindex="-1">Blok D</a></h3>
            <div class="grid4"><div>
                <p>Vlak konečně začal zpomalovat, bylo slyšet pískání brzd, až nakonec s trhnutím zastavil úplně.Kdyby vagón nebyl tak strašně přeplněný, mnozí lidé by určitě popadali na zem. Takhle se jen ještě víc natlačili na své sousedy a ozvalo se několik přidušených vyjeknutí. Zvenku byly slyšet přibližující se rychlé kroky, štěkot psů a několik strohých příkazů v němčině. Pak se dveře vagónu prudce otevřely. Muž v šedomodré uniformě vás vyhnal na nástupiště. Stáli jste namačkaní jeden vedle druhého, mžourali do prudkého slunečního světla. Někteří z vás se třásli pod náporem chladného větru, který s sebou přinášel i podivný, nepříjemný zápach. Pak další uniformovaní muži vepředu zaveleli a celý ten zástup lidí se pomalu pohnul kupředu. Klopýtali jste podél koleje. Pak, při pohledu vzhůru, jste spatřili váš cíl. Před vámi se tyčila masivní železná brána, vysoký plot z ostnatého drátu se strážní věží. A za ním řady dřevených baráků mezi kterými apaticky postávaly hubené postavy, všechny oblečené ve stejném pruhovaném mundůru…</p>                <a href="http://hrajlarp.cz/termin/20/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/20/" tabindex="-1"><img src="http://img40.imageshack.us/img40/9679/logoblokd.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>1.11.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/10/" tabindex="-1">Vlna</a></h3>
            <div class="grid4"><div>
                <p>Komorní LARP Vlna se odehrává na pozadí postapokalyptické totalitní společnosti roku 2051. Hráči během LARPu ztvární členy odbojového hnutí, známého jako Vlna, kteří se dostali do situace, kdy jejich rozhodnutí může ovlivnit budoucnost lidstva. Do situace, která vyzkouší jejich přesvědčení i ochotu obětovat nejen sebe samé.</p>                <a href="http://hrajlarp.cz/termin/10/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/10/" tabindex="-1"><img src="http://img707.imageshack.us/img707/8013/vlnaq.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>23.10.</span> úterý</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/9/" tabindex="-1">Moon</a></h3>
            <div class="grid4"><div>
                <p>Moon je krátký komorní LARP vycházející volně z prostředí světa známého sci-fi seriálu Firefly. Staví na propracovaných příbězích každé z postav a důležitých rozhodnutích, která musí učinit za ostatní obyvatele osady. Věříme, že hra hráčům poskytne materiál k zamyšlení a hledání odpovědí na různé otázky a že se stane víc než "jenom" zábavou.</p>                <a href="http://hrajlarp.cz/termin/9/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/9/" tabindex="-1"><img src="" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>18.10.</span> čtvrtek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/2/" tabindex="-1">Síla bratrství</a></h3>
            <div class="grid4"><div>
                <p>Fischerovi. Před první světovou válkou to byla vzorná a pohodová rodina. Byli to lidé s ideály a sny. Ovšem válka zničila jejich lásku a vzájemné dobré vztahy. Všichni, co zůstali, se teď snaží vrátit vše zpět. Ale zjišťují, že to nejde.</p>                <a href="http://hrajlarp.cz/termin/2/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/2/" tabindex="-1"><img src="http://hrajlarp.cz/temp/img/larp/bratrstvi_full.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>13.10.</span> sobota</h2>
                            <div class="akce"><span>Halahoj</span>tematický víkend</div>
                        </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/5/" tabindex="-1">Pryč od minulosti</a></h3>
            <div class="grid4"><div>
                <p>Pryč od minulosti čerpá z poetiky detektivek drsné školy 40.-50. let. V duchu nejlepších filmů noir se nese celá hra. Ve zšeřelé atmosféře baru American se vznáší erotické napětí i zpola zapomenuté hříchy předchozího života. Nechybí svůdná famme fatale, detektiv s břitkým jazykem ani zestárlá filmová hvězda.</p>                <a href="http://hrajlarp.cz/termin/5/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/5/" tabindex="-1"><img src="../../temp/img/larp/minulost.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>13.10.</span> sobota</h2>
                            <div class="akce"><span>Halahoj</span>tematický víkend</div>
                        </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/6/" tabindex="-1">Sen o můze</a></h3>
            <div class="grid4"><div>
                <p>Peníze? Moc? Sláva? Jsou to jenom prázdná slova, nebo je to odměna za napsání bestselleru? Nebo je to daň? A může být daní i svědomí, které vypovídá o tom, jak se kniha vlastně psala? Co když se realita a představy prolínají daleko víc, než se může by komu přišlo možné? Třeba až natolik, že nikdo nebude moct říct, co je realita a co je sen. Pak už jen sestoupí múzy ke spisovatelům, kteří je vyždímají na papír, upíšou svou duši knihám a pak zemřou v zapomnění času. Nebo ne?</p>                <a href="http://hrajlarp.cz/termin/6/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/6/" tabindex="-1"><img src="../../temp/img/larp/muza.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>13.10.</span> sobota</h2>
                            <div class="akce"><span>Halahoj</span>tematický víkend</div>
                        </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/7/" tabindex="-1">Hranice</a></h3>
            <div class="grid4"><div>
                <p>„Lidi se nemění. Aspoň ne natolik, aby to stálo za řeč…“ -Bela Lieberman</p>
			<p>Sudetská vesnice zaklíněná mezi vrcholky Rychlebských hor. Zdálo by se, že čas na ni zcela zapomněl, ale i sem pronikají ozvuky doby, disharmonické akordy velkých změn. Ale co lidé? Jsou pořád stejní? Ohýbá je doba nebo si vlastní hranici pod sebou podpalujeme vždy sami?</p>                <a href="http://hrajlarp.cz/termin/7/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/7/" tabindex="-1"><img src="../../temp/img/larp/hranice.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>12.10.</span> pátek</h2>
                    </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/3/" tabindex="-1">Svět po pádu</a></h3>
            <div class="grid4"><div>
                <p>Svět po Pádu je zničený a velmi nehostinný svět, ve kterém žijí zbytky zdegenerovaného lidstva. To si svou dřívější vyspělost připomíná už jen díky zničeným a často nefunkčním pozůstatkům z dob před Pádem. Čtveřice kladenských vojáků se vydává ku Praze, aby tam našla svého přítele. Muže, ve kterém vidí naději, že Kladno neskončí v úpadku.</p>                <a href="http://hrajlarp.cz/termin/3/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>

            </div></div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/3/" tabindex="-1"><img src="../../temp/img/larp/pad.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
                <div class="clearfix den"><h2 class="datum"><span>12.10.</span> pátek</h2>
                            <div class="akce"><span>Halahoj</span>tematický víkend</div>
                        </div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/4/" tabindex="-1">Lunapark život</a></h3>
            <div class="grid4">
                <div>
                <p>Lunapark život je příběhovým dramatem čerpajícím z prostředí francouzské avantgardy. Vypráví o rodinných tajemstvích a skrývaných touhách a aktualizuje prastará témata lásky a smrti.</p>                <a href="http://hrajlarp.cz/termin/4/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>
                </div>
            </div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/4/" tabindex="-1"><img src="../../temp/img/larp/lunapark_full.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
        <div class="clearfix den"><h2 class="datum"><span>7.10.</span> neděle</h2></div>

        <div class="termin clearfix">
            <h3><a href="http://hrajlarp.cz/termin/1/" tabindex="-1">Škola</a></h3>
            <div class="grid4">
                <div>
                <p>Vzpomínáš na šťastná a radostná léta na základní škole? První pusa, první cígo. Největším úspěchem roku bylo získání kartičky oblíbeného hokejisty. Nejčtenější literaturou fotoromán z&nbsp;Brávíčka. Poznámka v žákajdě se jevila jako neřešitelný problém. Chceš to prožít znovu? Hurá do Školy!</p>                <a href="http://hrajlarp.cz/termin/1/" class="biglink">podrobnosti o termínu &amp; přihlášení</a>
                </div>
            </div>
            <div class="grid2 square1">
                <a href="http://hrajlarp.cz/termin/1/" tabindex="-1"><img src="../../temp/img/larp/skola.jpg" alt="ilustrační obrázek"/></a>
            </div>
        </div>
        </div>
</div>

</div>


</body>
</html>