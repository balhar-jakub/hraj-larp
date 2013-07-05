<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template' %>
<template:insert template="/WEB-INF/layout/main.jsp">
    <template:put name="title" content="Registrace | HRAJ LARP" direct="true"></template:put>
    <template:put name="description" content="Registrace | HRAJ LARP" direct="true"></template:put>
    <template:put name="url" content="/user/add" direct="true"></template:put>
    <template:put name="image" content="/img/ogimg-larp.jpg" direct="true"></template:put>

    <template:put name="content" content="/WEB-INF/templates/user/add.jsp"></template:put>
</template:insert>