<g:applyLayout name="home">
	<!DOCTYPE html>
	<html>
	<head>
		<g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}"/>
		<title>Create New Topic</title>
	</head>

	<body>
	<content tag="content">
		<jqvalui:renderValidationScript for="com.ig.bootcamp.Topic" qtip="true" form="topicForm"/>

		<a href="#create-topic" class="skip" tabindex="-1">
			<g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
		</a>

		<div class="nav" role="navigation">
			<ul>
				<li>
					<g:link class="list" action="index">
						<g:message code="default.list.label" args="[entityName]"/></g:link>

				</li>
			</ul>
		</div>

		<div id="create-topic" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]"/></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>

			<g:hasErrors bean="${topicInstance}">
				<jqvalui:renderErrors style="margin-bottom:10px">
					<g:renderErrors bean="${topicInstance}" as="list" />
				</jqvalui:renderErrors>
			</g:hasErrors>
			<jqvalui:renderErrors style="margin-bottom:10px"/>

			%{--<g:hasErrors bean="${topicInstance}">--}%
				%{--<ul class="errors" role="alert">--}%
					%{--<g:eachError bean="${topicInstance}" var="error">--}%
						%{--<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message--}%
								%{--error="${error}"/></li>--}%
					%{--</g:eachError>--}%
				%{--</ul>--}%
			%{--</g:hasErrors>--}%
			<g:form name="topicForm" url="[resource: topicInstance, action: 'save']">
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save"
					                value="${message(code: 'default.button.create.label', default: 'Create')}"/>
				</fieldset>
			</g:form>
		</div>
	</content>
	</body>
	</html>
</g:applyLayout>