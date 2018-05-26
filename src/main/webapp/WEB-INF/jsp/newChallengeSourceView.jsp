<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<t:layout>
    <jsp:body>
        <div class="container">
            <div class="col-sm-3">
                <a href="${pageContext.request.contextPath}/admin/challengeSources" class="btn btn-default left" role="button">< Back to challenge list</a>
            </div>
            <div class="col-sm-6">
                <h2 class="text-center">Create new challenge</h2>
            </div>
        </div>
        <br/>
        <div class="container col-sm-10 col-sm-offset-1" style="font-size: 1.2em">
            <%--@elvariable id="newChallengeSource" type="om.challenge.provider.challengeprovider.model.ChallengeSource"--%>
            <form:form cssClass="form-horizontal" method="POST" action="/admin/challengeSources" modelAttribute="newChallengeSource">
                <div class="form-group">
                    <label for="projectName" class="col-sm-4 control-label">Challenge name:</label>
                    <div class="col-sm-6">
                        <form:input path="projectName" type="text" class="form-control" id="projectName"/>
                        <form:errors path="projectName" cssClass="error help-block label label-danger" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="expectedSolversName" class="col-sm-4 control-label">Expected Solvers Name:</label>
                    <div class="col-sm-6">
                        <form:input path="expectedSolversName" type="text" class="form-control" id="expectedSolversName"/>
                        <form:errors path="expectedSolversName" cssClass="error help-block label label-danger" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="expectedSolversEmail" class="col-sm-4 control-label">Expected Solvers Email:</label>
                    <div class="col-sm-6">
                        <form:input path="expectedSolversEmail" type="text" class="form-control" id="expectedSolversEmail"/>
                        <form:errors path="expectedSolversEmail" cssClass="error help-block label label-danger" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="allowedDurationMin" class="col-sm-4 control-label">Allowed duration:</label>
                    <div class="col-sm-4">
                        <form:input path="allowedDurationMin" type="text" class="form-control" id="allowedDurationMin" placeholder="e.g.120" />
                        <form:errors path="allowedDurationMin" cssClass="error help-block label label-danger" />
                    </div>
                    <div class="col-sm-2">minutes</div>
                </div>
                <div class="form-group">
                    <label for="description" class="col-sm-4 control-label">Description:</label>
                    <div class="col-sm-6">
                        <form:textarea path="description" rows="15" cssStyle="width:100%;resize: vertical;"></form:textarea>
                        <form:errors path="description" cssClass="error help-block label label-danger" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-sm-6">
                        <button type="submit" class="btn btn-default">Create challenge</button>
                    </div>
                </div>
            </form:form>
        </div>
    </jsp:body>
</t:layout>
