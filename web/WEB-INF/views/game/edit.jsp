<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<!DOCTYPE html>
<!-- saved from url=(0043)http://hrajlarp.cz/administrace/pridej-hru/ -->
<html class=" js no-flexbox canvas canvastext no-webgl no-touch geolocation postmessage websqldatabase indexeddb hashchange history draganddrop websockets rgba hsla multiplebgs backgroundsize borderimage borderradius boxshadow textshadow opacity cssanimations csscolumns cssgradients cssreflections csstransforms no-csstransforms3d csstransitions fontface generatedcontent video audio localstorage sessionstorage webworkers applicationcache svg inlinesvg smil svgclippaths" lang="en"><!--<![endif]--><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <link href="./Přidej hru_files/css" rel="stylesheet" type="text/css">
    <title>Přidej hru</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta property="og:title" content="Přidej hru">
    <meta property="og:type" content="website">
    <meta property="og:url" content="http://hrajlarp.cz/administrace/pridej-hru/">
    <meta property="og:description" content="Přidej hru">
    <meta property="og:image" content="http://hrajlarp.cz/ogimg-larp.jpg">
    <link href="http://hrajlarp.cz/temp/css/bootstrap.min.css" rel="stylesheet">
    <link href="http://hrajlarp.cz/temp/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link rel="stylesheet" href="http://hrajlarp.cz/temp/css/style.css">
    <script src="./Přidej hru_files/ga.js"></script><script src="./Přidej hru_files/modernizr-2.5.3.min.js"></script>
    <script src="./Přidej hru_files/respond.min.js"></script>
    <link rel="alternate" type="application/rss+xml" title="RSS Feed HRAJ LARP" href="http://www.facebook.com/feeds/page.php?format=rss20&id=247469131956626">
</head>

<body>
<form:form method="post" action="edit?id=4" commandName="myGame">
    <div>
           <label for="name">Jméno hry:*</label>
           <form:errors path="name" />
           <form:input path="name" id="name" value="${game.name}"/>
       </div>

       <div>
           <label for="anotation">Popis hry:*</label>
           <form:errors path="anotation" />
           <textarea id="anotation" name="anotation">${game.anotation}</textarea>
       </div>
       <div>
           <label for="aboutGame">O hře:</label>
           <form:errors path="aboutGame" />
           <textarea id="aboutGame" name="aboutGame">${game.aboutGame}</textarea>
       </div>
       <div>
           <label for="author">Autor:*</label>
           <form:errors path="author" />
           <form:input type="text" id="author" path="author" value="${game.author}" />
       </div>
       <div>
           <label for="date">Datum ve formátu(YYYY-mm-dd):*</label>
           <form:errors path="date" />
           <form:input type="text" id="date" path="date" value="${date}"/>
       </div>
       <div>
           <label for="time">Čas ve formátu(HH:MM):</label>
           <form:errors path="time" />
           <form:input type="text" id="time" path="time" value="${time}"/>
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
           <textarea id="info" name="info">${game.info}</textarea>
       </div>
       <div>
           <label for="place">Místo:</label>
           <form:errors path="place" />
           <textarea id="place" name="place">${game.place}</textarea>
       </div>
       <div>
           <label for="shortText">Info na případnou značku v kalendáři vedle termínu:</label>
           <form:errors path="shortText" />
           <textarea id="shortText" name="shortText">${game.shortText}</textarea>
       </div>

       <input type="submit" value="Ulož změny">
   </form:form>

</body>
</html>