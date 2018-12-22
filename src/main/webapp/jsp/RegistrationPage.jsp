<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="css/Style.css" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <fmt:setLocale value="${langLocal}"/>
    <fmt:setBundle basename="InterfaceValue" var="local"/>
    <title>RegistrationPage</title>
</head>
<body>
<div class="dialogForm">
    <h2>Registration</h2>
    <form action="/do/registration" method="post">
        <div class="authorization">
            <div class="form-group" style="line-height: 1.5">
                <label for="login"><fmt:message key="key.login" bundle="${local}"/></label>
                <input type="text" class="form-control" id="login" required maxlength="10"
                       placeholder="<fmt:message key="key.enterYourLogin" bundle="${local}"/>" name="login">
            </div>
            <div class="form-group" style="line-height: 0.5">
                <label for="password"><fmt:message key="key.password" bundle="${local}"/></label>
                <input type="password" class="form-control" id="password" required maxlength="10" minlength="6"
                       placeholder="<fmt:message key="key.enterYourPassword" bundle="${local}"/>"
                       name="password">
            </div>
            <div class="form-group" style="line-height: 0.5">
                <label for="firstName"><fmt:message key="key.firstName" bundle="${local}"/></label>
                <input type="text" class="form-control" id="firstName" required maxlength="15"
                       placeholder="<fmt:message key="key.enterYourFirstName" bundle="${local}"/>"
                       name="firstName">
            </div>
            <div class="form-group" style="line-height: 0.5">
                <label for="secondName"><fmt:message key="key.secondName" bundle="${local}"/></label>
                <input type="text" class="form-control" id="secondName" required maxlength="15"
                       placeholder="<fmt:message key="key.enterYourSecondName" bundle="${local}"/>"
                       name="secondName">
            </div>
            <div>
                <button type="submit" class="btn btn-primary"><fmt:message key="key.enter" bundle="${local}"/></button>
                <a href="/do/goToMainPage" style="padding-left: 20%"><fmt:message key="key.cancel" bundle="${local}"/></a>
            </div>
        </div>
    </form>
</div>
</body>
</html>
