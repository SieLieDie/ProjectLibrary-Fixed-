<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/Style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <fmt:setLocale value="${langLocal}"/>
    <fmt:setBundle basename="InterfaceValue" var="local"/>
    <title>ProjectLibrary</title>
</head>
<header>
    <div id="app" class="container" style="z-index: 2">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="/do/goToMainPage"><fmt:message key="key.library" bundle="${local}"/></a>
            <div id="navbarNavDropdown" class="navbar-collapse collapse">
                <ul class="navbar-nav mr-auto">
                    <form class="form-inline my-2 my-lg-0" action="/do/searchBookByName">
                        <input class="form-control mr-sm-2" type="text" placeholder="<fmt:message
                                key="key.searchBook" bundle="${local}"/>" id="bookName"
                               name="bookName">
                        <button class="btn btn-outline-info my-2 my-sm-0" type="submit"><fmt:message key="key.search"
                                                                                                     bundle="${local}"/></button>
                    </form>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="genres" data-toggle="dropdown"><fmt:message
                                key="key.genres" bundle="${local}"/></a>
                        <div class="dropdown-menu">
                            <c:forEach items="${genreList}" var="genre">
                                <a class="dropdown-item" href="/do/showBookByGenre?genreID=${genre.id}"
                                   name="${genre}">
                                    <c:out value="${genre.name}">${genre.name}</c:out>
                                </a>
                            </c:forEach>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="author" data-toggle="dropdown"><fmt:message
                                key="key.authors" bundle="${local}"/></a>
                        <div class="dropdown-menu">
                            <c:forEach items="${authorList}" var="author">
                                <a class="dropdown-item" href="/do/showBookByAuthor?authorID=${author.id}"
                                   name="${author}">
                                    <c:out value="${author.name}">${author.name}</c:out>
                                </a>
                            </c:forEach>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <c:choose>
                        <c:when test="${userRoleID == 1}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="add"
                           data-toggle="dropdown"><fmt:message key="key.add" bundle="${local}"/></a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="#"
                               onclick="document.getElementById('addBook').style.display = 'block'"><fmt:message
                                    key="key.book" bundle="${local}"/></a>
                            <a class="dropdown-item" href="#"
                               onclick="document.getElementById('addAuthor').style.display = 'block'"><fmt:message
                                    key="key.author" bundle="${local}"/></a>
                            <a class="dropdown-item" href="#"
                               onclick="document.getElementById('addGenre').style.display = 'block'"><fmt:message
                                    key="key.genre" bundle="${local}"/></a>
                        </div>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="remove"
                           data-toggle="dropdown"><fmt:message key="key.remove" bundle="${local}"/></a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="#"
                               onclick="document.getElementById('removeBook').style.display = 'block'"><fmt:message
                                    key="key.book" bundle="${local}"/></a>
                            <a class="dropdown-item" href="#"
                               onclick="document.getElementById('removeAuthor').style.display = 'block'"><fmt:message
                                    key="key.author" bundle="${local}"/></a>
                            <a class="dropdown-item" href="#"
                               onclick="document.getElementById('removeGenre').style.display = 'block'"><fmt:message
                                    key="key.genre" bundle="${local}"/></a>
                        </div>
                    </li>
                    </c:when>
                    <c:when test="${userRoleID == 2}">
                        <a class="nav-link" href="/do/showFavorite"><fmt:message key="key.showFavorite"
                                                                                 bundle="${local}"/></a>
                    </c:when>
                    </c:choose>
                    </li>
                </ul>

                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="lang" data-toggle="dropdown"><fmt:message
                                key="key.lang" bundle="${local}"/></a>
                        <div class="dropdown-menu">
                            <c:forEach items="${langList}" var="lang">
                                <a class="dropdown-item" href="/do/changeLang?langID=${lang.id}"
                                   name="${lang}">${lang.name}</a>
                            </c:forEach>
                        </div>
                    </li>
                    <c:choose>
                        <c:when test="${userRoleID == null}">
                            <li class="nav-item">
                                <a class="nav-link" href="#"
                                   onclick="document.getElementById('autDiv').style.display = 'block'"><fmt:message
                                        key="key.authorization" bundle="${local}"/></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#"
                                   onclick="document.getElementById('regDiv').style.display = 'block'"><fmt:message
                                        key="key.register" bundle="${local}"/></a>
                            </li>
                        </c:when>
                        <c:when test="${userRoleID != null}">
                            <li class="nav-item">
                                <a class="nav-link">${userName}</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/do/exitFromSession"><fmt:message
                                        key="key.exit" bundle="${local}"/></a>
                            </li>
                        </c:when>
                    </c:choose>
                </ul>
            </div>
        </nav>
    </div>
</header>
<body>
<c:choose>
    <c:when test="${userRoleID == 1}">
        <div id="addBook" style="display: none">
            <jsp:include page="AddBook.jsp"/>
        </div>
        <div id="addAuthor" style="display: none">
            <jsp:include page="AddAuthor.jsp"/>
        </div>
        <div id="addGenre" style="display: none">
            <jsp:include page="AddGenre.jsp"/>
        </div>
        <div id="removeBook" style="display: none">
            <jsp:include page="RemoveBook.jsp"/>
        </div>
        <div id="removeAuthor" style="display: none">
            <jsp:include page="RemoveAuthor.jsp"/>
        </div>
        <div id="removeGenre" style="display: none">
            <jsp:include page="RemoveGenre.jsp"/>
        </div>
    </c:when>
    <c:when test="${userRoleID == null}">
        <div id="autDiv" style="display: none">
            <jsp:include page="/index.jsp"/>
        </div>
        <div id="regDiv" style="display: none">
            <jsp:include page="RegistrationPage.jsp"/>
        </div>
    </c:when>
</c:choose>
<c:if test="${find == false}">
    <h2 style="padding-left: 30%; padding-top: 5%"><fmt:message key="key.find" bundle="${local}"/></h2>
</c:if>
<div>
    <c:forEach items="${bookList}" var="book">
        <div class="grid-container" style="position: relative">
            <div style="border: dashed">
                <img src="../css/image/bookImage.png" style="width: 100%; height: 100%"/>
            </div>
            <div class="book">
                <div class="name">${book.name}</div>
                <div class="author-genre">
                    <fmt:message key="key.author" bundle="${local}"/>: ${book.author}
                    <br/><fmt:message key="key.genre" bundle="${local}"/>: ${book.genre}
                </div>
                <div class="inStock">
                    <fmt:message key="key.inStock" bundle="${local}"/>: ${book.inStock}
                    <c:if test="${userRoleID == 2}">
                        <c:choose>
                            <c:when test="${book.isFavorite == false}">
                                <br/>
                                <a href="/do/addToFavorite?bookID=${book.id}"><fmt:message key="key.addToFavorite"
                                                                                           bundle="${local}"/></a>
                            </c:when>
                            <c:when test="${book.isFavorite == true}">
                                <br/>
                                <a href="/do/removeFromFavorite?bookID=${book.id}"><fmt:message key="key.remove"
                                                                                                bundle="${local}"/></a>
                            </c:when>
                        </c:choose>
                    </c:if>
                </div>
                <div class="description">${book.description}</div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
<footer>
</footer>
</html>