<g:applyLayout name="home">
	<%@ page import="com.ig.bootcamp.DocumentResource" %>
	<!DOCTYPE html>
	<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentResource.label', default: 'DocumentResource')}"/>
		<title><g:message code="default.edit.label" args="[entityName]"/></title>
	</head>

	<body>
	<content tag="content">
		<a href="#edit-documentResource" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
		                                                                       default="Skip to content&hellip;"/></a>

		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="list" action="index"><g:message code="default.list.label"
				                                                   args="[entityName]"/></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.create.label"
				                                                      args="[entityName]"/></g:link></li>
			</ul>
		</div>

		<div id="edit-documentResource" class="content scaffold-edit" role="main">
			<h1><g:message code="default.edit.label" args="[entityName]"/></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${documentResourceInstance}">
				<ul class="errors" role="alert">
					<g:eachError bean="${documentResourceInstance}" var="error">
						<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
								error="${error}"/></li>
					</g:eachError>
				</ul>
			</g:hasErrors>
			<g:form url="[resource: documentResourceInstance, action: 'update']" method="PUT">
				<g:hiddenField name="version" value="${documentResourceInstance?.version}"/>
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update"
					                value="${message(code: 'default.button.update.label', default: 'Update')}"/>
				</fieldset>
			</g:form>
		</div>
	</content>
	</body>
	</html>
</g:applyLayout>