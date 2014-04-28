<div style="margin: 0 auto;width: 800px;">
	<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
		<tr>
			<th colspan="4" style="border: 1px solid darkkhaki; text-align: center;background-image:
			-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">${title}</th>
		</tr>
		<tr>
			<th nowrap style="border: 1px solid darkkhaki;width: 33%">Topic Name</th>
			<th nowrap style="border: 1px solid darkkhaki;width: 33%">Owner</th>
			<th nowrap style="border: 1px solid darkkhaki;width: 33%">Description</th>
			<th nowrap style="border: 1px solid darkkhaki;width: 33%">Subscribe</th>
		</tr>
		<g:each in="${dataList}" var="topic">
			<tr>
				<td nowrap style="border: 1px solid darkkhaki;">${topic.topic_name}</td>
				<td nowrap style="border: 1px solid darkkhaki;">${topic.user_name}</td>
				<td nowrap
				    style="border: 1px solid darkkhaki;">${topic.description.length() > 40 ? topic.description.substring(0, 40) + '...' : topic.description}</td>
				<td nowrap style="border: 1px solid darkkhaki; text-align: center">
					<a href='javascript:void(0)' id='href_${topic.id}'
					   onclick="return updateSubscription(${topic.id}, ${topic.is_subscribed})">
						<img
								src=images/skin/${(topic.is_subscribed.equals('1')) ? 'unsubscribe.gif' :
										'subscribe.gif'}
								alt='Subscribe/Unsubscribe' height=16 width=16>
					</a>
				</td>
			</tr>
		</g:each>
	</table>
</div>