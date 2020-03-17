<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Appointments</title>
</head>
<body>
<h2>Appointments of patient ${patient.firstName} ${patient.lastName}</h2>
<c:forEach items="${appnt}" var="appnt">
	<p>${appnt.treatmentId.type} ${appnt.treatmentId.name} ${appnt.created_at}</p>
	<p>${appnt.pattern } ${appnt.dosage}</p><br>
</c:forEach>
<a href="<c:url value="addAppointment?patientId=${patient.id}"/>">Add new appointment</a>
</body>
</html>