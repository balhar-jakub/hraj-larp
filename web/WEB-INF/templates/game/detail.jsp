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
      <c:choose>
        <c:when test="${loggedInGame}">
            <c:choose>
                <c:when test="${substitute}">
             <span>
                 Jste přihlášen jako náhradník
             </span>
                </c:when>
                <c:otherwise>
             <span>
                 Jste přihlášen jako řádný účastník
             </span>
                </c:otherwise>
            </c:choose>
            <form method="post" action="logOutGame">
                <input type="hidden" name="gameId" value="${game.id}">
                <input type="submit" value="Odhlásit ze hry.">
            </form>
        </c:when>
        <c:otherwise>
          <form method="post" action="logInGame">
              <input type="hidden" name="gameId" value="${game.id}">
              <input type="hidden" name="replace" value="1">
              <c:choose>
                  <c:when test="${game.full}">
                      <input type="submit" value="Přihlásit se jako náhradník.">
                      <input type="hidden" name="substitute" value="1">
                  </c:when>
                  <c:otherwise>
                      <input type="submit" value="Přihlásit se na hru.">
                      <input type="hidden" name="substitute" value="0">
                  </c:otherwise>
              </c:choose>
              </c:otherwise>
              </c:choose>
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
                    <td>${game.menFreeRoles}</td>
                    <td>${game.womenFreeRoles}</td>
                    <td>${game.bothFreeRoles}</td>
                </tr>
                <tr>
                    <td>Zatím přihlášeno</td>
                    <td>${game.menAssignedRoles}</td>
                    <td>${game.womenAssignedRoles}</td>
                    <td></td>
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