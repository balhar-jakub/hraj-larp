<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Seznam editorů hry</h1>
<c:if test="${empty editors}">
    Tuto hru nespravuje žádný editor, upravovat ji mohou pouze administrátoři.
</c:if>

<c:if test="${not empty editors}">
<form method="post">
<table class="players">
    <tr class="first">
        <td>Jméno hráče</td>
        <td>Telefon</td>
        <td>Email</td>
        <td>Pohlaví</td>
        <td></td>
    </tr>
    <c:forEach items="${requestScope.editors}" var="editor">
        <tr>
            <td>${editor.name} ${editor.lastName}</td>
            <td>${editor.phone}</td>
            <td>${editor.email}</td>
            <td>${editor.genderTextual}</td>
            <td>
                <button type="submit" formaction="/admin/game/editors/remove/${gameId}/${editor.id}">Odebrat</button>
            </td>
        </tr>
    </c:forEach>
</table>
</form>
</c:if>

<h1>Přidat editora</h1>
<form method="post">
        <select multiple size="8" id="futureEditors" name="futureEditors">
           <c:forEach var="editor" items="${requestScope.notEditors}">
                <option value="${editor.id}" >
                    <c:out value="${editor.name} ${editor.lastName}, (${editor.genderTextual}), ${editor.email}" />
                </option>
           </c:forEach>
        </select>
    <button type="submit" formaction="/admin/game/editors/add/${gameId}">Přidat</button>
</form>