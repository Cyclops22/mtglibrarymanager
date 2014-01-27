<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add sets</title>
</head>
<body>
	
	<form:form commandName="form" action="addSets.html">
		<input type="submit" value="Save" />
		
		<table>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currSet" items="${form.sets}" varStatus="status">
					<tr>
						<td>
							<form:checkbox path="sets[${status.index}].selected"/>
							<form:hidden path="sets[${status.index}].id"/>
						</td>
						<td>
							<form:hidden path="sets[${status.index}].referencedSet.id"/>
							<c:out value="${currSet.referencedSet.name}"/>
						</td>
						<td><img src='<c:out value="${currSet.referencedSet.imageUrl}"/>'/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</form:form>
</body>
</html>