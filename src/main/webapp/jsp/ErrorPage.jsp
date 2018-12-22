<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ErrorPage</title>
    <fmt:setLocale value="${langLocal}"/>
    <fmt:setBundle basename="InterfaceValue" var="local"/>
    <c:set var="authorizationException" scope="request" value="authorizationException"/>
    <c:set var="addBookException" scope="request" value="addBookException"/>
    <c:set var="addAuthorException" scope="request" value="addAuthorException"/>
    <c:set var="addGenreException" scope="request" value="addGenreException"/>
    <c:set var="addUserException" scope="request" value="addUserException"/>
</head>
<body>
<div style="padding-top: 5%; padding-left: 25%">
    <c:choose>
        <c:when test="${exception eq addBookException}">
            <h2><fmt:message key="key.addBookExc" bundle="${local}"/></h2>
    </c:when>
        <c:when test="${exception eq addAuthorException}">
            <h2><fmt:message key="key.addAuthorExc" bundle="${local}"/></h2>
        </c:when>
        <c:when test="${exception eq addGenreException}">
            <h2><fmt:message key="key.addGenreExc" bundle="${local}"/></h2>
        </c:when>
        <c:when test="${exception eq authorizationException}">
            <h2><fmt:message key="key.authorizationExc" bundle="${local}"/></h2>
        </c:when>
        <c:when test="${exception eq addUserException}">
            <h2><fmt:message key="key.addNewUserExc" bundle="${local}"/></h2>
        </c:when>
    </c:choose>
</div>
</body>
</html>
