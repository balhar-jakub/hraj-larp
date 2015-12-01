<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template' %>
<template:insert template="/WEB-INF/layout/main.jsp">
  <template:put name="title" content="Hráči | HRAJ LARP" direct="true"></template:put>
  <template:put name="description" content="Hráči | HRAJ LARP" direct="true"></template:put>
  <template:put name="url" content="/weekend/game/players" direct="true"></template:put>
  <template:put name="image" content="/img/ogimg-larp.jpg" direct="true"></template:put>

  <template:put name="content" content="/WEB-INF/templates/weekend/game/players.jsp"></template:put>
</template:insert>