<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
    .alert {
        position: fixed;
        top: 10px;
        right: 10px;
        min-width: 20%;
    }
</style>


<%--@elvariable id="flashMessageSuccess" type="java.lang.String "--%>
<c:if test="${not empty flashMessageSuccess}">
    <div class="alert alert-success alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>Success!</strong>  ${flashMessageSuccess}.
    </div>
</c:if>

<%--@elvariable id="flashMessageError" type="java.lang.String"--%>
<c:if test="${not empty flashMessageError}">
    <div class="alert alert-danger alert-dismissible" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>Error!</strong>  ${flashMessageError}.
    </div>
</c:if>
