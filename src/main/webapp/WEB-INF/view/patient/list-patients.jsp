<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List of patients</title>
</head>
<body>
<form:form action="${pageContext.request.contextPath}/logout" method="POST">
	<input type="submit" value="Logout"/>
</form:form>
<h2>List of your patients</h2>

<c:if test="${!empty patients}">
	<c:forEach items="${patients}" var="patient">
		<a href="<c:url value="list-appointments?id=${patient.id}"/>">
		  ${patient.firstName} ${patient.lastName}
		</a><br>
	</c:forEach>
</c:if>

<a href="<c:url value="add-patient"/>">Add new patient</a>
</body>
</html>