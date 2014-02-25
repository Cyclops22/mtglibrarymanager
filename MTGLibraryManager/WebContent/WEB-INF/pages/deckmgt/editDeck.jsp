<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link type="text/css" rel="stylesheet" href="<c:url value="/css/style.css" />" />
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	
	<title>Managing ${form.name}</title>
</head>
<body>
	<fmt:setBundle basename="com.cyclops.library.mtg.resources.resources" var="bundle"/>
	
	<form:form commandName="form" action="submitEditDeck.html">
		<input type="button" value="Back" onclick="location.href='../manageDecks.html'"/>

		<form:input path="id" />
		<form:input path="name" />
		
		<div style="width: 1024px; background-color: green;">
			<table>
				<tbody>
					<c:forEach var="currCard" items="${cards}" varStatus="status">
						<tr>
							<td><c:out value="${currCard.card.name}"/></td>
							<!--
							<td>
								<c:forEach var="currSet" items="${currCard.sets}" varStatus="setStatus">
									<c:out value="${currSet.name}"/><c:if test="${setStatus.index < fn:length(currCard.sets) - 1}">, </c:if>
								</c:forEach>
							</td>
							-->
							<td><c:out value="${currCard.card.mana}"/></td>
							<td><c:out value="${currCard.card.type}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		
			
		</div>
	</form:form>
</body>

<script type="text/javascript">

</script>
</html>