<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${empty book.id ? 'Add New Book' : 'Edit Book'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <nav class="topnav">
        <a href="${pageContext.request.contextPath}/books" class="active">Books</a>
        <a href="${pageContext.request.contextPath}/authors">Authors</a>
    </nav>

    <h1>${empty book.id ? 'Add New Book' : 'Edit Book'}</h1>

    <c:set var="action" value="${empty book.id
        ? pageContext.request.contextPath.concat('/books')
        : pageContext.request.contextPath.concat('/books/update/').concat(book.id)}"/>

    <form:form action="${action}" method="post" modelAttribute="book">
        <div class="form-group">
            <label for="title">Title</label>
            <form:input path="title" id="title"/>
            <form:errors path="title" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <label for="genre">Genre</label>
            <form:input path="genre" id="genre"/>
            <form:errors path="genre" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <label for="authorId">Author</label>
            <select id="authorId" name="authorId" required>
                <option value="">-- Select Author --</option>
                <c:forEach items="${authors}" var="a">
                    <option value="${a.id}"
                        ${book.author != null && book.author.id == a.id ? 'selected' : ''}>
                        <c:out value="${a.name}"/> (<c:out value="${a.nationality}"/>)
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                ${empty book.id ? 'Save Book' : 'Update Book'}
            </button>
            <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">Cancel</a>
        </div>
    </form:form>
</div>
</body>
</html>
