<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="band white clearfix">

    <div class="text">
        <h1>${requestScope.title}</h1>
    </div>
    <table class="players">
        <tr class="first">
            <td>Jméno hráče</td>
            <td>Telefon</td>
            <td>Email</td>
            <td>Pohlaví</td>
        </tr>
        <c:forEach items="${requestScope.users}" var="user">
            <tr>
                <td>${user.name} ${user.lastName}</td>
                <td>${user.phone}</td>
                <td>${user.email}</td>
                <td>${user.genderTextual}</td>
            </tr>
        </c:forEach>
    </table>

    <p>
        ${requestScope.description}
    </p>

    <form method="post">
        <c:forEach items="${requestScope.users}" var="user">
            <input type="hidden" name="users" value="${user.id}"/>
        </c:forEach>
        <button type="submit" formaction="${requestScope.confirmLink}">Provést</button>
    </form>

    <form method="get">
         <button type="submit" formaction="/admin/rights/edit">Zrušit</button>
    </form>
</div>