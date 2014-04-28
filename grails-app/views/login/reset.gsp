<%--
  Created by IntelliJ IDEA.
  User: sandeep
  Date: 14/4/14
  Time: 10:36 PM
--%>

<%@ page import="org.springframework.validation.FieldError" contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>Reset Password</title>
	<meta name="layout" content="main"/>
</head>

<body>

<!-- Reset password -->
<div class="span7">
	<g:form class="resetForm" url="[controller: 'login', action: 'reset']">
		<fieldset>
			<legend>Reset forgotten password</legend>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<div class="control-group">
				<label for="email">Please provide your email used to register at LinkShare and we will send
				you a new password.</label>

				<div class="input-prepend">
					<span class="add-on">@</span>
					<g:textField name="email" placeholder="email@domain.com"
					             value="${params?.email}" required="true"/>
				</div>
			</div>

			<div class="control-group form-submit-group">
				<g:submitButton class="btn pull-right" name="submitButton" value="Reset my password"/>
				<span class="form-submit-link">
					<a style="color: #E6195E"
					   href="${createLink([controller: 'login', action: 'index'])}">Log in</a>
				</span>
			</div>
		</fieldset>
	</g:form>
</div>
<!-- Reset password -->
</body>
</html>