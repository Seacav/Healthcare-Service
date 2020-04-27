<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Show appointment</title>
<link rel="stylesheet" href="<c:url value="/resources/static/css/showAppntStyle.css" />" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/navbarStyle.css" />">
<link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/footerStyle.css" />">
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
	<h2>Appointment details</h2>
	<div class="info" id="appntInfo">
		<form:form method="POST" action="cancel?id=${appnt.id}&patientId=${appnt.patient.id }">
			<button type="submit" class="cancelBtn"><i class="fas fa-window-close"></i> Cancel appointment</button>
		</form:form>
		<p>Type: ${appnt.treatment.type}</p>
		<p>Name: ${appnt.treatment.name }</p>
		<c:if test="${appnt.treatment.type eq 'DRUG'}">
			<p>Dosage: ${appnt.dosage }</p>
		</c:if>
		<p>Due date: ${appnt.dueDate }</p>
		<c:if test="${appnt.treatment.type eq 'DRUG'}">
			<button class="changeBtn" id="dosageBtn"><i class="fas fa-syringe"></i> Change dosage</button>
		</c:if>
		<button class="changeBtn" id="patternBtn"><i class="far fa-clock"></i> Change pattern</button>
	</div>
	<div id="dosageModal" class="modal">
	    <div class="modal-content">
	        <div class="modal-header">
	            <span class="close" id="closeDsg">&times;</span>
	            <h2>Enter new dosage:</h2>
	        </div>
	        <div class="modal-body">
	            <form:form action="changeDosage?id=${appnt.id}" method="post">
	                <input type="text" name="dosage">
	                <input type="submit" value="Submit">
	            </form:form>
	        </div>
	    </div>
	</div>
	
	<div id="patternModal" class="modal">
	    <div class="modal-content">
	        <div class="modal-header">
	            <span class="close" id="closePtrn">&times;</span>
	            <h2>Choose new pattern:</h2>
	        </div>
	        <div class="modal-body">
	            <form:form action="changePattern?id=${appnt.id}" method="post">
	                <div id="dates">
					    <div id="firstTimestamp">
					        <input type="time" name="treatTime[]" id="date"/>
					        <input type="button" value="Add new time" id='addTime'/>
					    </div>
					</div>
	                <input type="submit" value="Submit">
	            </form:form>
	        </div>
	    </div>
	</div>
	
	<table id="eventsTable">
		<tr>
			<th>Date</th>
			<th>Status</th>
		</tr>
		<c:forEach items="${events }" var="event">
			<tr>
				<td>${event.date }</td>
				<td>${event.status }</td>
				<!-- 
				<td>
					<form:form action="deleteEvent?id=${event.id}" method="POST">
						<button type="submit" class="deleteEvent"><i class="fa fa-trash"></i></button>
					</form:form>
				</td>
				 -->
			</tr>
		</c:forEach>
	</table>
</div>
<div class="footer">
	<a>Some info here</a><br>
</div>
<script src="<c:url value="/resources/static/js/showAppntScript.js"/>"></script>
</body>
<script>
var appnt = {
		id:"${appnt.id}",
		treatment:
			{
			id: "${appnt.treatment.id}",
			type: "${appnt.treatment.type}",
			name: "${appnt.treatment.name}"
			}
}
</script>
</html>