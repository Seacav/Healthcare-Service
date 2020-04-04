<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Show appointment</title>
<link rel="stylesheet" href="<c:url value="/resources/static/css/showAppntStyle.css" />" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<h2>Appointment details</h2>
<form:form method="POST" action="cancel?id=${appnt.id}&patientId=${appnt.patient.id }">
	<input type="submit" value="Cancel appointment">
</form:form>
<p>Type: ${appnt.treatment.type}</p>
<p>Name: ${appnt.treatment.name }</p>
<p>Dosage: ${appnt.dosage }</p>
<p>Due date: ${appnt.dueDate }</p>
<button id="dosageBtn">Change dosage</button>
<button id="patternBtn">Change pattern</button>

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
<br><br>
<table id="eventsTable" border="1">
	<tr>
		<th>Date</th>
		<th>Status</th>
		<th>Options</th>
	</tr>
	<c:forEach items="${events }" var="event">
		<tr>
			<td>${event.date }</td>
			<td>${event.status }</td>
			<td>
				<form:form action="deleteEvent?id=${event.id}" method="POST">
					<button type="submit" class="deleteEvent"><i class="fa fa-trash"></i></button>
				</form:form>
			</td>
		</tr>
	</c:forEach>
</table>
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