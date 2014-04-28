package com.ig.bootcamp

class ApplicationTagLib {
	static namespace = "ls"

	def dateFormatter = {
		attrs, body ->
			Date date = attrs.date as Date
			String format = attrs.format
			out << date.format(format)
			out << body()
	}

	def ownedTopics = {
		User user = session.user

		List<Subscriptions> ownedSubscriptionsList = Subscriptions.findAllBySubscriberAndIsOwner(user, true)
		Map modelMap = [id: 0, Title: 'Owned Topic List- Using TagLib', ownedSubscriptionsList: ownedSubscriptionsList]

		out << render(template: "/user/UserHomeTemplate", model: modelMap)
	}

	def subscribedTopics = {
		User user = session.user

		List<Subscriptions> subscriptionsList = Subscriptions.findAllBySubscriber(user)

		def content = """
			<div style="margin: 0 auto;width: 800px;">
				<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
					<tr>
						<th colspan="7" style="border: 1px solid darkkhaki; text-align: center;background-image:
						-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">Subscribed Topic List- Using TagLib</th>
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
			"""
		for (subscription in subscriptionsList) {
			content += """
					<tr>
						<td nowrap style="border: 1px solid darkkhaki;">${subscription.subscriber.userId}</td>
						<td nowrap style="border: 1px solid darkkhaki;">${subscription.subscriber.userName}</td>
						<td nowrap style="border: 1px solid darkkhaki;">${subscription.topic.topicName}</td>
						<td nowrap style="border: 1px solid darkkhaki;">${subscription.topic.scope}</td>
						<td nowrap style="border: 1px solid darkkhaki;">${subscription.topic.dateCreated}</td>
						<td nowrap style="border: 1px solid darkkhaki;">${subscription.rating}</td>
						<td nowrap style="border: 1px solid darkkhaki;">${subscription.isRead}</td>
					</tr>
			"""
		}
		content += """
					</table>
				</div>"""
		out << content
	}

	def mostSubscribedTopics = {
		User user = session.user

		List list = Subscriptions.createCriteria().list {
			projections {
				'topic' {
					scope: 'Public'
				}
				groupProperty('topic')
				count('id', 'ct')
			}

			order 'ct', 'desc'
		}

		println '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
		println list
		println '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'

		def content = """
			<div style="margin: 0 auto;width: 800px;">
				<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
					<tr>
						<th colspan="2" style="border: 1px solid darkkhaki; text-align: center;background-image:
						-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">Popular Topics</th>
					</tr>
					<tr>
						<th nowrap style="border: 1px solid darkkhaki;text-align: center;">Topic Name</th>
						<th nowrap style="border: 1px solid darkkhaki;text-align: center;">Subscriptions</th>
					</tr>
			"""
		if (list) {
			for (List dataList in list) {
				content += """
					<tr>
						<td nowrap style="border: 1px solid darkkhaki;text-align: center;">${dataList.get(0)?.topicName}</td>
						<td nowrap style="border: 1px solid darkkhaki;text-align: center;">${dataList.get(1)}</td>
					</tr>
			"""
			}
		} else {
			content += """
					<tr>
						<td nowrap colspan = "2" style="border: 1px solid darkkhaki;text-align: center;">
							<strong>No Data Found!</strong>
						</td>
					</tr>
			"""
		}

		content += """
					</table>
				</div>"""
		out << content
	}

	def mostReadItems = {
		User user = session.user

		println '==============================================================================='
		List list = []
		List userSubscribedTopicList = Subscriptions.createCriteria().list {
			projections {
				property 'topic'
				eq('subscriber', user)
				gt('rating', 5)
			}
		}
		if (userSubscribedTopicList) {
			list = UserResourceMapping.createCriteria().list {
				projections {
					groupProperty('resource')
					count('id', 'ct')
				}
				'resource' {
					inList('topic', userSubscribedTopicList)
				}
				eq('isRead', true)

				order 'ct', 'desc'
				order 'resource', 'asc'
			}
		}

		println list
		println '==============================================================================='

		def content = """
			<div style="margin: 0 auto;width: 800px;">
				<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
					<tr>
						<th colspan="3" style="border: 1px solid darkkhaki; text-align: center;background-image:
						-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">Popular Item Resources</th>
					</tr>
					<tr>
						<th nowrap style="border: 1px solid darkkhaki;text-align: center;">Topic Name</th>
						<th nowrap style="border: 1px solid darkkhaki;text-align: center;">Resource Title</th>
						<th nowrap style="border: 1px solid darkkhaki;text-align: center;">#ofTimesAccessed</th>
					</tr>
			"""
		if (list) {
			for (List dataList in list) {
				content += """
					<tr>
						<td nowrap style="border: 1px solid darkkhaki;text-align: center;">
							${dataList.get(0)?.topic?.topicName}
						</td>
						<td nowrap style="border: 1px solid darkkhaki;text-align: center;">
							${dataList.get(0)?.title}
						</td>
						<td nowrap style="border: 1px solid darkkhaki;text-align: center;">
							${dataList.get(1)}
						</td>
					</tr>
			"""
			}
		} else {
			content += """
					<tr>
						<td nowrap colspan = "3" style="border: 1px solid darkkhaki;text-align: center;">
							<strong>No Data Found!</strong>
						</td>
					</tr>
			"""
		}

		content += """
					</table>
				</div>"""
		out << content
	}

	def unreadItems = {
		attrs ->
			def count = attrs.count
			User user = session.user

			List<Subscriptions> subscriptionsList = Subscriptions.findAllBySubscriber(user)
			List<UserResourceMapping> unreadUserResourceMappingList =
					UserResourceMapping.findAllByIsReadAndSubscription(false, subscriptionsList, [max: count])

			def content = """
				<div style="margin: 0 auto;width: 800px;">
					<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">
						<tr>
							<th colspan="4" style="border: 1px solid darkkhaki; text-align: center;background-image:
							-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">Unread Item List- Using TagLib</th>
						</tr>
						<tr>
							<th nowrap style="border: 1px solid darkkhaki;">Topic Name</th>
							<th nowrap style="border: 1px solid darkkhaki;">Resource Title</th>
							<th nowrap style="border: 1px solid darkkhaki;">Resource Type</th>
							<th nowrap style="border: 1px solid darkkhaki;text-align: center">
								<g:img dir="images" file="mark-read.gif">Mark As Read</g:img>
							</th>
						</tr>
			"""

			for (unreadItemList in unreadUserResourceMappingList) {
				content += """
						<tr>
							<td nowrap style="border: 1px solid darkkhaki;">${unreadItemList.resource.topic.topicName}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${unreadItemList.resource.title}</td>
							<td nowrap style="border: 1px solid darkkhaki;">${unreadItemList.resource.type}</td>
							<td nowrap style="border: 1px solid darkkhaki;text-align: center">
								<a href="javascript:void(0) id='href_${unreadItemList.id}'"
									onclick="return markItemAsRead('${unreadItemList.id}')"
									<img src="images/mark-read.gif" alt="Mark as Read" height="16" width="16">
								</a>
							</td>
						</tr>
				"""
			}
			content += """
					</table>
				</div>"""
			out << content
	}
}