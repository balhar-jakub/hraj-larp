<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h3>Opravdu si přejete smazat hru?</h3>
<input type="button" value="Ano" onclick="location.href='/admin/game/delete/${gameId}'">
<input type="button" value="Ne" onclick="location.href='/admin/game/list'">