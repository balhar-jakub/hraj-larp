<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template' %>
<div class="text">
    <h1>${game.name} | ${game.dateAsDMY}</h1>
</div>

<div class="page-termin">

    <div class="text left">
        <p>${game.shortText}</p>
        <img src="${game.image}" alt="ilustrační obrázek">
    </div>


    <div class="rightside">
        <div class="siderow clearfix">
            <div class="datum">
                <span>${game.dateAsDM}</span>
                ${game.dateAsDayName}
            </div>
            <div class="cas">
                <span>${game.dateTime}</span>
            </div>
        </div>
        <div class="siderow center">
        <span class="info">
          <!-- If user is logged and date is correct button Přihlaš tady bude. -->
          <form method="post" action="http://hrajlarp.cz/attend/">
              <input type="hidden" name="gameId" value="${game.id}">
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
                    <td>${game.menRole}</td>
                    <td>${game.womenRole}</td>
                    <td>${game.bothRole}</td>
                </tr>
                <tr>
                    <td>z toho volných</td>
                    <td>${game.menRole}</td>
                    <td>${game.womenRole}</td>
                    <td>${game.bothRole}</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="siderow">
            <h4>Místo</h4>
            ${game.place}
        </div>
    </div>

    <div class="text left">
        <h2>O hře</h2>
        <p>${game.aboutGame}</p>

        <h3>Autor</h3>
        ${game.author}
    </div>
</div>