<%--
  Created by IntelliJ IDEA.
  User: sandeep
  Date: 14/4/14
  Time: 10:36 PM
--%>

<%@ page import="org.springframework.validation.FieldError" contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>Register</title>
	<meta name="layout" content="main"/>
	<jqvalui:renderValidationScript for="com.ig.bootcamp.util.UserCO" qtip="true" domain="com.ig.bootcamp.User"
	                                renderErrorsOnTop="false" form="regForm"/>
	%{--not="role,password,lastLoginTime,dateCreated,lastUpdated,failedLoginAttempts,resetPassword,enabled,locked"/>--}%
</head>

<body>
<!-- Sign up -->
<div class="span7">
	<g:form name="regForm" id="regForm" class="regForm" url="[controller: 'login', action: 'register']">
		<fieldset>
			<legend>Sign up</legend>
			<g:if test="${flash.message}">
				<div class="message">${flash.message}</div>
			</g:if>

			<g:hasErrors bean="${userCOInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${userCOInstance}" var="error">
						<li <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message
								error="${error}"/></li>
					</g:eachError>
				</ul>
			</g:hasErrors>

			<div class="control-group">
				<div class="input-prepend ">
					<span class="add-on field-user"></span>
					<g:textField name="userId" placeholder="User Id" value="${params?.userId}" required="true"/>
				</div>
			</div>

			<div class="control-group">
				<div class="input-prepend ">
					<span class="add-on field-user"></span>
					<g:textField name="userName" placeholder="User Name" value="${params?.userName}"
					             required="true"/>
				</div>
			</div>

			<div class="control-group">
				<div class="input-prepend ">
					<span class="add-on field-birthdate"></span>
					<g:textField name="dateOfBirth" placeholder="Date of birth"
					             value="${params?.dateOfBirth}"
					             required="true"/>
				</div>
			</div>

			%{--<div class="control-group">--}%
			%{--<div class="input-prepend ">--}%
			%{--<span class="add-on field-birthdate"></span>--}%
			%{--<input type="date" id="dateOfBirth" name="dateOfBirth" value="${params.dateOfBirth}" required="true"--}%
			%{--placeholder="Date of birth"/>--}%
			%{--</div>--}%
			%{--</div>--}%

			<div class="control-group">
				<div class="input-prepend ">
					<span class="add-on">@</span>
					<g:textField name="email" placeholder="email@domain.com" value="${params?.email}"
					             required="true"/>
				</div>
			</div>

			<div class="control-group">
				<div class="input-prepend ">
					<span class="add-on field-password"></span>
					<g:passwordField name="plainPassword" placeholder="Password" required="true"/>
				</div>
			</div>

			%{--<div class="control-group">--}%
			%{--<div class="input-prepend ">--}%
			%{--<div id="progressbar"></div>--}%
			%{--</div>--}%
			%{--</div>--}%

			<div class="control-group">
				<div class="input-prepend ">
					<span class="add-on field-password"></span>
					<g:passwordField name="confirmPassword"
					                 placeholder="Password Confirmation" required="true"/>
				</div>
			</div>

			<div class="control-group form-submit-group">
				<g:submitButton class="btn pull-right" name="submitButton" value="Sign up"/>
				<span class="form-submit-link">
					<a style="color: #E6195E"
					   href="${createLink([controller: 'login', action: 'index'])}">Already have an account
					</a>
				</span>
				<g:if test="${flash.message && flash.success && (flash?.mailSentCount < 5)}">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					<span class="form-submit-link">
						<a style="color: #E6195E"
						   href="${createLink([controller: 'login', action: 'resendEmail'])}">Resend Email
						</a>
					</span>
					<%
						flash.put('id', flash.id)
						flash.put('mailSentCount', flash.mailSentCount + 1)
					%>
				</g:if>
			</div>
		</fieldset>
	</g:form>
</div>
<script>
	jQuery(function () {
		$("#dateOfBirth").datepicker();
	});
	//	$(function () {
	//		$("#plainPassword").complexify({}, function (valid, complexity) {
	//			$("#progressbar").progressbar({
	//				value: complexity
	//			});
	//		});
	//	});
</script>

<!-- Sign up -->
</body>
</html>