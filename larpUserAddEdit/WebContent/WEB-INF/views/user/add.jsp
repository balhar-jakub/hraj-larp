<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>add user</title>
    <link href="/style/font.css" type="text/css" rel="stylesheet" >
    <link href="/style/style.css" type="text/css" rel="stylesheet">
</head>
<body>
	<div class="text">
        <form:form method="post" action="register" commandName="userForm">
            <div>
            	<label for="name">Jméno: </label>
            	<form:input path="name" id="name" value="${user.name}" />
            	<%-- <form:input type="text" id="name" path="name" value="${user.name}" />  --%>
	           	<form:errors path="name" />
            </div>
            <div>
            	<label for="lastName">Příjmení: </label>
	           	<form:input path="lastName" id="lastName" value="${user.lastName}" />
	           	<%-- <form:input type="text" id="lastName" path="lastName" value="${user.lastName}" />  --%>
	           	<form:errors path="lastName" />
            </div>
            <div>
            	<label for="userName">Uživatelské jméno: </label>    
            	<form:input path="userName" id="userName" value="${user.userName}" />
	           	<%-- <form:input type="text" id="userName" path="userName" value="${user.userName}" /> --%>
	           	<form:errors path="userName" />
            </div>
            <div>
                <label for="password">Heslo: </label>
                <form:password path="password" id="password" value="${user.password}" />
                <!--  <input type="password" id="password" name="password" value="">-->
                <form:errors path="password" />
            </div>
            <div>
                <label for="passwordAgain">Heslo pro kontrolu: </label>
                <form:password path="passwordAgain" id="passwordAgain" value="${user.passwordAgain}" />
                <form:errors path="passwordAgain" />
            </div>
            <div>
            	<label for="email">Email: </label>
            	<form:input path="email" id="email" value="${user.email}" />
	           	<%-- <form:input type="text" id="email" path="email" value="${user.email}" /> --%>
	           	<form:errors path="email" />
            </div>
            <div>
            	<label for="phone">Telefon: </label>
            	<form:input path="phone" id="phone" value="${user.phone}" />
            	<%-- <form:input type="text" id="phone" path="phone" value="${user.phone}" /> --%>
	           	<form:errors path="phone" />
            </div>

            <div>
                <form:radiobutton path="genderForm" id="genderForm" value="M" /> Muž 
                <form:radiobutton path="genderForm" id="genderForm" value="F" /> Žena 
                <form:errors path="genderForm" />
            </div>
            <div>
                <label style="float: left;" for="mailInformation">Chci dostávat na email informace: </label>
                <form:checkbox path="mailInformation" id="mailInformation" />
                <form:errors path="mailInformation" />
            </div>
			
            <input type="submit" value="Registruj">
        </form:form>
    </div>

</div>

<script src="assets/js/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../temp/js/libs/jquery-1.7.1.min.js"><\/script>')</script>
<script>
    function equalHeight(group) {
        var tallest = 0;
        group.css("height", "auto");
        group.each(function () {
            var thisHeight = $(this).height();
            if (thisHeight > tallest) {
                tallest = thisHeight;
            }
        });
        group.css("height", tallest);
    }

    $(document).ready(function () {
        equalHeight($(".menu-segment"));
        equalHeight($(".eh1"));
    });
    $(window).resize(function () {
        equalHeight($(".menu-segment"));
        equalHeight($(".eh1"));
    });
</script>
<script src="assets/js/bootstrap.min.js"></script>
<script>
    var _gaq = [
        ['_setAccount', 'UA-35090741-1'],
        ['_trackPageview']
    ];
    (function (d, t) {
        var g = d.createElement(t), s = d.getElementsByTagName(t)[0];
        g.src = ('https:' == location.protocol ? '//ssl' : '//www') + '.google-analytics.com/ga.js';
        s.parentNode.insertBefore(g, s)
    }(document, 'script'));
</script>


</body></html>