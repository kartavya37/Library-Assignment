<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${empty author.id ? 'Add New Author' : 'Edit Author'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <nav class="topnav">
        <a href="${pageContext.request.contextPath}/books">Books</a>
        <a href="${pageContext.request.contextPath}/authors" class="active">Authors</a>
    </nav>

    <h1>${empty author.id ? 'Add New Author' : 'Edit Author'}</h1>

    <c:set var="action" value="${empty author.id
        ? pageContext.request.contextPath.concat('/authors')
        : pageContext.request.contextPath.concat('/authors/update/').concat(author.id)}"/>

    <form:form action="${action}" method="post" modelAttribute="author">
        <div class="form-group">
            <label for="name">Name</label>
            <form:input path="name" id="name"/>
            <form:errors path="name" cssClass="field-error"/>
        </div>

        <div class="form-group">
            <label for="nationality">Nationality</label>
            <form:input path="nationality" id="nationality"/>
            <form:errors path="nationality" cssClass="field-error"/>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                ${empty author.id ? 'Save Author' : 'Update Author'}
            </button>
            <a href="${pageContext.request.contextPath}/authors" class="btn btn-secondary">Cancel</a>
        </div>
    </form:form>
</div>
</body>
</html>
