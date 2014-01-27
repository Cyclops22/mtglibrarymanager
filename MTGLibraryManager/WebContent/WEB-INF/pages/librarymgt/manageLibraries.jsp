<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Libraries Management</title>
</head>
<body>
	
	<form:form commandName="form" action="removeLibrary.html">
		<input type="button" value="Create" onclick="location.href='createLibrary.html'"/>
		<input type="submit" value="Delete"/>
		
		<table>
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currLibrary" items="${form.libraries}" varStatus="status">
					<tr>
						<td>
							<form:checkbox path="libraries[${status.index}].selected"/>
							<form:hidden path="libraries[${status.index}].id"/>
						</td>
						<td><a href="${currLibrary.id}/editLibrary.html"><c:out value="${currLibrary.name}"/></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</body>
</html>