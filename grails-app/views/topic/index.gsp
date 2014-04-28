<g:applyLayout name="home">
	<%@ page import="com.ig.bootcamp.Topic" %>

	<!DOCTYPE html>
	<html>
	<head>
		<g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}"/>
		<title>My Topics</title>
	</head>

	<body>
	<content tag="content">
		<a href="#list-topic" class="skip" tabindex="-1">
			<g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
		</a>

		<div class="nav" role="navigation">
			<ul>
				<li>
					<g:link class="create" action="create">
					<g:message code="default.create.label" args="[entityName]"/></g:link>
				</li>
			</ul>
		</div>

		<div id="list-topic" class="content scaffold-list" role="main">
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
				<tr>

					<g:sortableColumn property="topicName"
					                  title="${message(code: 'topic.topicName.label', default: 'Topic Name')}"/>

					<g:sortableColumn property="description"
					                  title="${message(code: 'topic.description.label', default: 'Description')}"/>

					<g:sortableColumn property="scope" title="${message(code: 'topic.scope.label', default: 'Scope')}"/>

					<g:sortableColumn property="dateCreated"
					                  title="${message(code: 'topic.dateCreated.label', default: 'Date Created')}"/>

					<g:sortableColumn property="lastUpdated"
					                  title="${message(code: 'topic.lastUpdated.label', default: 'Last Updated')}"/>

				</tr>
				</thead>
				<tbody>
				<g:each in="${topicInstanceList}" status="i" var="topicInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

						<td><g:link action="show"
						            id="${topicInstance.id}">${fieldValue(bean: topicInstance, field: "topicName")}</g:link></td>

						<td>${fieldValue(bean: topicInstance, field: "description")}</td>

						<td>${fieldValue(bean: topicInstance, field: "scope")}</td>

						<td><g:formatDate date="${topicInstance.dateCreated}"/></td>

						<td><g:formatDate date="${topicInstance.lastUpdated}"/></td>

					</tr>
				</g:each>
				</tbody>
			</table>

			<div class="pagination">
				<g:paginate total="${topicInstanceCount ?: 0}"/>
			</div>
		</div>
	</content>
	</body>
	</html>
</g:applyLayout>