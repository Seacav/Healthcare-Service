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
	<p>${appnt.drug_name} ${appnt.created_at}</p><br>
</c:forEach>
</body>
</html>