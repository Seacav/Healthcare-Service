<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add appointment</title>
	<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/navbarStyle.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/footerStyle.css" />">
	<link rel="stylesheet" href="<c:url value="/resources/static/css/addAppntStyle.css" />">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
</head>
<body>
<div class="navbar">
		<a href="${pageContext.request.contextPath}/doctor/"><i class="fas fa-home"></i></a>
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<button type="submit" class="logout"><i class="fas fa-door-open"></i></button>
		</form:form>
</div>
<div class="wrapper">
	<h2>Search treatment</h2>
	<form:form method="POST" action="addAppointment">
		<input type="hidden" name="patientId" value="${patientId}"/>
		<input type="text" name="treatmentName" id="treatmentName"/>
		<select name="treatmentType">
			<option value="DRUG">Drug</option>
			<option value="PROCEDURE">Procedure</option>
		</select>
		<input type="submit" value="Search" id="submit-button"/>
	</form:form>
	<c:if test="${!empty searchSuccess }">
		<c:choose>
			<c:when test="${searchSuccess eq false }">
				<a>Sorry, couldn't find anything.</a>
			</c:when>
			<c:when test="${searchSuccess eq true }">
				<c:forEach items="${treatments}" var="treatment">
					<a href="<c:url value="addAppointment/showForm?patientId=${patientId}&treatmentId=${treatment.id}&isDrug=${treatment.type eq 'DRUG'}"/>">
						${treatment.name}</a><br> 
				</c:forEach>
			</c:when>
		</c:choose>
	</c:if>
</div>
<div class="footer">
	<a>Some info here</a><br>
</div>
</body>
</html>