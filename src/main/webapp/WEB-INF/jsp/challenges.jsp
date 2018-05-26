<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:layout>
    <jsp:body>
        <div class="row">
            <a href="${pageContext.request.contextPath}/admin/challengeSources/new" class="btn btn-default" role="button">Create new challenge</a>
        </div>
        <table class="table table-hover">
            <thead>
            <tr>
                <%--<th>Id</th>--%>
                <th>Name</th>
                <th>Expected Solvers Name</th>
                <th>Expected Solvers Email</th>
                <th>Created at</th>
                <th>Duration</th>
                <th>Resolved</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <%--@elvariable id="challenges" type="java.util.List"--%>
            <c:forEach items="${challenges}" var="challenge">
                <tr>
                    <%--<td><c:out value="${challenge.challengeSource.challengeId.id}"/></td>--%>
                    <td><c:out value="${challenge.challengeSource.projectName}"/></td>
                    <td><c:out value="${challenge.challengeSource.expectedSolversName}"/></td>
                    <td><c:out value="${challenge.challengeSource.expectedSolversEmail}"/></td>
                    <td><c:out value="${challenge.challengeSource.createTimestamp}"/></td>
                    <td><c:out value="${challenge.challengeSource.allowedDurationMin}"/> minutes</td>
                    <td class="text-center">
                        <c:if test="${challenge.hasSolution()}">
                            <span class="glyphicon glyphicon-ok"></span>
                        </c:if>
                        <c:if test="${not challenge.hasSolution()}">
                            <span class="glyphicon glyphicon-remove"></span>
                        </c:if>
                    </td>
                    <td><a href="/challengeSources/${challenge.challengeSource.challengeId.id}">Details</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </jsp:body>
</t:layout>
