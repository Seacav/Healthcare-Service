<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/navbarStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/footerStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/listAppntStyle.css" />">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
<title>Appointments</title>
</head>
<body>
<div class="navbar">
		<a href="${pageContext.request.contextPath}/doctor/"><i class="fas fa-home"></i></a>
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<button type="submit" class="logout"><i class="fas fa-door-open"></i></button>
		</form:form>
</div>
<div class="wrapper">
	<h2>Appointments of patient ${patient.firstName} ${patient.lastName}</h2>
	<form:form method="POST" action="dischargePatient?patientId=${patient.id}">
		<button id="discharge" type="submit"><i class="fas fa-minus-square"></i> Discharge patient</button>
	</form:form>
	<c:forEach items="${appnt}" var="appnt">
		<a href="<c:url value="list-appointments/show?appointmentId=${appnt.id}"/>"
				>${appnt.treatment.type} ${appnt.treatment.name} ${appnt.created_at}</a>
	</c:forEach>
	<a id="addAppnt" href="<c:url value="addAppointment?patientId=${patient.id}"/>">
		<i class="fas fa-calendar-plus"></i> Add new appointment</a>
</div>
<div class="footer">
	<a>Some info here</a><br>
</div>
</body>
</html>