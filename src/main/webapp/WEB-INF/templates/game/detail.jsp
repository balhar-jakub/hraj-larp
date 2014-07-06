<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template' %>
<div class="text">
    <h1>${game.name} | ${game.date}</h1>
</div>

<c:choose>
  <c:when test="${not empty game.shortText}">
    <div class="page-termin festival">
  </c:when>
  <c:otherwise>
    <div class="page-termin">
  </c:otherwise>
</c:choose>

    <div class="text left">
        <p>${game.info}</p>
        <img src="${game.image}" alt="ilustrační obrázek">
    </div>


    <div class="rightside">
        <div class="siderow clearfix">
            <div class="datum">
                <span>${game.dateAsDM}</span>
                ${game.dateAsDayName}
            </div>
            <div class="cas">
                <span>${game.time}</span>
            </div>
        </div>
        <div class="siderow center">
        <span class="info" style="width:80%;">
          <!-- If user is logged and date is correct button Přihlaš tady bude. -->
  <c:choose>
      <c:when test="${logged}">
          <c:choose>
            <c:when test="${isFuture}">
               <span>
                     Hra již proběhla.
               </span>
            </c:when>
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
                    <input type="submit" value="Odhlásit ze hry.">
                    <input type="hidden" name="gameId" value="${game.id}">
                </form>
            </c:when>
            <c:otherwise>
	            <c:choose>
	            	<c:when test="${regStarted}">
	            		<form method="post" action="logInGame">
		                  <input type="hidden" name="gameId" value="${game.id}">
		                  <c:choose>
		                      <c:when test="${isFull}">
		                          <input type="submit" value="Přihlásit se jako náhradník.">
		                      </c:when>
		                      <c:otherwise>
		                          <input type="submit" value="Přihlásit se na hru.">
		                      </c:otherwise>
		                  </c:choose>
		              </form>
	            	</c:when>
		            <c:otherwise>
		            	Přihlašování do hry je možné až od ${regStart}
		            	<c:choose>
		                     <c:when test="${showNotifRegStart}">
		                     	<form method="post" action="regNotifyForm" >
		                  			<input type="hidden" name="gameId" value="${game.id}">
		                          	<input type="submit" style="width:100%;" value="Informovat den před začátkem přihlašování.">
		                        </form>
		                     </c:when>
		                </c:choose>
		           </c:otherwise>
		       </c:choose>   
           </c:otherwise>
    	</c:choose>
    </c:when>
    <c:otherwise>
      	<span> Pro přihlášení na hru se přihlašte nebo registrujte. </span>
    </c:otherwise>
    </c:choose>
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
                    <td>${game.players.menRoles}</td>
                    <td>${game.players.womenRoles}</td>
                    <td>${game.players.bothRoles}</td>
                </tr>
                <tr>
                    <td>z toho volných</td>
                    <td>${game.players.menFreeRoles}</td>
                    <td>${game.players.womenFreeRoles}</td>
                    <td>${game.players.bothFreeRoles}</td>
                </tr>
                <tr>
                    <td>Náhradníci</td>
                    <td>${game.players.menSubstitutes}</td>
                    <td>${game.players.womenSubstitutes}</td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="siderow">
            <h4>Místo</h4>
            ${game.place}
        </div>
        <c:if test="${not empty game.web}">
            <div class="siderow">
                <h4>Web Larpu</h4>
                <a href="${game.web}">${game.web}</a>
            </div>
        </c:if>

        <c:if test="${not empty canEdit}">
            <div class="siderow">
                <a href="/game/edit?id=${game.id}"><h4>Editace hry</h4></a>
            </div>
        </c:if>
    </div>

    <div class="text left">
        <h2>O hře</h2>
        <p>${game.aboutGame}</p>

        <h2>Popis</h2>
        <p>${game.annotation}</p>

        <h3>Autor</h3>
        <p>${game.author}</p>

        <h3>Typ hry:</h3>
        <c:choose>
            <c:when test="${not empty game.shortText}">
                <p>Hra uváděná v rámci festivalu HRAJ LARP.</p>
            </c:when>
            <c:otherwise>
                <p>${game.shortText}</p>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty game.action}">
            <h3>Akce:</h3>
            <p>${game.action}</p>
        </c:if>
    </div>
</div>