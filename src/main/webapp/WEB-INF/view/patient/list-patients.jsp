<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/navbarStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/listPatientsStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/footerStyle.css" />">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
<title>List of patients</title>
</head>
<body>
<div class="navbar">
		<a href="${pageContext.request.contextPath}/doctor/"><i class="fas fa-home"></i></a>
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<button type="submit" class="logout"><i class="fas fa-door-open"></i></button>
		</form:form>
</div>
<div class="wrapper">
	<h2>List of your patients</h2>
	<div id="firstColumn">
		<c:if test="${!empty patients}">
			<c:forEach items="${patients}" var="patient">
				<!-- 
				<a href="<c:url value="list-appointments?id=${patient.id}"/>">
				   <i class="fas fa-hospital-user"></i> ${patient.firstName} ${patient.lastName}
				</a>
				 -->
				 <button class="patientName" onclick=renderInfo(${patient.id})>
				 	<i class="fas fa-hospital-user"></i> ${patient.firstName} ${patient.lastName}
				 </button>
			</c:forEach>
		</c:if>
		
		<a id="addPatient" href="<c:url value="add-patient"/>"><i class="fas fa-plus-circle"></i> Add new patient</a>
	</div>
	<div id="secondColumn">
	</div>
</div>
<div class="footer">
	<a>Some info here</a>
</div>
<script src="<c:url value="/resources/static/js/listPatientsScript.js"/>"></script>
</body>
</html>