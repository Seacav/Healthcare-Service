<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <title>Document</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="<c:url value="/resources/static/js/pagination.js"/>"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>  
    <link rel="stylesheet" href="<c:url value="/resources/static/css/pagingStyle.css" />">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value="/resources/static/css/tiles/navbarStyle.css" />">
</head>
<body>
	<div class="navbar">
			<a href="${pageContext.request.contextPath}/nurse/"><i class="fas fa-home"></i></a>
			<form:form action="${pageContext.request.contextPath}/logout" method="POST">
				<button type="submit" class="logout"><i class="fas fa-door-open"></i></button>
			</form:form>
	</div>
    <div id="wrapper">
        <section>
        	<div id="filters">
        		Show
				<select id='filter-by'>
					<option value='today'>for today</option>
					<option value='hour'>for this hour</option>
					<option value='all'>all</option>
				</select>
				<input type="text" id="patientFilter" placeholder="Enter name..">
        	</div>
        	<div id="pagination-bar"></div>
            <div id="pagination-data-container"></div>
        </section>
    </div>
    <script src="<c:url value="/resources/static/js/pagingScript.js"/>"></script>
</body>
</html>