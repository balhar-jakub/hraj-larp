<%--
  Created by IntelliJ IDEA.
  User: Prasek
  Date: 22.4.13
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template' %>
<template:insert template="/WEB-INF/layout/main.jsp">
    <template:put name="title" content="Potvrzení smazání | HRAJ LARP" direct="true"></template:put>
    <template:put name="description" content="Potvrzení smazání | HRAJ LARP" direct="true"></template:put>
    <template:put name="url" content="/admin/game/confirmation" direct="true"></template:put>
    <template:put name="image" content="/img/ogimg-larp.jpg" direct="true"></template:put>

    <template:put name="content" content="/WEB-INF/templates/admin/game/confirmation.jsp"></template:put>
</template:insert>