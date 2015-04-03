<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Managing ${form.name}</title>
	
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
	
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/style.css" />" />
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
</head>
<body>
	<fmt:setBundle basename="com.cyclops.library.mtg.resources.resources" var="bundle"/>
	
	<img id="add" src="" alt="Add sets"/>
	<img id="remove" src="" alt="Remove selected"/>
	<img id="back" src="" alt="Back"/>
	
	<form:form commandName="form" action="removeSets.html">
		<table class="listing">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
					<th id="total">&nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="currSet" items="${form.sets}" varStatus="status">
					<tr class="${status.index % 2 == 0 ? 'even' : 'odd'}">
						<td>
							<form:checkbox path="sets[${status.index}].selected"/>
							<form:hidden path="sets[${status.index}].id"/>
							<form:hidden path="sets[${status.index}].referencedSet.id"/>
						</td>
						<td>
							<a href="${currSet.id}/editSetLibrary.html"><c:out value="${currSet.referencedSet.name}"/></a>
						</td>
						<td>
							<c:choose>
							    <c:when test="${empty currSet.referencedSet.gathererCode}">
							    	<img src="http://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=${currSet.referencedSet.code}&size=small&rarity=C" alt="${currSet.referencedSet.name}"/>
							        
							    </c:when>
							    <c:otherwise>
							        <img src="http://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=${currSet.referencedSet.gathererCode}&size=small&rarity=C" alt="${currSet.referencedSet.name}"/>
							        
							    </c:otherwise>
							</c:choose>
							
						</td>
						<td tdtype="total">
							${currSet.numberOfCards}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<form:hidden path="id" />
		<form:hidden path="name" />
	</form:form>
	
	<div id="addSetsDlg" title="Add sets to library" style="display: none;">
		<form:form commandName="setsForm" action="addSets.html">
			<div>Select sets to add</div>
		  	
		  	<table>
				<thead>
					<tr>
						<th>&nbsp;</th>
						<th>Name</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="currSet" items="${setsForm.sets}" varStatus="status">
						<tr>
							<td>
								<form:hidden path="sets[${status.index}].id"/>
							
								<form:checkbox path="sets[${status.index}].selected"/>
							</td>
							<td><c:out value="${currSet.name}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form:form>
	</div>
</body>

<script type="text/javascript">

$( document ).ready(function() {
	$("#addSetsDlg").dialog({
	    autoOpen: false,
	    modal: true,
	    resizable: false,
		height:768,
	    width: 720,
	    close: function() {
	    	$("tbody", $(this)).empty();
		},
	    buttons: [ 
	      	{
				text: "Add",
				width: 95,
				click: function() {
					$('form#setsForm').submit();
				}
	      	},
			{
				text: "Cancel",
				width: 95,
				click: function() {
					$(this).dialog("close");
				}
			}
		]
 	});

	var bigTotal = 0;
	$("td[tdtype*='total']").each(function() {
	    var value = $(this).text();
	    
	    // add only if the value is number
	    if(!isNaN(value) && value.length != 0) {
	        bigTotal += parseInt(value);
	    }
	});
	$('th#total').text(bigTotal);

	$("img#add").click(function() {
		$("#addSetsDlg").dialog("open");
	});

	$("img#remove").click(function() {
		$("form#form").submit();
	});

	$("img#back").click(function() {
		window.location.href = "<c:url value='/librarymgt/manageLibraries.html' />";
	});
});

</script>
</html>