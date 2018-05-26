<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.username" var="username" />
    <div>Authenticated as ${username}</div>
</sec:authorize>