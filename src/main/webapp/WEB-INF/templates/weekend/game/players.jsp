<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form method="post">
  <h2><c:out value="${gameName}" /></h2>
  <span class="printButton"><input type=button onclick="window.print()" value="Tisk seznamu"></span>
  <table class="players">
    <tr class="first">
      <td>Přezdívka</td>
      <td>Jméno hráče</td>
      <td>Telefon</td>
      <td>Email</td>
      <td>Pohlaví</td>
      <td>Zaplaceno</td>
    </tr>
    <c:forEach items="${requestScope.players}" var="player">
      <tr>
        <td>${player.userAttended.userName}</td>
        <td>${player.userAttended.name} ${player.userAttended.lastName}</td>
        <td>${player.userAttended.phone}</td>
        <td>${player.userAttended.email}</td>
        <td>${player.userAttended.genderTextual}</td>
        <td>${player.payedTextual}</td>
      </tr>
    </c:forEach>
    <c:if test="${not empty requestScope.substitutes}">
      <tr><td><b>Náhradníci:</b></td></tr>
      <c:forEach items="${requestScope.substitutes}" var="substitute">
        <tr>
          <td>${substitute.userAttended.userName}</td>
          <td>${substitute.userAttended.name} ${substitute.userAttended.lastName}</td>
          <td>${substitute.userAttended.phone}</td>
          <td>${substitute.userAttended.email}</td>
          <td>${substitute.userAttended.genderTextual}</td>
          <td>${substitute.payedTextual}</td>
        </tr>
      </c:forEach>
    </c:if>
  </table>
</form>