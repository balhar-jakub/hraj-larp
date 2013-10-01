<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form:form method="post" action="add" enctype="multipart/form-data" commandName="myGame">

    <c:if test="${not empty copied}">
        <h3>Nyní můžete přidat kopii původní hry, nezapoměňte ji upravit</h3>
        Nejdůležitější je nastavení datumů hry a počty hráčů.
        <form:input type="hidden" value="${myGame.image}" name="originalImage" path="originalImage"/>
    </c:if>

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
        <label for="shortText">Typ hry:</label>
        <form:errors path="shortText" />
        <form:select path="shortText" id="shortText">
            <form:option value="Nezávislá" selected = "selected">Nezávislá</form:option>
            <form:option value="Festivalová">Festivalová</form:option>
        </form:select>
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
        <label for="imageFile">Nahrajte obrázek:</label>
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
        <label for="registrationStartedDate">Datum začátku přihlašování do hry (YYYY-mm-dd):*</label>
        <form:errors path="registrationStartedDate" />
        <form:input type="text" id="registrationStartedDate" path="registrationStartedDate" value="${game.registrationStartedDate}"/>
    </div>
    <div>
        <label for="registrationStartedTime">Čas začátku přihlašování do hry (HH:MM):</label>
        <form:errors path="registrationStartedTime" />
        <form:input type="text" id="registrationStartedTime" path="registrationStartedTime" value="${game.registrationStartedTime}"/>
    </div>
    <div>
        <label for="ordinaryPlayerText">Mail pro přihlášeného hráče(Posílá se když se hráč přihlásí jako regulerní hráč):</label>
        <form:textarea id="ordinaryPlayerText" path="ordinaryPlayerText" 
        value="" escapeXml="true" />
        <form:errors path="ordinaryPlayerText" />
    </div>
    <div>
        <label for="replacementsText">Mail pro náhradu (Posílá se v okamžiku kdy se z náhradníka stane hráč):</label>
        <form:textarea id="replacementsText" path="replacementsText" 
        value="" escapeXml="true" />
        <form:errors path="replacementsText" />
    </div>
    <div>
        <label for="registeredSubstitute">Mail pro náhradu (Posílá se v okamžiku kdy se hráč přihlásí jako náhrada):</label>
        <form:textarea id="registeredSubstitute" path="registeredSubstitute"
                       value="" escapeXml="true" />
        <form:errors path="registeredSubstitute" />
    </div>
	<div>
        <label style="float: left;" for="mailProhibition">Nechci odesílat maily o této hře: </label>
        <form:checkbox path="mailProhibition" id="mailProhibition" />
        <form:errors path="mailProhibition" />
    </div>
    <input type="submit" value="Přidej hru">
</form:form>