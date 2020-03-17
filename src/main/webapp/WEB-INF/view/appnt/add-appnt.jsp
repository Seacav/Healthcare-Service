<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add appointment</title>
</head>
<body>
<h2>Search treatment</h2>
<form:form method="POST" action="addAppointment">
	<input type="hidden" name="patientId" value="${patientId}"/>
	<input type="text" name="treatmentName"/>
	<select name="treatmentType">
		<option value="DRUG">Лекарство</option>
		<option value="PROCEDURE">Процедура</option>
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
				<a href="<c:url value="addAppointment/showForm?patientId=${patientId}&treatmentId=${treatment.id}"/>">${treatment.name}</a><br> 
			</c:forEach>
		</c:when>
	</c:choose>
</c:if>
</body>
</html>