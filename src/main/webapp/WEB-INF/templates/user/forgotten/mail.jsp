<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p>
    Zadejte své uživatelské jméno. Na email přiřazený k tomuto uživatelskému jménu vám bude zaslán odkaz pro
    nastavení nového hesla.
</p>
<form method="post" action="/user/forgotten/">
    Uživatelské jméno: <input type="text" name="email"/><br>
    <input type="submit" value="Odeslat"/>
</form>
