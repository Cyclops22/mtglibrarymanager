<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Managing ${form.name}</title>
</head>
<body>

	<form:form commandName="form" action="submitEditLibrary.html">
		<input type="submit" name="AddSets" value="Add sets"/>
		<input type="submit" name="RemoveSelected" value="Remove selected"/>

		<table>
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>Set</th>
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
							<a href="${currSet.id}/editSetLibrary.html"><c:out value="${currSet.referencedSet.name}"/></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<form:hidden path="id" />
		<form:hidden path="name" />
	</form:form>
</body>
</html>