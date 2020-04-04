<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Appointments</title>
</head>
<body>
<h2>Appointments of patient ${patient.firstName} ${patient.lastName}</h2>
<form:form method="POST" action="dischargePatient?patientId=${patient.id}">
	<input type="submit" value="Discharge patient">
</form:form>
<c:forEach items="${appnt}" var="appnt">
	<p><a href="<c:url value="list-appointments/show?appointmentId=${appnt.id}"/>"
			>${appnt.treatment.type} ${appnt.treatment.name} ${appnt.created_at}</a></p>
</c:forEach>
<a href="<c:url value="addAppointment?patientId=${patient.id}"/>">Add new appointment</a>
</body>
</html>