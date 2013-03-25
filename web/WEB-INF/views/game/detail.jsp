<%--
  Created by IntelliJ IDEA.
  User: Matheo
  Date: 8.3.13
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">

    <title>${requestScope.game.name} na HRAJ LARP | ${requestScope.game.dateAsDMY}</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta property="og:title" content="${requestScope.game.name} na HRAJ LARP | ${requestScope.game.dateAsDMY}"/>
    <meta property="og:type" content="website"/>
    <meta property="og:url" content="http://hrajlarp.cz/game/detail?id=${requestScope.game.id}"/>         <!-- TODO change URL -->
    <meta property="og:description" content="Pojďte si zahrát larp ${requestScope.game.name} na festivalu komorních larpů HRAJ LARP!"/>
    <meta property="og:image" content="/img/ogimg-larp.jpg"/>

    <link rel='stylesheet' type='text/css'
          href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700&subset=latin,latin-ext'>

    <link href="/style/bootstrap.min.css" rel="stylesheet">
    <link href="/style/bootstrap-responsive.min.css" rel="stylesheet">

    <link href="/style/style.css" rel="stylesheet">
    <link href="/style/font.css" type="text/css" rel="stylesheet">

    <link rel="alternate" type="application/rss+xml" title="RSS Feed HRAJ LARP"
          href="http://www.facebook.com/feeds/page.php?format=rss20&id=247469131956626"/>
</head>
<body>
<%-- navigation panel --%>
<c:import url="/WEB-INF/templates/nav.jsp" />

<div class="band white clearfix">

  <div class="text">
    <h1>${requestScope.game.name} | ${requestScope.game.dateAsDMY}</h1>
  </div>

  <div class="page-termin">

    <div class="text left">
      <p>${requestScope.game.shortText}</p>
      <img src="${requestScope.game.image}" alt="ilustrační obrázek">
    </div>


    <div class="rightside">
      <div class="siderow clearfix">
        <div class="datum">
          <span>${requestScope.game.dateAsDM}</span>
          ${requestScope.game.dateAsDayName}
        </div>
        <div class="cas">
          <span>${requestScope.game.dateTime}</span>
        </div>
      </div>
      <div class="siderow center">
        <span class="info">
          <!-- If user is logged and date is correct button Přihlaš tady bude. -->
          <form method="post" action="http://hrajlarp.cz/attend/">
            <input type="hidden" name="gameId" value="${requestScope.game.id}">
            <input type="hidden" name="replace" value="1">
            <input type="submit" value="Přihlásit se jako náhradník.">
            ${requestScope.substitute}
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
              <td>${requestScope.game.menRole}</td>
              <td>${requestScope.game.womenRole}</td>
              <td>${requestScope.game.bothRole}</td>
            </tr>
            <tr>
              <td>z toho volných</td>
              <td>${requestScope.game.menFreeRoles}</td>
              <td>${requestScope.game.womenFreeRoles}</td>
              <td>${requestScope.game.bothFreeRoles}</td>
            </tr>
            <tr>
              <td>Zatím přihlášeno</td>
              <td>${requestScope.game.menAssignedRoles}</td>
              <td>${requestScope.game.womenAssignedRoles}</td>
              <td></td>
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
</body>
</html>