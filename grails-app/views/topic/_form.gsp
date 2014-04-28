<%@ page import="com.ig.bootcamp.Topic" %>


<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'topicName', 'error')} required">
	<label for="topicName">
		<g:message code="topic.topicName.label" default="Topic Name"/>
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="topicName" required="required" value="${topicInstance?.topicName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'scope', 'error')} ">
	<label for="scope">
		<g:message code="topic.scope.label" default="Scope"/>

	</label>
	<g:select style="height:25px;" name="scope" from="${topicInstance.constraints.scope.inList}"
	          value="${topicInstance?.scope}" required="required"
	          valueMessagePrefix="topic.scope" noSelection="['': '----------Select----------']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: topicInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="topic.description.label" default="Description"/>

	</label>
	<g:textArea style="min-height:200px;max-height:200px;min-width:200px;max-width:200px;" rows="5" cols="20" placeholder="Provide Topic Description here..." name="description"
	            value="${topicInstance?.description}"/>
</div>

<g:hiddenField id="owner" name="owner.id" value="${session?.user?.id}" class="many-to-one"/>

