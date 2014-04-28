<g:applyLayout name="home">
	<%@ page import="com.ig.bootcamp.Topic" %>
	<!DOCTYPE html>
	<html>
	<head>
		<g:set var="entityName" value="${message(code: 'topic.label', default: 'Topic')}"/>
		<title>Show Topic-${topicInstance?.topicName}</title>
		<script type="text/javascript">
			function validateInvites() {
				alert('h..');
				var form = document.getElementById('invitationform')
				var mailFields = form.getElementsByTagName('input')
				var allNull = true

				for (var i = 0; i < mailFields.length; i++) {
					alert(mailFields[i].name);
					if(mailFields[i] && mailFields[i].value){
						var value = mailFields[i].value
						value = trim(value)
						alert(value);
					}
				}
				return false;
			}
		</script>
	</head>

	<body>
	<content tag="content">
		<a href="#show-topic" class="skip" tabindex="-1">
			<g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
		</a>

		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="list" action="index"><g:message code="default.list.label"
				                                                   args="[entityName]"/></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.create.label"
				                                                      args="[entityName]"/></g:link></li>
			</ul>
		</div>

		<div id="show-topic" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]"/></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list topic">

				<g:if test="${topicInstance?.topicName}">
					<li class="fieldcontain">
						<span id="topicName-label" class="property-label"><g:message code="topic.topicName.label"
						                                                             default="Topic Name"/></span>

						<span class="property-value" aria-labelledby="topicName-label"><g:fieldValue
								bean="${topicInstance}" field="topicName"/></span>

					</li>
				</g:if>

				<g:if test="${topicInstance?.scope}">
					<li class="fieldcontain">
						<span id="scope-label" class="property-label"><g:message code="topic.scope.label"
						                                                         default="Scope"/></span>

						<span class="property-value" aria-labelledby="scope-label"><g:fieldValue bean="${topicInstance}"
						                                                                         field="scope"/></span>

					</li>
				</g:if>

				<g:if test="${topicInstance?.dateCreated}">
					<li class="fieldcontain">
						<span id="dateCreated-label" class="property-label"><g:message code="topic.dateCreated.label"
						                                                               default="Date Created"/></span>

						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate
								date="${topicInstance?.dateCreated}"/></span>

					</li>
				</g:if>

				<g:if test="${topicInstance?.description}">
					<li class="fieldcontain">
						<span id="description-label" class="property-label"><g:message code="topic.description.label"
						                                                               default="Description"/></span>

						<span class="property-value" aria-labelledby="description-label"><g:fieldValue
								bean="${topicInstance}" field="description"/></span>

					</li>
				</g:if>

				<g:if test="${topicInstance?.lastUpdated}">
					<li class="fieldcontain">
						<span id="lastUpdated-label" class="property-label"><g:message code="topic.lastUpdated.label"
						                                                               default="Last Updated"/></span>

						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate
								date="${topicInstance?.lastUpdated}"/></span>

					</li>
				</g:if>

				<g:if test="${topicInstance?.owner}">
					<li class="fieldcontain">
						<span id="owner-label" class="property-label"><g:message code="topic.owner.label"
						                                                         default="Owner"/></span>

						<span class="property-value" aria-labelledby="owner-label"><g:link controller="user"
						                                                                   action="show"
						                                                                   id="${topicInstance?.owner?.id}">${topicInstance?.owner?.userName}</g:link></span>

					</li>
				</g:if>

			</ol>
			<g:form name="invitationform" controller="invitation" action="invite" onsubmit="return validateInvites()">
				<fieldset style="width: 50%;margin: 0px auto;">
					<legend>Send Invitations</legend>
					<g:hiddenField name="topic" value="${topicInstance.id}"/>
					<div class="control-group">
						<div class="input-prepend ">
							<span class="add-on">@</span>
							<g:textField name="email_1" placeholder="email@domain.com" value="${params?.email_1}"/>
						</div>
					</div>

					<div class="control-group">
						<div class="input-prepend ">
							<span class="add-on">@</span>
							<g:textField name="email_2" placeholder="email@domain.com" value="${params?.email_2}"/>
						</div>
					</div>

					<div class="control-group">
						<div class="input-prepend ">
							<span class="add-on">@</span>
							<g:textField name="email_3" placeholder="email@domain.com" value="${params?.email_3}"/>
						</div>
					</div>

					<div class="control-group">
						<div class="input-prepend ">
							<span class="add-on">@</span>
							<g:textField name="email_4" placeholder="email@domain.com" value="${params?.email_4}"/>
						</div>
					</div>

					<div class="control-group">
						<div class="input-prepend ">
							<span class="add-on">@</span>
							<g:textField name="email_5" placeholder="email@domain.com" value="${params?.email_5}"/>
						</div>
					</div>

					<div class="control-group form-submit-group">
						<g:submitButton class="btn pull-right" name="submitButton" value="Send Invitation!"
						                onsubmit="return validateInvites()"/>
					</div>
				</fieldset>
			</g:form>



			<g:form url="[resource: topicInstance, action: 'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${topicInstance}"><g:message
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
</g:applyLayout>>