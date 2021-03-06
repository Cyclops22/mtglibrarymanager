<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
	<title>Add sets</title>
</head>
<body>
	<fmt:setBundle basename="com.cyclops.library.mtg.resources.resources" var="bundle"/>
	
	<form:form commandName="form" action="addSets.html">
		<input type="submit" value="Save" />
		<input type="button" value="Cancel" onclick="location.href='editLibrary.html'"/>
		
		<table class="listing">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:set var="lastGroup" value="" />
				
				<c:forEach var="currSet" items="${form.sets}" varStatus="status">
					<c:if test="${currSet.referencedSet.category != lastGroup}">
						<tr>
							<td colspan="3">
								<h2><fmt:message key="expansion.detail.description.${currSet.referencedSet.category}" bundle="${bundle}"/></h2>
							</td>
						</tr>
					</c:if>
					
					<c:set var="lastGroup" value="${currSet.referencedSet.category}" />
					
					<tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
						<td>
							<form:checkbox path="sets[${status.index}].selected"/>
							<form:hidden path="sets[${status.index}].id"/>
							<form:hidden path="sets[${status.index}].referencedSet.id"/>
						</td>
						<td>
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