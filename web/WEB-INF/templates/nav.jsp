<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Matheo
  Date: 13.3.13
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <nav>
        <ul>
            <div class="band white clearfix">
                <li>
                    <a href="http://hrajlarp.cz/" class="logo"><span>Úvodní stránka</span>
                        <div class="grid1 square logo1"><img src="/img/logo1.png" width="100%" alt="logo"></div>
                        <div class="grid1 square logo2"><img src="/img/logo2.png" width="100%" alt="logo"></div>
                    </a>
                </li>
                <div class="grid2 square1 head1"></div>
                <div class="grid2 square1 head2">
                    <a href="http://facebook.com/hrajlarp" class="social" tabindex="-1"><i class="icon-facebook-sign"></i></a>
                    <a href="http://plus.google.com/108076251206659481852/" class="social" tabindex="-1"><i class="icon-google-plus-sign"></i></a>


                    <%-- place panel with login informations here --%>
    				<c:import url="/assets/templates/loginPanel.jsp" />

                </div>
            </div>
            <div class="band clearfix" id="menu">
                <div class="menu-segment" style="height: 140.4036464691162px;">
                    <em>Základy</em>
                    <li><a href="http://hrajlarp.cz/zaklady/"><span>Co je larp?</span></a></li>
                    <li><a href="http://hrajlarp.cz/zaklady/nav.jsp#jak-na-to"><span>Jak si zahrát?</span></a></li>
                </div><div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Náš festival</em>
                <li><a href="http://hrajlarp.cz/festival/"><span>Info &amp; pokyny</span></a></li>

                <!--<li><a href="#"><span>Fotogalerie</span></a></li>!-->
            </div><div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Kdy se hraje?</em>
                <li><a href="http://hrajlarp.cz/kalendar/"><span>Kalendář</span></a></li>
            </div><div class="menu-segment" style="height: 140.4036464691162px;">
                <em>O nás</em>
                <li><a href="http://hrajlarp.cz/o-projektu/"><span>O projektu</span></a></li>
                <li><a href="http://hrajlarp.cz/o-projektu/nav.jsp#kontakt"><span>Tým &amp; kontakt</span></a></li>
            </div><div class="menu-segment" style="height: 140.4036464691162px;">
                <em>Larpy jinde</em>
                <li><a href="http://hrajlarp.cz/odkazy/"><span>Odkazy</span></a></li>
            </div>
            </div>
        </ul>
    </nav>