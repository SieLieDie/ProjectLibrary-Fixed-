<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/Style.css" type="text/css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <title>Authorization</title>
</head>
<body>
<div class="container, dialogForm">
    <h2>Authorization</h2>
    <form action="/do/authorization" method="post">
        <div class="authorization">
            <div class="form-group, login">
                <label for="login">Login:</label>
                <input type="text" class="form-control" id="login" placeholder="Enter login" name="login" required
                       maxlength="10">
            </div>
            <div class="form-group, password">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" placeholder="Enter password" name="password"
                       required maxlength="8" minlength="6">
            </div>
            <br/>
            <div>
                <button type="submit" class="btn btn-primary">Enter</button>
                <a href="/do/goToMainPage" style="padding-left: 20%">Enter like guest</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>
