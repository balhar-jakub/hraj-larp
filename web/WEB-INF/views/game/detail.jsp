<%--
  Created by IntelliJ IDEA.
  User: Matheo
  Date: 8.3.13
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="d" uri="/WEB-INF/functions/date.tld" %>

<html>
<head>
    <title>Simple jsp page</title>

    <link href="/style/style.css" type="text/css" rel="stylesheet">
    <link href="/style/font.css" type="text/css" rel="stylesheet">
</head>
<body>
    <nav>
        <ul>
            <div class="band white clearfix">
                <li>
                    <a href="http://hrajlarp.cz/" class="logo"><span>Úvodní stránka</span>
                        <div class="grid1 square logo1"><img src="/img/logo1.png" width="100%" alt="logo"></div>
                        <div class="grid1 square logo2"><img src="/img/logo2.png" width="100%" alt="logo"></div>
                    </a>
                </li>
                <div class="grid2 square1 head1"></div>
                <div class="grid2 square1 head2">
                    <a href="http://facebook.com/hrajlarp" class="social" tabindex="-1"><i class="icon-facebook-sign"></i></a>
                    <a href="http://plus.google.com/108076251206659481852/" class="social" tabindex="-1"><i class="icon-google-plus-sign"></i></a>


                    <%-- place panel with login informations here --%>
                    <div>Balda</div>
                    <div><a href="http://hrajlarp.cz/ucast/14">Mé přihlášky</a> <a href="http://hrajlarp.cz/odhlaseni/">Odhlášení</a></div>
                    <div><a href="http://hrajlarp.cz/edituj-uzivatele">Editace údajů</a></div>
                </div>
            </div>
            <div class="band clearfix" id="menu">
                <div class="menu-segment" style="height: 140.4036464691162px;">
                    <em>Základy</em>
                    <li><a href="http://hrajlarp.cz/zaklady/"><span>Co je larp?</span></a></li>
                    <li><a href="http://hrajlarp.cz/zaklady/#jak-na-to"><span>Jak si zahrát?</span></a></li>
                </div><div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Náš festival</em>
                <li><a href="http://hrajlarp.cz/festival/"><span>Info &amp; pokyny</span></a></li>

                <!--<li><a href="#"><span>Fotogalerie</span></a></li>!-->
            </div><div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Kdy se hraje?</em>
                <li><a href="http://hrajlarp.cz/kalendar/"><span>Kalendář</span></a></li>
            </div><div class="menu-segment" style="height: 140.4036464691162px;">
                <em>O nás</em>
                <li><a href="http://hrajlarp.cz/o-projektu/"><span>O projektu</span></a></li>
                <li><a href="http://hrajlarp.cz/o-projektu/#kontakt"><span>Tým &amp; kontakt</span></a></li>
            </div><div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Larpy jinde</em>
                <li><a href="http://hrajlarp.cz/odkazy/"><span>Odkazy</span></a></li>
            </div>
            </div>
        </ul>
    </nav>
    <div class="band white clearfix">

        <div class="text">
            <h1>${requestScope.game.name} | ${d:dateAsYMD(requestScope.game.date)}</h1>
        </div>

        <div class="page-termin">

            <div class="text left">
                <p>${requestScope.game.shortText}</p>
                <img src="${requestScope.game.image}" alt="ilustrační obrázek">
            </div>


            <div class="rightside">
                <div class="siderow clearfix">
                   <div class="datum">
                       <span>${d:dateAsDM(requestScope.game.date)}</span>
                       ${d:dateAsdayName(requestScope.game.date)}
                   </div>
                   <div class="cas">
                       <span>${d:time(requestScope.game.date)}</span>
                   </div>
                </div>
                <div class="siderow center">
                <span class="info">
                    <!-- If user is logged and date is correct button Přihlaš tady bude. -->
                            <form method="post" action="http://hrajlarp.cz/attend/">
                                <input type="hidden" name="gameId" value="${requestScope.game.id}">
                                <input type="hidden" name="replace" value="1">
                                <input type="submit" value="Přihlásit se jako náhradník.">
                            </form>
                </span>
                </div>
                            <div class="siderow">
                    <table class="table">
                        <thead>
                        <tr>
                            <td><b>Role</b></td>
                            <td>mužské</td>
                            <td>ženské</td>
                            <td>nezáleží</td>
                        </tr>
                        </thead>
                        <tbody>
                        <!-- Get amount of free places and all places-->
                        <tr>
                            <td>počet míst</td>
                            <td>3</td>
                            <td>2</td>
                            <td>1</td>
                        </tr>
                        <tr>
                            <td>z toho volných</td>
                            <td>0</td>
                            <td>0</td>
                            <td>0</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                            <div class="siderow">
                                <h4>Místo</h4>
                                ${requestScope.game.place}
                            </div>


                        </div>


            <div class="text left">

                <h2>O hře</h2>
                <p>${requestScope.game.aboutGame}</p>

                <h3>Autor</h3>
                ${requestScope.game.author}
            </div>

        </div>


    </div>

    <script src="./Sanatorium na HRAJ LARP   7.3.2013_files/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../temp/js/libs/jquery-1.7.1.min.js"><\/script>')</script>
    <script>
        function equalHeight(group) {
            var tallest = 0;
            group.css("height", "auto");
            group.each(function () {
                var thisHeight = $(this).height();
                if (thisHeight > tallest) {
                    tallest = thisHeight;
                }
            });
            group.css("height", tallest);
        }

        $(document).ready(function () {
            equalHeight($(".menu-segment"));
            equalHeight($(".eh1"));
        });
        $(window).resize(function () {
            equalHeight($(".menu-segment"));
            equalHeight($(".eh1"));
        });
    </script>
    <script src="./Sanatorium na HRAJ LARP   7.3.2013_files/bootstrap.min.js"></script>
    <script>
        var _gaq = [
            ['_setAccount', 'UA-35090741-1'],
            ['_trackPageview']
        ];
        (function (d, t) {
            var g = d.createElement(t), s = d.getElementsByTagName(t)[0];
            g.src = ('https:' == location.protocol ? '//ssl' : '//www') + '.google-analytics.com/ga.js';
            s.parentNode.insertBefore(g, s)
        }(document, 'script'));
    </script>
</body>
</html>