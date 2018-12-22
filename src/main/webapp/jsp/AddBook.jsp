<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/Style.css" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <fmt:setLocale value="${langLocal}"/>
    <fmt:setBundle basename="InterfaceValue" var="local"/>
    <title>Add book</title>
</head>
<body>
<div class="dialogForm">
    <h2><fmt:message key="key.addBook" bundle="${local}"/></h2>
    <form action="/do/addBook" method="post">
        <div class="form-group" style="line-height: 1.5">
            <label for="bookName"><fmt:message key="key.bookName" bundle="${local}"/>:</label>
            <input type="text" class="form-control" id="bookName" name="bookName" required maxlength="50"
                   placeholder="<fmt:message key="key.bookNamePlaceholder" bundle="${local}"/>">
        </div>
        <div class="form-group" style="line-height: 0.5">
            <label for="author"><fmt:message key="key.author" bundle="${local}"/>:</label>
            <select id="author" name="author">
                <c:forEach items="${authorList}" var="author">
                    <option value="${author.coreID}">${author.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group" style="line-height: 0.5">
            <label for="genre"><fmt:message key="key.genre" bundle="${local}"/>:</label>
            <select id="genre" name="genre">
                <c:forEach items="${genreList}" var="genre">
                    <option value="${genre.coreID}">${genre.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group" style="line-height: 0.5">
            <label for="inStock"><fmt:message key="key.inStock" bundle="${local}"/>:</label>
            <input type="text" class="form-control" id="inStock" name="inStock" required maxlength="3"
                   pattern="[^0\W]\d+" placeholder="<fmt:message key="key.bookInStokePlaceholder" bundle="${local}"/>">
        </div>
        <div class="form-group" style="line-height: 0.9">
            <label for="description"><fmt:message key="key.description" bundle="${local}"/>:</label>
            <textarea name="description" id="description" cols="33" rows="10" maxlength="400"
                      placeholder="<fmt:message key="key.bookDescriptionPlaceholder" bundle="${local}"/>"></textarea>
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
