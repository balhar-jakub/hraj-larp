<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty succesMessage}" >
    <c:if test="${succesMessage}">
        <h3>Akce byla úspěšně přidána.</h3>
    </c:if>
    <c:if test="${!succesMessage}">
        <h3>Akci se nepodařilo přidat!</h3>
    </c:if>
</c:if>
<form:form method="post" action="/admin/game/addAction">
	<div>
        <label for="actionText">Nová akce:</label>
        <textarea id="actionText" name="actionText"></textarea>
    </div>
    <div>
        <label for="gameText">Pro hru:</label>
        <select multiple id="gameText" name="gameText">
           <c:forEach var="g" items="${games}">
                <option value="${g.id}" >${g.name}</option>
            </c:forEach>
        </select>
    </div>

  	<input type="submit" value="Ulož akci">
</form:form>

<form:form method="post" action="/admin/game/addAction">
	<div>
        <label for="actionText">Existující akce:</label>
        <select id="actionText" name="actionText">
            <c:forEach var="a" items="${actions}">
                <option value="${a}" >${a}</option>
            </c:forEach>
        </select>
    </div>
    <div>
        <label for="gameText">Pro hru:</label>
        <select multiple id="gameText" name="gameText">
           <c:forEach var="g" items="${games}">
                <option value="${g.id}" >${g.name}</option>
            </c:forEach>
        </select>
    </div>

  	<input type="submit" value="Přiřaď akci">
</form:form>