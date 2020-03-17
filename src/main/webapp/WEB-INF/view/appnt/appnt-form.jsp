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
</head>
<body>
<h2>Appointment form</h2>
<form:form action="processForm" method="POST" modelAttribute="eventGenerator">
	<div class="weekdays">
	    <input type="checkbox" name="days" value="Mo" id="mon" class="day"/>
	    <label for="mon">ПН</label>
	    
	    <input type="checkbox" name="days" value="Tu" id="tue" class="day"/>
	    <label for="tue">ВТ</label>
	    
	    <input type="checkbox" name="days" value="We" id="wed" class="day"/>
	    <label for="wed">СР</label>
	    
	    <input type="checkbox" name="days" value="Th" id="thu" class="day"/>
	    <label for="thu">ЧТ</label>
	    
	    <input type="checkbox" name="days" value="Fr" id="fri" class="day"/>
	    <label for="fri">ПТ</label>
	    
	    <input type="checkbox" name="days" value="Sa" id="sat" class="day"/>
	    <label for="sat">СБ</label>
	    
	    <input type="checkbox" name="days" value="Su" id="sun" class="day"/>
	    <label for="sun">ВС</label>
	</div>
 	<a>Choose time:</a>
	<div id="dates">
	    <div id="firstTimestamp">
	        <input type="time" name="treatTime[0]" id="date"/>
	        <input type="button" value="Add new time" id='addTime'/>
	    </div>
	</div>
	<div>
	    <a>Choose duration:</a>
	    <select name="duration" size=1>
	        <option value="1">1 неделя</option>
	        <option value="2">2 недели</option>
	        <option value="3">3 недели</option>
	        <option value="4">4 недели</option>
	    </select>
	</div>
	<input type="submit" value="Submit">
	<script src="<c:url value="/resources/static/js/appntFormScript.js"/>"></script>
</form:form>
</body>
</html>