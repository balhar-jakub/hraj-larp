<%--
  Created by IntelliJ IDEA.
  User: balda
  Date: 6.3.13
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- saved from url=(0043)http://hrajlarp.cz/administrace/pridej-hru/ -->
<html class=" js no-flexbox canvas canvastext no-webgl no-touch geolocation postmessage websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients cssreflections csstransforms no-csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths" lang="en"><!--<![endif]--><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="./Přidej hru_files/css" rel="stylesheet" type="text/css">
    <title>Přidej hru</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <link rel="alternate" type="application/rss+xml" title="RSS Feed HRAJ LARP" href="http://www.facebook.com/feeds/page.php?format=rss20&id=247469131956626">

    <link rel='stylesheet' type='text/css'
          href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700&subset=latin,latin-ext'>

    <link href="/style/bootstrap.min.css" rel="stylesheet">
    <link href="/style/bootstrap-responsive.min.css" rel="stylesheet">

    <link href="/style/style.css" type="text/css" rel="stylesheet">
    <link href="/style/font.css" type="text/css" rel="stylesheet">
</head>

<body>
<c:import url="/WEB-INF/templates/nav.jsp" />

<div class="band white clearfix">
<form:form method="post" action="add" enctype="multipart/form-data" commandName="myGame">
       <div>
           <label for="name">Jméno hry:*</label>
           <form:errors path="name" />
           <form:input path="name" id="name" />
       </div>

       <div>
           <label for="anotation">Popis hry:*</label>
           <form:errors path="anotation" />
           <form:textarea id="anotation" path="anotation" />
       </div>
       <div>
           <label for="aboutGame">O hře:</label>
           <form:errors path="aboutGame" />
           <form:textarea id="aboutGame" path="aboutGame" />
       </div>
       <div>
           <label for="author">Autor:*</label>
           <form:errors path="author" />
           <form:input type="text" id="author" path="author" value="${game.author}" />
       </div>
       <div>
           <label for="date">Datum ve formátu(YYYY-mm-dd):*</label>
           <form:errors path="date" />
           <form:input type="text" id="date" path="date" value="${game.date}"/>
       </div>
       <div>
           <label for="time">Čas ve formátu(HH:MM):</label>
           <form:errors path="time" />
           <form:input type="text" id="time" path="time" value="${game.time}"/>
       </div>
       <div>
           <label for="imageFile">Nahrajte obrázek:*</label>
           <input type="file" name="imageFile">
           <form:errors path="image" />
       </div>
       <div>
           <label for="web">Web:</label>
           <form:errors path="web" />
           <form:input type="text" id="web" path="web" value="${game.web}" />
       </div>
       <div>
           <label for="menRole">Mužské role:</label>
           <form:errors path="menRole" />
           <form:input type="text" id="menRole" path="menRole" value="${game.menRole}" />
       </div>
       <div>
           <label for="womenRole">Ženské role:</label>
           <form:errors path="womenRole" />
           <form:input type="text" id="womenRole" path="womenRole" value="${game.womenRole}" />
       </div>
       <div>
           <label for="bothRole">Obojetné role:</label>
           <form:errors path="bothRole" />
           <form:input type="text" id="bothRole" path="bothRole" value="${game.bothRole}" />
       </div>
       <div>
           <label for="info">Upoutávka:</label>
           <form:errors path="info" />
           <form:textarea id="info" path="info"/>
       </div>
       <div>
           <label for="place">Místo:</label>
           <form:errors path="place" />
           <form:textarea id="place" path="place"/>
       </div>
       <div>
           <label for="shortText">Info na případnou značku v kalendáři vedle termínu:</label>
           <form:errors path="shortText" />
           <form:textarea id="shortText" path="shortText" value=""/>
       </div>


       <input type="submit" value="Přidej hru">
   </form:form>
</div>
</body>
</html>