<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Create a new library</title>
	
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
</head>
<body>
	<img id="save" src="" alt="Save"/>
	<img id="cancel" src="" alt="Cancel"/>
		
	<form:form commandName="form" action="createLibrary.html">
		<table>
			<tbody>
				<tr>
					<td><form:label path="name">Name:</form:label></td>
					<td><form:input path="name" /></td>
				</tr>
			</tbody>
		</table>
	</form:form>
</body>

<script type="text/javascript">

$(document).ready(function() {
	$("img#save").click(function() {
		$("form#form").submit();
	});

	$("img#cancel").click(function() {
		window.location.href = "<c:url value='/librarymgt/manageLibraries.html' />";
	});
});

</script>
</html>