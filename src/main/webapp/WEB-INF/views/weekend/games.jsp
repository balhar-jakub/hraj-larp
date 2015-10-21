<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template' %>
<template:insert template="/WEB-INF/layout/main.jsp">
  <template:put name="title" content="HRAJ LARP Víkend Hry | HRAJ LARP" direct="true"></template:put>
  <template:put name="description" content="HRAJ LARP Víkend Hry | HRAJ LARP" direct="true"></template:put>
  <template:put name="url" content="/vikend/hry" direct="true"></template:put>
  <template:put name="image" content="/img/ogimg-larp.jpg" direct="true"></template:put>

  <template:put name="content" content="/WEB-INF/templates/weekend/games.jsp"></template:put>
</template:insert>