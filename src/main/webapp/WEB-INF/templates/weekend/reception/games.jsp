<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="text">
  <h2>Hry uváděné v rámci HRAJ LARP víkendu</h2>

  <p>
    Zde vidíte seznam her uváděných v rámci HRAJ LARP víkendu. Kliknutím na název hry se dostanete na detail hry,
    kde se pak můžete přihlásit.
  </p>

  <h3>Pátek 18:00 - 23:00</h3>

  <table width="100%">
    <thead>
    <th>Název hry</th>
    <th>Umístění</th>
    <th>Mužské role</th>
    <th>Ženské role</th>
    <th>Obojetné role</th>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.games1}" var="game">
      <tr>
        <td><h3><a href="/weekend/game/players/${game.id}" tabindex="-1">${game.name}</a></h3></td>
        <td>${game.place}</td>
        <td style="text-align: center">${game.menFreeRoles}/${game.menRole}</td>
        <td style="text-align: center">${game.womenFreeRoles}/${game.womenRole}</td>
        <td style="text-align: center">${game.bothFreeRoles}/${game.bothRole}</td>
      </tr>

    </c:forEach>
    </tbody>
  </table>

  <h3>Sobota 9:00 - 14:00</h3>

  <table width="100%">
    <thead>
    <th>Název hry</th>
    <th>Umístění</th>
    <th>Mužské role</th>
    <th>Ženské role</th>
    <th>Obojetné role</th>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.games2}" var="game">
      <tr>
        <td><h3><a href="/weekend/game/players/${game.id}" tabindex="-1">${game.name}</a></h3></td>
        <td>${game.place}</td>
        <td style="text-align: center">${game.menFreeRoles}/${game.menRole}</td>
        <td style="text-align: center">${game.womenFreeRoles}/${game.womenRole}</td>
        <td style="text-align: center">${game.bothFreeRoles}/${game.bothRole}</td>
      </tr>

    </c:forEach>
    </tbody>
  </table>

  <h3>Sobota 16:00 - 21:00</h3>

  <table width="100%">
    <thead>
    <th>Název hry</th>
    <th>Umístění</th>
    <th>Mužské role</th>
    <th>Ženské role</th>
    <th>Obojetné role</th>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.games3}" var="game">
      <tr>
        <td><h3><a href="/weekend/game/players/${game.id}" tabindex="-1">${game.name}</a></h3></td>
        <td>${game.place}</td>
        <td style="text-align: center">${game.menFreeRoles}/${game.menRole}</td>
        <td style="text-align: center">${game.womenFreeRoles}/${game.womenRole}</td>
        <td style="text-align: center">${game.bothFreeRoles}/${game.bothRole}</td>
      </tr>

    </c:forEach>
    </tbody>
  </table>

  <h3>Neděle 10:00 - 15:00</h3>

  <table width="100%">
    <thead>
    <th>Název hry</th>
    <th>Umístění</th>
    <th>Mužské role</th>
    <th>Ženské role</th>
    <th>Obojetné role</th>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.games4}" var="game">
      <tr>
        <td><h3><a href="/weekend/game/players/${game.id}" tabindex="-1">${game.name}</a></h3></td>
        <td>${game.place}</td>
        <td style="text-align: center">${game.menFreeRoles}/${game.menRole}</td>
        <td style="text-align: center">${game.womenFreeRoles}/${game.womenRole}</td>
        <td style="text-align: center">${game.bothFreeRoles}/${game.bothRole}</td>
      </tr>

    </c:forEach>
    </tbody>
  </table>

  <h3>Přespání</h3>

  <c:forEach items="${requestScope.sleepOver}" var="game">
    <p><a href="/weekend/game/players/${game.id}" tabindex="-1">${game.name}</a></p>
  </c:forEach>
</div>