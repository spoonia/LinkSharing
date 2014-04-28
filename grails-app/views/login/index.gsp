<%--
  Created by IntelliJ IDEA.
  User: sandeep
  Date: 14/4/14
  Time: 10:36 PM
--%>

<%@ page import="org.springframework.validation.FieldError" contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>Login to LinkShare</title>
	<meta name="layout" content="main"/>
</head>

<body>
<!-- Log in -->
<div class="span7">
	<g:form class="authForm" url="[controller: 'login', action: 'login']">
		<fieldset>
			<legend>Login</legend>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>
			<div class="control-group">
				<div class="input-prepend ">
					<span class="add-on field-user"></span>
					<g:textField name="username" placeholder="Username or email address"
					             value="${params?.username}" required="true"/>
				</div>
			</div>

			<div class="control-group">
				<div class="input-prepend ">
					<span class="add-on field-password"></span>
					<g:passwordField name="password" placeholder="Password" required="true"/>
				</div>
			</div>

			<div class="control-group form-submit-group">
				<g:submitButton class="btn pull-right" name="submitButton" value="Log in"/>
				<span class="form-submit-link">
					<a style="color: #E6195E"
					   href="${createLink([controller: 'login', action: 'reset'])}">forgot your password?
					</a>
				</span>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span class="form-submit-link">
					<a style="color: #E6195E"
					   href="${createLink([controller: 'login', action: 'register'])}">Create an account
					</a>
				</span>
			</div>
		</fieldset>
	</g:form>
</div>
<!-- Log in -->
</body>
</html>