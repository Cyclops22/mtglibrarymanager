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

	<a href="retrieveSets.html">Retrieve sets</a>

	<form:form commandName="newSetsForm" action="submitMTGSets.html">
		<input type="submit" name="Retrieve" value="Retrieve sets details" />
		<input type="submit" name="Save" value="Save sets" />
		
		<h1>New sets</h1>
		<table>
			<thead>
				<tr>
					<th>Set</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>Url</th>
					<th>Number of cards</th>
					<th>Release date</th>
					<th>Aliases</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currNewSet" items="${newSetsForm.sets}" varStatus="status">
					<tr>
						<td>
							<form:hidden path="sets[${status.index}].id"/>
							<form:hidden path="sets[${status.index}].name"/>
							<form:hidden path="sets[${status.index}].abbreviation"/>
							<form:hidden path="sets[${status.index}].category"/>
							<form:hidden path="sets[${status.index}].language"/>
							<form:hidden path="sets[${status.index}].releaseDate"/>
							<form:hidden path="sets[${status.index}].url"/>
							<form:hidden path="sets[${status.index}].imageUrl"/>
							
							<c:out value="${currNewSet.name}"/>
						</td>
						<td>${currNewSet.abbreviation}</td>
						<td><img src="${currNewSet.imageUrl}"></td>
						<td>${currNewSet.url}</td>
						<td>${fn:length(currNewSet.cards)}</td>
						<td>${currNewSet.releaseDate}</td>
						<td><form:input path="sets[${status.index}].aliases"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
	
	<h1>Sets</h1>
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
						<a href='<c:out value="${currSet.name}"/>/displaySet.html'><c:out value="${currSet.name}"/></a>
					</td>
					<td>${currSet.abbreviation}</td>
					<td><img src="${currSet.imageUrl}"></td>
					<td>${currSet.url}</td>
					<td>${fn:length(currSet.cards)}</td>
					<td>${currSet.aliases}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
