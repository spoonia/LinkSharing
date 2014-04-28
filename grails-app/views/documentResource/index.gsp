<g:applyLayout name="home">
	<%@ page import="com.ig.bootcamp.DocumentResource" %>
	<!DOCTYPE html>
	<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'documentResource.label', default: 'DocumentResource')}"/>
		<title><g:message code="default.list.label" args="[entityName]"/></title>
	</head>

	<body>
	<content tag="content">
		<a href="#list-documentResource" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
		                                                                       default="Skip to content&hellip;"/></a>

		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="create" action="create"><g:message code="default.create.label"
				                                                      args="[entityName]"/></g:link></li>
			</ul>
		</div>

		<div id="list-documentResource" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]"/></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="span7" style="margin: 0 auto;border: 1px solid #000000">
				<thead>
				<tr>

					<g:sortableColumn property="title"
					                  title="${message(code: 'documentResource.title.label', default: 'Title')}"/>

					<g:sortableColumn property="summary"
					                  title="${message(code: 'documentResource.summary.label', default: 'Summary')}"/>

					<th>Download Link</th>


				</tr>
				</thead>
				<tbody>
				<g:each in="${documentResourceInstanceList}" status="i" var="documentResourceInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td style="width: 33%"><g:link action="show"
						            id="${documentResourceInstance.id}">
							${fieldValue(bean: documentResourceInstance, field: "title")}</g:link>
						</td>

						<td style="width: 33%">${fieldValue(bean: documentResourceInstance, field: "summary")}</td>

						<td style="width: 33%">
							<g:link controller="documentResource" action="download"
							        id="${documentResourceInstance.docName}">Download</g:link>
						</td>

					</tr>
				</g:each>
				</tbody>
			</table>

			<div class="pagination">
				<g:paginate total="${documentResourceInstanceCount ?: 0}"/>
			</div>
		</div>
	</content>
	</body>
	</html>
</g:applyLayout>