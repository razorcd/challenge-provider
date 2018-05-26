<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:layout>
    <jsp:body><%--@elvariable id="challenge" type="com.challenge.provider.challengeprovider.domain.Challenge"--%>
        <div class="row">
            <sec:authorize access="isAuthenticated() and hasRole('ADMIN')">
                <a href="${pageContext.request.contextPath}/admin/challengeSources" class="btn btn-default" role="button">< Back to challenge list</a>
            </sec:authorize>

            <sec:authorize access="!isAuthenticated()">
                <%--TIMER and START CHALLENGE--%>
                <c:if test="${empty challenge.challengeSolution}">
                    <c:if test="${!challenge.getChallengeSource().isStarted()}">
                        <a href="${pageContext.request.contextPath}/challengeSources/${challenge.challengeSource.challengeId.id}/startChallenge"
                                    class="btn btn-danger pull-right" role="button"
                                    onclick="return confirm('Are you sure you want to start this challenge?');">Start challenge</a>
                    </c:if>
                    <c:if test="${challenge.getChallengeSource().isStarted()}">
                        <div class="pull-right">
                            <jsp:include page="../jsp/partials/timerCountdown.jsp" />
                        </div>
                    </c:if>
                </c:if>
            </sec:authorize>
        </div>

        <%--CHALLENGE SOURCE VIEW--%>
        <div class="row col-sm-8 col-sm-offset-2" style="font-size: 1.5em">
            <div class="col-sm-3">Challenge:</div>
            <div class="col-sm-9">${challenge.challengeSource.projectName}</div>
            <div class="clearfix"></div>


            <div class="col-sm-3"></div>
            <div class="col-sm-9"><small>(${challenge.challengeSource.challengeId.id})</small></div>
            <div class="clearfix"></div>


            <div class="col-sm-3">Challenge for:</div>
            <div class="row col-sm-9">
                <div class="col-xs-12">${challenge.challengeSource.expectedSolversName}</div>
                <div class="col-xs-12">
                    <a href="mailto:${challenge.challengeSource.expectedSolversEmail}">${challenge.challengeSource.expectedSolversEmail}</a>
                </div>
            </div>
            <div class="clearfix"></div>

            <div class="col-sm-3">Allowed time:</div>
            <div class="col-sm-9">${challenge.challengeSource.allowedDurationMin} minutes</div>
            <div class="clearfix"></div>


            <div class="col-sm-3">Created at:</div>
            <div class="col-sm-9">${challenge.challengeSource.createTimestamp}</div>
            <div class="clearfix"></div>

            <c:if test="${not empty challenge.challengeSource.startedTimestamp}">
                <div class="col-sm-3">Started at:</div>
                <div class="col-sm-9">${challenge.challengeSource.startedTimestamp}</div>
                <div class="clearfix"></div>
            </c:if>

            <sec:authorize access="isAuthenticated() and hasRole('ADMIN')">
                <div class="col-sm-3">Description:</div>
                <div class="col-sm-9">
                    <pre class="text-left">${challenge.challengeSource.description}</pre>
                </div>
            </sec:authorize>

            <sec:authorize access="!isAuthenticated()">
                <c:if test="${!challenge.challengeSource.isStarted()}">
                    <br/>
                    <div class="col-sm-12 text-center">Challenge description will be visible after start.</div>
                    <br/><br/>
                </c:if>
                <c:if test="${challenge.challengeSource.isStarted()}">
                    <div class="col-sm-12">
                        <pre class="text-left">${challenge.challengeSource.description}</pre>
                    </div>
                </c:if>
            </sec:authorize>

            <sec:authorize access="isAuthenticated() and hasRole('ADMIN')">
                <div class="col-sm-3"><strong>Link:</strong> <br/> <small style="font-size: 0.6em">(send to candidate)</small></div>
                <div class="col-sm-9">
                    <small class="text-left">
                        <span id="domain"></span>
                        <script>
                            // display current URL in dom
                            var domainEl = document.getElementById("domain");
                            domainEl.innerText = window.location.href;
                        </script>
                    </small>
                </div>
            </sec:authorize>
        </div>

        <c:if test="${!challenge.hasSolution() and challenge.getChallengeSource().isStarted()}">
            <div class="row">
                <div class="col-xs-12">
                    <hr/>
                </div>
            </div>


            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <h2 class="text-center">Add your solution</h2>
                    <br/>
                </div>
            </div>


            <div class="row col-sm-10 col-sm-offset-1" style="font-size: 1.2em">

                <%--FORM TO ADD A SOLUTION--%>

                <%--@elvariable id="newChallengeSolution" type="com.challenge.provider.challengeprovider.model.ChallengeSolution"--%>
                <form:form cssClass="form-horizontal" method="POST" action="${pageContext.request.contextPath}/challengeSources/${challenge.challengeSource.challengeId.id}/solution" enctype="multipart/form-data" modelAttribute="newChallengeSolution">
                        <div class="form-group">
                            <label for="solversName" class="col-sm-4 control-label">Resolved by:</label>
                            <div class="col-sm-6">
                                <form:input path="solversName" type="text" class="form-control" id="solversName"/>
                                <form:errors path="solversName" cssClass="error help-block label label-danger" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="solversEmail" class="col-sm-4 control-label">Solvers email:</label>
                            <div class="col-sm-6">
                                <form:input path="solversEmail" type="text" class="form-control" id="solversEmail"/>
                                <form:errors path="solversEmail" cssClass="error help-block label label-danger" />

                            </div>
                        </div>
                        <div class="form-group">
                            <label for="description" class="col-sm-4 control-label">Description:</label>
                            <div class="col-sm-6">
                                <form:textarea path="description" rows="15" cssStyle="width:100%;resize: vertical;"></form:textarea>
                                <form:errors path="description" cssClass="error help-block label label-danger" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="solutionFile" class="col-sm-4 control-label">File:</label>
                            <div class="col-sm-6">
                                <input type="file" name="solutionFile" id="solutionFile"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-6">
                                <button type="submit" class="btn btn-default">Create challenge</button>
                            </div>
                        </div>
                    </form:form>
            </div>

        </c:if>

        <c:if test="${challenge.hasSolution()}">

            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <h2 class="text-center">Solution</h2>
                    <br/>
                </div>
            </div>

            <%--CHALLENGE SOLUTION VIEW--%>
            <jsp:include page="../jsp/partials/solution.jsp" />

        </c:if>
    </jsp:body>
</t:layout>
