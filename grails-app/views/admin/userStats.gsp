<%@ page import="org.springframework.validation.FieldError" %>
<%--
  Created by IntelliJ IDEA.
  User: sandeep
  Date: 16/4/14
  Time: 11:17 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<g:applyLayout name="home">
	<html>
	<head>
		<title>User Statistics</title>
	</head>

	<body>
	<content tag="content">
		<div style="height: 400px; width: 100%">
			<div style="height: 40px; width: 100%">&nbsp;</div>
			<table style="width:400px; margin: 0 auto; border: 1px solid darkkhaki;
			background-color: lightcyan;">
				<tr>
					<th style="border: 1px solid darkkhaki;">User Id</th>
					<th style="border: 1px solid darkkhaki;">User Name</th>
					<th style="border: 1px solid darkkhaki;"># of Subscriptions</th>
				</tr>
				<g:each in="${data}" var="row">
					<tr>
						<g:each in="${row}" var="col">
							<g:if test="${col in com.ig.bootcamp.User}">
								<td style="border: 1px solid darkkhaki;">${col.userId}</td>
								<td style="border: 1px solid darkkhaki;">${col.userName}</td>
							</g:if>
							<g:else>
								<td style="border: 1px solid darkkhaki;text-align: right">${col}</td>
							</g:else>
						</g:each>
					</tr>
				</g:each>
			</table>
		</div>

	</content>
	</body>
	</html>
</g:applyLayout>