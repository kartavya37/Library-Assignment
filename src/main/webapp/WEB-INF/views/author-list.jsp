<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Authors</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <nav class="topnav">
        <a href="${pageContext.request.contextPath}/books">Books</a>
        <a href="${pageContext.request.contextPath}/authors" class="active">Authors</a>
    </nav>

    <h1>Authors</h1>

    <c:if test="${not empty success}">
        <div class="success-message">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <a href="${pageContext.request.contextPath}/authors/new" class="btn btn-primary">+ Add New Author</a>

    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Name</th>
            <th>Nationality</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${authors}" var="a" varStatus="status">
            <tr>
                <td>${status.count}</td>
                <td><c:out value="${a.name}"/></td>
                <td><c:out value="${a.nationality}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/authors/edit/${a.id}" class="btn btn-edit">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
