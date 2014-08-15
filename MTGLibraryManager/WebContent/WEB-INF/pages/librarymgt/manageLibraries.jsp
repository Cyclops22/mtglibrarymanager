<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Libraries Management</title>
	
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
</head>
<body>
	
	<form:form commandName="form" action="deleteLibrary.html">
		<img id="create" src="" alt="Create"/>
		<img id="delete" src="" alt="Delete"/>
		<img id="back" src="" alt="Back"/>
		
		<table class="listing">
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currLibrary" items="${form.libraries}" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
						<td>
							<form:checkbox path="libraries[${status.index}].selected"/>
							<form:hidden path="libraries[${status.index}].id"/>
						</td>
						<td><a href="${currLibrary.id}/editLibrary.html"><c:out value="${currLibrary.name}"/></a></td>
						<td><a href="${currLibrary.id}/exportlibrary.html">Export</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
	
	<hr/>
	<form:form commandName="importForm" action="import.html">
		<input type="text" name="libraryName"/>
		<textarea name="importText" form="importForm"></textarea>
		<input type="submit" value="Import"/>
	</form:form>
</body>

<script type="text/javascript">

$(document).ready(function() {
	$("img#create").click(function() {
		window.location.href = "<c:url value='/librarymgt/createLibrary.html' />";
	});

	$("img#delete").click(function() {
		$("form#form").submit();
	});

	$("img#back").click(function() {
		window.location.href = "<c:url value='/' />";
	});
});

</script>
</html>