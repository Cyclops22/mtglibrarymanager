<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sets management</title>
</head>
<body>

	<a href="updateSetsFromTCGSite.html">Fetch sets from TCGPlayer site</a>

	<form:form commandName="form" action="submitMTGSets.html">
		<input type="submit" name="Retrieve" value="Retrieve" />
		<input type="submit" name="Save" value="Save" />
		
		<table>
			<thead>
				<tr>
					<th>Set</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>Url</th>
					<th>Number of cards</th>
					<th>Aliases</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currSet" items="${form.sets}" varStatus="status">
					<tr>
						<td>
							<form:hidden path="sets[${status.index}].id"/>
							<form:hidden path="sets[${status.index}].language"/>
							<a href='<c:out value="${currSet.name}"/>/displaySet.html'><c:out value="${currSet.name}"/></a>
							<form:hidden path="sets[${status.index}].name"/>
						</td>
						<td>${currSet.abbreviation}<form:hidden path="sets[${status.index}].abbreviation"/></td>
						<td><img src="${currSet.imageUrl}"><form:hidden path="sets[${status.index}].imageUrl"/></td>
						<td>${currSet.url}<form:hidden path="sets[${status.index}].url"/></td>
						<td>${fn:length(currSet.cards)}</td>
						<td><form:input path="sets[${status.index}].aliases"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</body>
</html>
