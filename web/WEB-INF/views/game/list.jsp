<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: balda
  Date: 6.3.13
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table align="center" border="1">
    <tr>
        <td>Nr:</td><td>Name:</td><td>Autor</td><td>Modify?</td>
    </tr>
    <c:forEach var="game" items="${games}" varStatus="status">
        <tr>
            <td><c:out value="${status.count}"/></td><td><c:out value="${game.name}"/></td>
            <td><c:out value="${game.author}"/></td><td>Modify</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>