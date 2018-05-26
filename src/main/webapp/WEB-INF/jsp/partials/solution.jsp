<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row col-sm-8 col-sm-offset-2" style="font-size: 1.5em">
    <%--<div class="col-sm-3"></div>--%>
    <%--<div class="col-sm-9"><small>(${challenge.challengeSource.challengeId.id})</small></div>--%>

    <div class="col-sm-3">Solved by:</div>
    <div class="col-sm-9">
        <div class="col-xs-12">${challenge.challengeSolution.solversName}</div>
        <div class="col-xs-12">
            <a href="mailto:${challenge.challengeSolution.solversEmail}">${challenge.challengeSolution.solversEmail}</a>
        </div>
    </div>

    <div class="col-sm-3">Created at:</div>
    <div class="col-sm-9">${challenge.challengeSolution.createTimestamp}</div>
    <div class="clearfix"></div>

    <div class="col-sm-3">Description:</div>
    <div class="col-sm-9">
        <pre class="text-left">${challenge.challengeSolution.description}</pre>
    </div>

    <c:if test="${not empty challenge.challengeSolution.uploadedFileName}">
        <div class="col-sm-3">Uploaded file:</div>
        <div class="col-sm-9">${challenge.challengeSolution.uploadedFileName}</div>
    </c:if>
</div>