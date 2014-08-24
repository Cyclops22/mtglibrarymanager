<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Sets management</title>
	
	<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
	
	<script src="http://code.jquery.com/jquery-1.11.0.js"></script>
	<script src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
</head>
<body>
	<fmt:setBundle basename="com.cyclops.library.mtg.resources.resources" var="bundle"/>
	
	<img id="import" src="" alt="Import sets"/>
	<img id="back" src="" alt="Back"/>
	
	<h1>Imported sets</h1>
	<table>
		<thead>
			<tr>
				<th>Name</th>
				<th>&nbsp;</th>
				<th>Release date</th>
				<th>Number of cards</th>
				<th>Block</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="currSet" items="${setsForm.sets}" varStatus="status">
				<tr>
					<td><a href="${currSet.code}/displaySet.html"><c:out value="${currSet.name}"/></a></td>
					<td><img src="http://mtgimage.com/symbol/set/${currSet.code}/c/16.png"/></td>
					<td><c:out value="${currSet.releaseDate}"/></td>
					<td>${fn:length(currSet.cards)}</td>
					<td><c:out value="${currSet.block}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<div id="importSetsDlg" title="Import sets" style="display: none;">
		<form:form commandName="importSetsForm" action="importSets.html">
			<div>Select sets to import</div>
		  	
		  	<table>
				<thead>
					<tr>
						<th>&nbsp;</th>
						<th>Name</th>
						<th>Release date</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="currImportSetFormBean" items="${importSetsForm.importSetFormBeans}" varStatus="status">
						<tr>
							<td>
								<form:hidden path="importSetFormBeans[${status.index}].code"/>
								<form:hidden path="importSetFormBeans[${status.index}].name"/>
								<form:hidden path="importSetFormBeans[${status.index}].releaseDate"/>
							
								<form:checkbox path="importSetFormBeans[${status.index}].selected"/>
							</td>
							<td><c:out value="${currImportSetFormBean.name}"/></td>
							<td><c:out value="${currImportSetFormBean.releaseDate}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form:form>
	</div>
</body>

<script type="text/javascript">

$(document).ready(function() {
	$("#importSetsDlg").dialog({
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
				text: "Import",
				width: 95,
				click: function() {
					$('form#importSetsForm').submit();
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

	$("img#import").click(function() {
		$("#importSetsDlg").dialog("open");
	});

	$("img#back").click(function() {
		window.location.href = "<c:url value='/' />";
	});
});

</script>
</html>
