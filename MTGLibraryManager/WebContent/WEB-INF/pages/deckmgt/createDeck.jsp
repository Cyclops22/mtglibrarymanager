<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
	
	<title>Create a new deck</title>
</head>
<body>
	<form:form commandName="form" action="createDeck.html">
		<table>
			<tbody>
				<tr>
					<td><form:label path="name">Name:</form:label></td>
					<td><form:input path="name" /></td>
				</tr>
			</tbody>
		</table>
		
		<input type="submit" value="Save" />
	</form:form>
</body>
</html>