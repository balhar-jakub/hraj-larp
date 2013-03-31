<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form:form method="post" action="update" commandName="userForm">
    <form:hidden path="id" value="${user.id}"/>
	<form:hidden path="oldPassword" value="${user.oldPassword}"/>
    <div class="text">
        <label for="name">Jméno: </label>
        <form:input path="name" id="name" value="${user.name}"/>
        <form:errors path="name"/>
    </div>
    <div>
        <label for="lastName">Příjmení: </label>
        <form:input path="lastName" id="lastName" value="${user.lastName}"/>
        <form:errors path="lastName"/>
    </div>
    <div>
        <label for="userName">Uživatelské jméno: </label>
        <form:input path="userName" id="userName" value="${user.userName}"/>
        <form:errors path="userName"/>
    </div>
    <div>
        <label for="password">Heslo: </label>
        <form:password path="password" id="password" value="${user.password}"/>
        <form:errors path="password"/>
    </div>
    <div>
        <label for="passwordAgain">Heslo pro kontrolu: </label>
        <form:password path="passwordAgain" id="passwordAgain" value="${user.passwordAgain}"/>
        <form:errors path="passwordAgain"/>
    </div>
    <div>
        <label for="email">Email: </label>
        <form:input path="email" id="email" value="${user.email}"/>
        <form:errors path="email"/>
    </div>
    <div>
        <label for="phone">Telefon: </label>
        <form:input path="phone" id="phone" value="${user.phone}"/>
        <form:errors path="phone"/>
    </div>

    <div>
        <form:radiobutton path="genderForm" id="genderForm" value="M"/> Muž
        <form:radiobutton path="genderForm" id="genderForm" value="F"/> Žena
        <form:errors path="genderForm"/>
    </div>
    <div>
        <label style="float: left;" for="mailInformation">Chci dostávat na email informace: </label>
        <form:checkbox path="mailInformation" id="mailInformation"/>
        <form:errors path="mailInformation"/>
    </div>

    <input type="submit" value="Uprav">
</form:form>