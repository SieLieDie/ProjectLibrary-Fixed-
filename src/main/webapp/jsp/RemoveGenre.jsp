<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/Style.css" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <fmt:setLocale value="${langLocal}"/>
    <fmt:setBundle basename="InterfaceValue" var="local"/>
    <title>Remove Genre</title>
</head>
<body>
<div class="dialogForm">
    <h2><fmt:message key="key.removeGenre" bundle="${local}"/></h2>
    <form action="/do/removeGenre" method="post">
        <div class="form-group" style="line-height: 1.5">
            <label for="genreCoreID"><fmt:message key="key.genreCore" bundle="${local}"/>:</label>
            <select id="genreCoreID" name="genreCoreID">
                <c:forEach items="${genreList}" var="genre">
                    <option value="${genre.coreID}">${genre.name}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="key.enter" bundle="${local}"/></button>
            <a href="/do/goToMainPage" style="padding-left: 20%"><fmt:message key="key.cancel" bundle="${local}"/></a>
        </div>
    </form>
</div>
</body>
</html>
