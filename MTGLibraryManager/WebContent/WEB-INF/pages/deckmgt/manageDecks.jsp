<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />" />
	<title>Deck Management</title>
</head>
<body>
	
	<form:form commandName="form" action="removeDeck.html">
		<input type="button" value="Create" onclick="location.href='createDeck.html'"/>
		<input type="submit" value="Delete"/>
		
		<table class="listing">
			<thead>
				<tr>
					<th>Id</th>
					<th>Name</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currDeck" items="${form.decks}" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
						<td>
							<form:checkbox path="decks[${status.index}].selected"/>
							<form:hidden path="decks[${status.index}].id"/>
						</td>
						<td><a href="${currDeck.id}/editDeck.html"><c:out value="${currDeck.name}"/></a></td>
						<td><a href="${currDeck.id}/exportdeck.html">Export</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form:form>
</body>
</html>