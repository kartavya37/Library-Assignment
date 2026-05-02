<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book Library</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <nav class="topnav">
        <a href="${pageContext.request.contextPath}/books" class="active">Books</a>
        <a href="${pageContext.request.contextPath}/authors">Authors</a>
    </nav>

    <h1>Book Library</h1>

    <c:if test="${not empty success}">
        <div class="success-message">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <a href="${pageContext.request.contextPath}/books/new" class="btn btn-primary">+ Add New Book</a>

    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Title</th>
            <th>Genre</th>
            <th>Author</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${books}" var="book" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td><c:out value="${book.title}"/></td>
                <td><c:out value="${book.genre}"/></td>
                <td><c:out value="${book.author.name}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/books/edit/${book.id}" class="btn btn-edit">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
