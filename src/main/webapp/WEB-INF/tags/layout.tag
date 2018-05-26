<%@tag description="Main layout" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--<%@attribute name="layout" required="true"%>--%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>


<html lang="en">
<head>
    <link href="/webjars/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <script src="/webjars/jquery/1.9.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="/webjars/momentjs/2.22.1/min/moment.min.js"></script>
</head>
<body>
    <div class="container">


        <div class="row col-xs-12 header">
            <sec:authorize access="isAuthenticated() and hasRole('ADMIN')">
                <h1 name="col-sm-6" class="text-center">
                    Challenges administration
                </h1>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <h1 name="col-sm-6" class="text-center">
                    The Force will be with you. Always!
                </h1>
            </sec:authorize>
            <div class="col-sm-2 pull-right">
                <jsp:include page="../jsp/partials/principal.jsp" />
            </div>
        </div>

        <div class="clearfix"></div>
        <hr/>

        <div class="row col-xs-12">
            <jsp:doBody/>
        </div>

        <div class="clearfix"></div>
        <hr/>

        <div name="footer" class="text-center" id="copyright">
            Copyright <a href="https://github.com/razorcd">Cristian Dugacicu</a>.
        </div>
    </div>

    <jsp:include page="../jsp/partials/flashMessage.jsp" />
</body>
</html>