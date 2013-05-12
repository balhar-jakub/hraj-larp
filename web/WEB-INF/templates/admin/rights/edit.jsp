<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="band white clearfix">

    <div class="text">
        <h1>Editace práv uživatele</h1>
    </div>

        <div>
             <h2>Současní osvědčení editoři</h2>

            <c:if test="${empty authEditors}">
                V současné době není v systému žádný osvědčený editor.
                Všechny nově vkládané hry musí být před vložením do kalendáře
                schváleny administrátorem.
            </c:if>
            <c:if test="${not empty authEditors}">
             <form method="post">
               <table class="players">
                  <tr class="first">
                     <td>Jméno hráče</td>
                     <td>Přezdívka</td>
                     <td>Telefon</td>
                     <td>Email</td>
                     <td>Pohlaví</td>
                     <td></td>
                  </tr>
                  <c:forEach items="${requestScope.authEditors}" var="user">
                     <tr>
                         <td>${user.name} ${user.lastName}</td>
                         <td>${user.userName}</td>
                         <td>${user.phone}</td>
                         <td>${user.email}</td>
                         <td>${user.genderTextual}</td>
                         <td>
                            <button type="submit" formaction="/admin/rights/autheditors/remove/confirm/${user.id}">Odebrat právo</button>
                         </td>
                     </tr>
                  </c:forEach>
               </table>
            </form>
            </c:if>

            <h2>Přidat uživateli práva osvědčeného editora</h2>
            <c:if test="${empty futureAuthorized}">
                V současné době není dostupný žádný uživatel, který by mohl získat práva osvědčeného editora.
            </c:if>
            <c:if test="${not empty futureAuthorized}">
            <form method="post">
                <select multiple size="8" id="futureAuthorized" name="futureAuthorized">
                    <c:forEach var="user" items="${requestScope.futureAuthorized}">
                    <option value="${user.id}" >
                            <c:out value="${user.name} ${user.lastName}, ${user.userName}, (${user.genderTextual}), ${user.email}" />
                        </option>
                </c:forEach>
                </select>
                <button type="submit" formaction="/admin/rights/autheditors/add/confirm/">Pověřit</button>
            </form>
            </c:if>
        </div>

        <div>
            <h2>Současní administrátoři</h2>
            <form method="post">
              <table class="players">
                  <tr class="first">
                     <td>Jméno hráče</td>
                     <td>Přezdívka</td>
                     <td>Telefon</td>
                     <td>Email</td>
                     <td>Pohlaví</td>
                     <td></td>
                  </tr>
                  <c:forEach items="${requestScope.admins}" var="admin">
                     <tr>
                         <td>${admin.name} ${admin.lastName}</td>
                         <td>${admin.userName}</td>
                         <td>${admin.phone}</td>
                         <td>${admin.email}</td>
                         <td>${admin.genderTextual}</td>
                         <td>
                            <button type="submit" formaction="/admin/rights/admins/remove/confirm/${admin.id}">Odebrat právo</button>
                         </td>
                     </tr>
                  </c:forEach>
              </table>
            </form>

            <h2>Pověřit uživatele rolí administrátora</h2>
            <c:if test="${empty futureAdmins}">
                V současné době není dostupný žádný uživatel, který by mohl získat administrátorská práva.
            </c:if>
            <c:if test="${not empty futureAdmins}">
            <form method="post">
                <select multiple size="8" id="futureAdmins" name="futureAdmins">
                    <c:forEach var="user" items="${requestScope.futureAdmins}">
                        <option value="${user.id}" >
                            <c:out value="${user.name} ${user.lastName}, ${user.userName}, (${user.genderTextual}), ${user.email}" />
                        </option>
                    </c:forEach>
                </select>
                <button type="submit" formaction="/admin/rights/admins/add/confirm/">Pověřit</button>
            </form>
            </c:if>
        </div>
</div>