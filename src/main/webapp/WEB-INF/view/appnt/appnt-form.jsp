<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Appointment form</title>
<link rel="stylesheet" href="<c:url value="/resources/static/css/appntFormStyle.css" />" />
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
	<h2>Appointment form</h2>
	<form:form action="processForm" method="POST" modelAttribute="eventGenerator">
		<input type="hidden" name="patientId" value="${eventGenerator.patientId }"/>
		<input type="hidden" name="treatmentId" value="${eventGenerator.treatmentId }"/>
		<div class="optionContainer">
			<div class="weekdays">
			    <input type="checkbox" name="days" value="0" id="mon" class="day"/>
			    <label for="mon">MO</label>
			    
			    <input type="checkbox" name="days" value="1" id="tue" class="day"/>
			    <label for="tue">TU</label>
			    
			    <input type="checkbox" name="days" value="2" id="wed" class="day"/>
			    <label for="wed">WE</label>
			    
			    <input type="checkbox" name="days" value="3" id="thu" class="day"/>
			    <label for="thu">TH</label>
			    
			    <input type="checkbox" name="days" value="4" id="fri" class="day"/>
			    <label for="fri">FR</label>
			    
			    <input type="checkbox" name="days" value="5" id="sat" class="day"/>
			    <label for="sat">SA</label>
			    
			    <input type="checkbox" name="days" value="6" id="sun" class="day"/>
			    <label for="sun">SU</label>
			</div>
		</div>
		<div class="optionContainer">
	 		<a>Choose time:</a>
			<div id="dates">
			    <div id="firstTimestamp">
			        <input type="time" name="treatTime" id="date"/>
			        <input type="button" value="Add new time" id='addTime'/>
			    </div>
			</div>
		</div>
		<div class="optionContainer">
			<c:if test="${isDrug}">
				Enter dosage: <input type="text" name="dosage"/><br>
			</c:if>
		</div>
		<div class="optionContainer">
		    <a>Choose duration:</a>
		    <select name="duration" size=1>
		        <option value="1">1 week</option>
		        <option value="2">2 weeks</option>
		        <option value="3">3 weeks</option>
		        <option value="4">4 weeks</option>
		    </select>
		</div>
		<div class="optionContainer">
			<input type="checkbox" name="startNextWeek" id="startNext" />
			<label for="startNext">Start from next week?</label>
			<input type="submit" value="Submit">
		</div>
		<script src="<c:url value="/resources/static/js/appntFormScript.js"/>"></script>
	</form:form>
</div>
<div class="footer">
	<a>Some info here</a><br>
</div>
</body>
</html>