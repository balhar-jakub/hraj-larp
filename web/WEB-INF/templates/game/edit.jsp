<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form:form method="post" action="edit" enctype="multipart/form-data" commandName="myGame">
    <form:hidden path="id" value="${game.id}"/>
    <div>
        <label for="name">Jméno hry:*</label>
        <form:errors path="name"/>
        <form:input path="name" id="name" value="${game.name}"/>
    </div>

    <div>
        <label for="anotation">Popis hry:*</label>
        <form:errors path="anotation"/>
        <form:textarea path="anotation" id="anotation" cols="20" rows="3" />
    </div>
    <div>
        <label for="shortText">Typ hry:</label>
        <form:errors path="shortText" />
        <form:select path="shortText" id="shortText">
        <c:choose>
          <c:when test="${game.festival}">
            <form:option value="Nezávislá">Nezávislá</form:option>
            <form:option value="Festivalová" selected="selected">Festivalová</form:option>
          </c:when>
          <c:otherwise>
            <form:option value="Nezávislá" selected = "selected">Nezávislá</form:option>
            <form:option value="Festivalová">Festivalová</form:option>
          </c:otherwise>
        </c:choose>
        </form:select>
    </div>
    <div>
        <label for="aboutGame">O hře:</label>
        <form:errors path="aboutGame"/>
        <form:textarea path="aboutGame" id="aboutGame" cols="20" rows="3" />
    </div>
    <div>
        <label for="author">Autor:*</label>
        <form:errors path="author"/>
        <form:input type="text" id="author" path="author" value="${game.author}"/>
    </div>
    <div>
        <label for="date">Datum ve formátu(YYYY-mm-dd):*</label>
        <form:errors path="date"/>
        <form:input type="text" id="date" path="date" value="${date}"/>
    </div>
    <div>
        <label for="time">Čas ve formátu(HH:MM):</label>
        <form:errors path="time"/>
        <form:input type="text" id="time" path="time" value="${time}"/>
    </div>
    <div>
        <label for="imageFile">Nahrajte obrázek:</label>
        <input type="file" name="imageFile">
        <form:errors path="image" />
    </div>
    <div>
        <label for="web">Web:</label>
        <form:errors path="web"/>
        <form:input type="text" id="web" path="web" value="${game.web}"/>
    </div>
    <div>
        <label for="menRole">Mužské role:</label>
        <form:errors path="menRole"/>
        <form:input type="text" id="menRole" path="menRole" value="${game.menRole}"/>
    </div>
    <div>
        <label for="womenRole">Ženské role:</label>
        <form:errors path="womenRole"/>
        <form:input type="text" id="womenRole" path="womenRole" value="${game.womenRole}"/>
    </div>
    <div>
        <label for="bothRole">Obojetné role:</label>
        <form:errors path="bothRole"/>
        <form:input type="text" id="bothRole" path="bothRole" value="${game.bothRole}"/>
    </div>
    <div>
        <label for="info">Upoutávka:</label>
        <form:errors path="info"/>
        <form:textarea path="info" id="info" cols="20" rows="3" />
    </div>
    <div>
        <label for="place">Místo:</label>
        <form:errors path="place"/>
        <form:textarea path="place" id="place" cols="20" rows="3" />
    </div>
    <div>
        <label for="registrationStarted">Datum a čas začátku přihlašování do hry(YYYY-mm-dd hh:mm):</label>
        <form:input type="text" id="registrationStarted" path="registrationStarted" value="${game.registrationStarted}" />
        <form:errors path="registrationStarted" />
    </div>
    <div>
        <label for="ordinaryPlayerText">Mail pro přihlášeného hráče:</label>
        <form:textarea path="ordinaryPlayerText" id="ordinaryPlayerText" cols="20" rows="3" />
        <form:errors path="ordinaryPlayerText" />
    </div>
    <div>
        <label for="replacementsText">Mail pro náhradu:</label>
        <form:textarea path="replacementsText" id="replacementsText" cols="20" rows="3" />
        <form:errors path="replacementsText" />
    </div>
    <input type="submit" value="Ulož změny">
</form:form>