<g:applyLayout name="home">
	<!DOCTYPE html>
	<html>
	<head>
		<g:set var="entityName" value="${message(code: 'documentResource.label', default: 'DocumentResource')}"/>
		<title>Create New Document Resource</title>
	</head>

	<body>
	<content tag="content">
		<a href="#create-documentResource" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
		                                                                         default="Skip to content&hellip;"/></a>

		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="list" action="index"><g:message code="default.list.label"
				                                                   args="[entityName]"/></g:link></li>
			</ul>
		</div>

		<div id="create-documentResource" class="content scaffold-create" role="main" class="span7"
		     style="margin: 0 auto;">
			<h1><g:message code="default.create.label" args="[entityName]"/></h1>
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
			<g:uploadForm url="[resource: documentResourceInstance, action: 'upload']">
				<fieldset class="form">
					<g:hiddenField name="type" value="Document" valueMessagePrefix="documentResource.type"/>

					<div class="fieldcontain ${hasErrors(bean: documentResourceInstance, field: 'topic', 'error')} required">
						<g:select style="height: 30px;" id="topic" name="topic.id"
						          from="${com.ig.bootcamp.Topic.list()}"
						          optionKey="id"
						          optionValue="topicName"
						          required="required"
						          value="${documentResourceInstance?.topic?.id}" class="many-to-one"
						          noSelection="['': '---------Select Topic---------']"/>
					</div>

					<div class="fieldcontain ${hasErrors(bean: documentResourceInstance, field: 'title', 'error')} required">
						<g:textField name="title" required="required" value="${documentResourceInstance?.title}"
						             placeholder="Resource Title"/>
					</div>

					<div class="fieldcontain ${hasErrors(bean: documentResourceInstance, field: 'summary', 'error')} required">
						<g:textArea style="min-height:200px;max-height:200px;min-width:200px;max-width:200px;"
						            rows="5" cols="20" name="summary" maxlength="1024" required="required"
						            placeholder="Summary for resource..." value="${documentResourceInstance?.summary}"/>
					</div>

					<input type="file" value="" placeholder="Click to browse file!" name="file" required="true"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save"
					                value="${message(code: 'default.button.create.label', default: 'Create')}"/>
				</fieldset>
			</g:uploadForm>
		</div>
	</content>
	</body>
	</html>
</g:applyLayout>