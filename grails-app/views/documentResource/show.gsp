<g:applyLayout name="home">
	<%@ page import="com.ig.bootcamp.DocumentResource" %>
	<!DOCTYPE html>
	<html>
	<head>
		<g:set var="entityName" value="${message(code: 'documentResource.label', default: 'DocumentResource')}"/>
		<title><g:message code="default.show.label" args="[entityName]"/></title>
	</head>

	<body>
	<content tag="content">
		<a href="#show-documentResource" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
		                                                                       default="Skip to content&hellip;"/></a>

		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="list" action="index"><g:message code="default.list.label"
				                                                   args="[entityName]"/></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.create.label"
				                                                      args="[entityName]"/></g:link></li>
			</ul>
		</div>

		<div id="show-documentResource" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]"/></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list documentResource">

				<g:if test="${documentResourceInstance?.type}">
					<li class="fieldcontain">
						<span id="type-label" class="property-label"><g:message code="documentResource.type.label"
						                                                        default="Type"/></span>

						<span class="property-value" aria-labelledby="type-label"><g:fieldValue
								bean="${documentResourceInstance}" field="type"/></span>

					</li>
				</g:if>

				<g:if test="${documentResourceInstance?.summary}">
					<li class="fieldcontain">
						<span id="summary-label" class="property-label"><g:message code="documentResource.summary.label"
						                                                           default="Summary"/></span>

						<span class="property-value" aria-labelledby="summary-label"><g:fieldValue
								bean="${documentResourceInstance}" field="summary"/></span>

					</li>
				</g:if>

				<g:if test="${documentResourceInstance?.title}">
					<li class="fieldcontain">
						<span id="title-label" class="property-label"><g:message code="documentResource.title.label"
						                                                         default="Title"/></span>

						<span class="property-value" aria-labelledby="title-label"><g:fieldValue
								bean="${documentResourceInstance}" field="title"/></span>

					</li>
				</g:if>

				<g:if test="${documentResourceInstance?.docName}">
					<li class="fieldcontain">
						<span id="docName-label" class="property-label"><g:message code="documentResource.docName.label"
						                                                           default="Doc Name"/></span>

						<span class="property-value" aria-labelledby="docName-label"><g:fieldValue
								bean="${documentResourceInstance}" field="docName"/></span>

					</li>
				</g:if>

				<g:if test="${documentResourceInstance?.contentType}">
					<li class="fieldcontain">
						<span id="contentType-label" class="property-label"><g:message
								code="documentResource.contentType.label" default="Content Type"/></span>

						<span class="property-value" aria-labelledby="contentType-label"><g:fieldValue
								bean="${documentResourceInstance}" field="contentType"/></span>

					</li>
				</g:if>

				<g:if test="${documentResourceInstance?.size}">
					<li class="fieldcontain">
						<span id="size-label" class="property-label"><g:message code="documentResource.size.label"
						                                                        default="Size"/></span>

						<span class="property-value" aria-labelledby="size-label"><g:fieldValue
								bean="${documentResourceInstance}" field="size"/></span>

					</li>
				</g:if>

				<g:if test="${documentResourceInstance?.topic}">
					<li class="fieldcontain">
						<span id="topic-label" class="property-label"><g:message code="documentResource.topic.label"
						                                                         default="Topic"/></span>

						<span class="property-value" aria-labelledby="topic-label"><g:link controller="topic"
						                                                                   action="show"
						                                                                   id="${documentResourceInstance?.topic?.id}">${documentResourceInstance?.topic?.encodeAsHTML()}</g:link></span>

					</li>
				</g:if>

			</ol>
			<g:form url="[resource: documentResourceInstance, action: 'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${documentResourceInstance}"><g:message
							code="default.button.edit.label" default="Edit"/></g:link>
					<g:actionSubmit class="delete" action="delete"
					                value="${message(code: 'default.button.delete.label', default: 'Delete')}"
					                onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
				</fieldset>
			</g:form>
		</div>
	</content>
	</body>
	</html>
</g:applyLayout>