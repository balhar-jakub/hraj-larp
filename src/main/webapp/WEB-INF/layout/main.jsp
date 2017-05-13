<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='/WEB-INF/tlds/template.tld' prefix='template' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <link href='https://fonts.googleapis.com/css?family=PT+Sans+Narrow:400,700&subset=latin,latin-ext' rel='stylesheet'
          type='text/css'>
    <title><template:get name='title'/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta property="og:title" content="<template:get name='title' />"/>
    <meta property="og:type" content="website"/>
    <meta property="og:url" content="<template:get name='url' />"/>
    <meta property="og:description" content="<template:get name='description' />"/>
    <meta property="og:image" content="<template:get name='image' />"/>

    <link href="/style/bootstrap.min.css" rel="stylesheet">
    <link href="/style/bootstrap-responsive.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/style/style.css">
    <script src="/js/libs/modernizr-2.5.3.min.js"></script>
    <script src="/js/libs/respond.min.js"></script>
    <link rel="alternate" type="application/rss+xml" title="RSS Feed HRAJ LARP"
          href="http://www.facebook.com/feeds/page.php?format=rss20&id=247469131956626"/>
</head>

<body>
<!-- navigation begin -->
<nav>
    <ul>
        <div class="band newblue clearfix">
            <li>
                <a href="/" class="logo"><span>Úvodní stránka</span>
                    <div class="grid1 square1 logo1"><img src="/img/logo-oranz.svg" width="150" alt="logo"></div>
                </a>
            </li>
            <div class="grid3 square1 head1"></div>
            <div class="grid2 square1 head2">
                <a href="http://facebook.com/hrajlarp" class="social" tabindex="-1"><i
                        class="icon-facebook-sign"></i></a>
                <a href="http://plus.google.com/108076251206659481852/" class="social" tabindex="-1"><i
                        class="icon-google-plus-sign"></i></a>
                <a href="http://www.praguebynight.eu/" class="social" tabindex="-1">
                    <img src="/img/logo-pbn.jpg" width="37" height="37" style="margin-top: -6px;border-radius: 5px;">
                </a>

                <!-- |Get somehow info about login and insert it here. -->
                <c:import url="/WEB-INF/templates/loginPanel.jsp"></c:import>
            </div>
        </div>
        <div class="band clearfix" id="menu">
            <div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Základy</em>
                <li><a href="/zaklady/"><span>Co je larp?</span></a></li>
                <li><a href="/zaklady#jak-na-to"><span>Jak si zahrát?</span></a></li>
            </div>
            <div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Náš festival</em>
                <li><a href="/festival/"><span>Info &amp; pokyny</span></a></li>

                <!--<li><a href="#"><span>Fotogalerie</span></a></li>!-->
            </div>
            <div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Kdy se hraje?</em>
                <li><a href="/kalendar/"><span>Kalendář</span></a></li>
            </div>
            <div class="menu-segment" style="height: 140.4036464691162px;">
                <em>O nás</em>
                <li><a href="/o-projektu/"><span>O projektu</span></a></li>
                <li><a href="/o-projektu#kontakt"><span>Tým &amp; kontakt</span></a></li>
            </div>
            <div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Larpy jinde</em>
                <li><a href="/odkazy/"><span>Odkazy</span></a></li>
            </div>
        </div>
    </ul>
</nav>
<!-- navigation end -->

<div class="band white clearfix">

    <template:get name='content'/>
</div>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="/js/libs/jquery-1.7.1.min.js"><\/script>')</script>
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
<script src="/js/bootstrap.min.js"></script>
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

</body>
</html>