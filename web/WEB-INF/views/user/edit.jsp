<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix='form' uri='http://www.springframework.org/tags/form' %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>edit user</title>
    <link href="/style/font.css" type="text/css" rel="stylesheet">
    <link href="/style/style.css" type="text/css" rel="stylesheet">
</head>

<body>
<div class="band white clearfix">
    <div class="text">
        <form:form method="post" action="update" commandName="userForm">
        <form:hidden path="id" value="${user.id}" />
        <label for="name">Jméno: </label>
        <form:input path="name" id="name" value="${user.name}"/>
        <form:errors path="name" />
    </div>
    <div>
        <label for="lastName">Příjmení: </label>
        <form:input path="lastName" id="lastName" value="${user.lastName}"/>
        <form:errors path="lastName" />
    </div>
    <div>
        <label for="userName">Uživatelské jméno: </label>
        <form:input path="userName" id="userName" value="${user.userName}"/>
        <form:errors path="userName" />
    </div>
    <div>
        <label for="password">Heslo: </label>
        <form:password path="password" id="password" value="${user.password}"/>
        <form:errors path="password" />
    </div>
    <div>
        <label for="passwordAgain">Heslo pro kontrolu: </label>
        <form:password path="passwordAgain" id="passwordAgain" value="${user.passwordAgain}"/>
        <form:errors path="passwordAgain" />
    </div>
    <div>
        <label for="email">Email: </label>
        <form:input path="email" id="email" value="${user.email}"/>
        <form:errors path="email" />
    </div>
    <div>
        <label for="phone">Telefon: </label>
        <form:input path="phone" id="phone" value="${user.phone}"/>
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

    <input type="submit" value="Uprav">
    </form:form>
</div>

<script src="/js/jquery-1.9.1.min.js"></script>
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