<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Matheo
  Date: 13.3.13
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">

    <title>Kalendář larpů | HRAJ LARP</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta property="og:title" content="Kalendář larpů | HRAJ LARP"/>
    <meta property="og:type" content="website"/>
    <meta property="og:url" content="http://hrajlarp.cz/calendar"/>      <!-- TODO change URL -->
    <meta property="og:description" content="Kalendář larpů | HRAJ LARP"/>
    <meta property="og:image" content="/img/ogimg-larp.jpg"/>

    <link rel='stylesheet' type='text/css'
          href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700&subset=latin,latin-ext'>

    <link href="/style/bootstrap.min.css" rel="stylesheet">
    <link href="/style/bootstrap-responsive.min.css" rel="stylesheet">

    <link href="/style/style.css" type="text/css" rel="stylesheet">
    <link href="/style/font.css" type="text/css" rel="stylesheet">

    <script src="/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="/js/content/calendar.js"></script>
</head>

<body>
    <%-- navigation panel --%>
    <c:import url="/WEB-INF/templates/nav.jsp" />


<div class="band white clearfix">

    <div class="text">
        <h1>Kalendář larpů</h1>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a class="tabAnchor" id="tab" href="#tab1" data-toggle="tab">Nadcházející termíny</a></li>
        <li><a class="tabAnchor" href="#tab2" data-toggle="tab">Minulé termíny</a></li>
    </ul>

    <div class="tab-content">
        <div class="tab-pane active" id="tab1">
            <div class="text page-kalendar">
                <c:if test="${not empty requestScope.futureGames}">
                    <c:forEach items="${requestScope.futureGames}" var="game">
                        <div class="clearfix den"><h2 class="datum"><span>${game.dateAsDM}</span>${game.dateAsDayName}</h2></div>
                            <div class="termin clearfix">
                                <h3><a href="/game/detail?id=${game.id}" tabindex="-1">${game.name}</a></h3>
                                <div class="grid4">
                                    <div>
                                        <p>${game.info}</p>
                                        <a href="/game/detail?id=${game.id}" class="biglink">podrobnosti o termínu &amp; přihlášení</a>
                                    </div>
                                </div>
                            </div>
                            <div class="grid2 square1">
                                <a href="/game/detail?id=${game.id}" tabindex="-1"><img src="${game.image}" alt="${game.name}"/></a>
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
                        <h3><a href="/game/detail?id=${game.id}" tabindex="-1">${game.name}</a></h3>
                        <div class="grid4">
                            <div>
                                <p>${game.info}</p>
                                <a href="/game/detail?id=${game.id}" class="biglink">podrobnosti o termínu &amp; přihlášení</a>
                            </div>
                        </div>
                        <div class="grid2 square1">
                            <a href="/game/detail?id=${game.id}" tabindex="-1"><img src="${game.image}" alt="${game.name}"/></a>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>