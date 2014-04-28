<%--
  Created by IntelliJ IDEA.
  User: sandeep
  Date: 18/4/14
  Time: 1:21 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<g:applyLayout name="home">
	<html>
	<head>
		<title>Change Password</title>
	</head>

	<body>
	<content tag="content">
		<div class="span7">
			<g:form class="authForm" url="[controller: 'user', action: 'forceChange']">
				<fieldset>
					<legend>Change Password</legend>
					<g:if test="${flash.message}">
						<div class="message">${flash.message}</div>
					</g:if>
					<div class="control-group">
						<div class="input-prepend ">
							<span class="add-on field-password"></span>
							<g:passwordField name="currentPassword" placeholder="Current Password"
							                 required="true"/>
						</div>
					</div>

					<div class="control-group">
						<div class="input-prepend ">
							<span class="add-on field-password"></span>
							<g:passwordField name="newPassword" placeholder="New Password" required="true"/>
						</div>
					</div>

					<div class="control-group">
						<div class="input-prepend ">
							<span class="add-on field-password"></span>
							<g:passwordField name="confirmPassword" placeholder="Confirm" required="true"/>
						</div>
					</div>

					<div class="control-group form-submit-group">
						<g:submitButton class="btn pull-right" name="submitButton" value="Change Password"/>
					</div>
				</fieldset>
			</g:form>
		</div>
	</content>
	</body>
	</html>
</g:applyLayout>