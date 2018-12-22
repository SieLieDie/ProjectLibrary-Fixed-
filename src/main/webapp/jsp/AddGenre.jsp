<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/Style.css" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <fmt:setLocale value="${langLocal}"/>
    <fmt:setBundle basename="InterfaceValue" var="local"/>
    <title>Add genre</title>
</head>
<body>
<div class="dialogForm">
    <h2><fmt:message key="key.addGenre" bundle="${local}"/></h2>
    <form action="/do/addGenre" method="post">
        <div class="form-group" style="line-height: 1.5">
            <label for="genreCore"><fmt:message key="key.genreCore" bundle="${local}"/>:</label>
            <input type="text" class="form-control" id="genreCore" name="genreCore" required maxlength="15">
        </div>
        <c:forEach items="${langList}" var="lang">
            <div class="form-group" style="line-height: 1.5">
                <label for="${lang.name}">${lang.name}</label>
                <input type="text" class="form-control" id="${lang.name}" name="${lang.name}"
                       placeholder="<fmt:message key="key.enterGenre" bundle="${local}"/>" required maxlength="15"/>
            </div>
        </c:forEach>
        <div>
            <button type="submit" class="btn btn-primary">
                <fmt:message key="key.enter" bundle="${local}"/></button>
            <a href="/do/goToMainPage" style="padding-left: 20%"><fmt:message key="key.cancel" bundle="${local}"/></a>
        </div>
    </form>
</div>
</body>
</html>
