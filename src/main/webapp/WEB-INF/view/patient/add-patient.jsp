<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add new patient</title>
</head>
<body>
<h2>Add new patient</h2>
<c:if test="${empty patient}">
	<h3>Please enter insurance number</h3>
	<form:form method="POST" action="add-patient">
		<input type="text" name="insNumber" id="ins"/><br><br>
		<input type="submit" value="Submit" id="submit-button"/>
	</form:form>
</c:if>
<c:if test="${!empty patient}">
	<p>Patient does exist!</p>
	<!-- Show patient info -->
	<!-- if his status == 'discharged' then show href to method addExisitingPatient-->
	<!-- if his status == 'treated' then show 'Sorry, can't add patient, he already has therapist'-->
	<p>${patient.firstName} ${patient.lastName}</p>
	<p>Insurance number: ${patient.insNumber}</p>
	<c:choose>
		<c:when test="${patient.status eq 'DISCHARGED' }">
			<form:form method="POST" action="addExistingPatient?id=${patient.id}">
				Enter patient's diagnosis:
				<input type="text" name="diagnosis"/>
				<input type="submit" value="Add patient">
			</form:form>
		</c:when>
		<c:when test="${patient.status eq 'TREATED'}">
			Sorry, this user is already assigned to a therapist.
		</c:when>
	</c:choose>
</c:if>
<script>
	const subButton = document.querySelector('#submit-button');
	subButton.disabled = true;
	const reg = new RegExp('^[0-9]+$');
	document.querySelector('#ins').addEventListener('input', function(e){
		if (reg.test(e.target.value)) {
			subButton.disabled = false;
		} else {
			subButton.disabled = true;
		}
	});
</script>
</body>
</html>