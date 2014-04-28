<g:if test="${id == 0}">
	<div style="margin: 0 auto;width: 800px;">
		<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
			<tr>
				<th colspan="3" style="border: 1px solid darkkhaki; text-align: center;background-image:
				-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">${Title}</th>
			</tr>
			<tr>
				<th nowrap style="border: 1px solid darkkhaki;width: 33%">Topic Name</th>
				<th nowrap style="border: 1px solid darkkhaki;width: 33%">Scope</th>
				<th nowrap style="border: 1px solid darkkhaki;width: 33%">Date Created</th>
			</tr>
			<g:each in="${ownedSubscriptionsList}" var="row">
				<tr>
					<g:if test="${row in com.ig.bootcamp.Subscriptions}">
						<g:each in="${row.topic}" var="col">
							<td nowrap style="border: 1px solid darkkhaki;">${col.topicName}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.scope}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.dateCreated}</td>
						</g:each>
					</g:if>
				</tr>
			</g:each>
		</table>
	</div>
</g:if>
<g:elseif test="${id == 1}">
	<div style="margin: 0 auto;width: 800px;">
		<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
			<tr>
				<th colspan="7" style="border: 1px solid darkkhaki; text-align: center;background-image:
				-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">${Title}</th>
			</tr>
			<tr>
				<th nowrap style="border: 1px solid darkkhaki;">Owner Id</th>
				<th nowrap style="border: 1px solid darkkhaki;">Owner Name</th>
				<th nowrap style="border: 1px solid darkkhaki;">Topic Name</th>
				<th nowrap style="border: 1px solid darkkhaki;">Scope</th>
				<th nowrap style="border: 1px solid darkkhaki;">Date Created</th>
				<th nowrap style="border: 1px solid darkkhaki;">Rating</th>
				<th nowrap style="border: 1px solid darkkhaki;">Read Flag</th>
			</tr>
			<g:each in="${subscriptionsList}" var="row">
				<tr>
					<g:if test="${row in com.ig.bootcamp.Subscriptions}">
						<g:each in="${row.subscriber}" var="col">
							<td nowrap style="border: 1px solid darkkhaki;">${col.userId}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.userName}</td>
						</g:each>
						<g:each in="${row.topic}" var="col">
							<td nowrap style="border: 1px solid darkkhaki;">${col.topicName}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.scope}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.dateCreated}</td>
						</g:each>
						<td nowrap style="border: 1px solid darkkhaki;">
							<div class="subscriptionRating" id="${row.id}">${row.rating}</div>
						</td>
						<td nowrap style="border: 1px solid darkkhaki;">${row.isRead}</td>
					</g:if>
				</tr>
			</g:each>
		</table>
	</div>
</g:elseif>
<g:elseif test="${id == 2}">
	<div style="margin: 0 auto;width: 800px;">
		<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
			<tr>
				<th colspan="3" style="border: 1px solid darkkhaki; text-align: center;background-image:
				-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">${Title}</th>
			</tr>
			<tr>
				<th nowrap style="border: 1px solid darkkhaki;">Topic Name</th>
				<th nowrap style="border: 1px solid darkkhaki;">Resource Title</th>
				<th nowrap style="border: 1px solid darkkhaki;">Resource Type</th>
			</tr>
			<g:each in="${readUserResourceMappingList}" var="row">
				<tr>
					<g:if test="${row in com.ig.bootcamp.UserResourceMapping}">
						<g:each in="${row.resource}" var="col">
							<td nowrap style="border: 1px solid darkkhaki;">${col.topic.topicName}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.title}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.type}</td>
						</g:each>
					</g:if>
				</tr>
			</g:each>
		</table>
	</div>
</g:elseif>
<g:elseif test="${id == 3}">
	<div style="margin: 0 auto;width: 800px;">
		<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
			<tr>
				<th colspan="3" style="border: 1px solid darkkhaki; text-align: center;background-image:
				-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">${Title}</th>
			</tr>
			<tr>
				<th nowrap style="border: 1px solid darkkhaki;">Topic Name</th>
				<th nowrap style="border: 1px solid darkkhaki;">Resource Title</th>
				<th nowrap style="border: 1px solid darkkhaki;">Resource Type</th>
			</tr>
			<g:each in="${unreadUserResourceMappingList}" var="row">
				<tr>
					<g:if test="${row in com.ig.bootcamp.UserResourceMapping}">
						<g:each in="${row.resource}" var="col">
							<td nowrap style="border: 1px solid darkkhaki;">${col.topic.topicName}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.title}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${col.type}</td>
						</g:each>
					</g:if>
				</tr>
			</g:each>
		</table>
	</div>
</g:elseif>