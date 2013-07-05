<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Success</h2>

<p>Operace proběhla úspěšně.</p>

<c:if test="${not empty mailEdited}">
	<p><font color="red">
    Změnil/a jste si e-mailovou adresu. Váš účet není plně aktivován.
    </font></p>
    <p>Na zadanou e-mailovou adresu byl odeslán ověřovací mail. Zkontrolujte si prosím Vaši schránku pro plnou funkčnost účtu.</p>
</c:if>