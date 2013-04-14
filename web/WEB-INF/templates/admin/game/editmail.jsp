<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form:form method="post" action="/admin/game/editmail" commandName="editMailForm">
	<form:hidden path="id" value="${game.id}"/>
	
	<div>
        <label for="ordinaryPlayerText">Upravit text mailu pro přihlášeného hráče:</label>
        <textarea id="ordinaryPlayerText" name="ordinaryPlayerText">${game.ordinaryPlayerText}</textarea>
    </div>
    <div>
        <label for="replacementsText">Upravit text mailu pro náhradu:</label>
        <textarea id="replacementsText" name="replacementsText" >${game.replacementsText}</textarea>
    </div>
  	<input type="submit" value="Ulož změny">      
</form:form>