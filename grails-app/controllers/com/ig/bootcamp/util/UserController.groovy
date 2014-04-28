package com.ig.bootcamp.util

import com.ig.bootcamp.ApplicationTagLib
import com.ig.bootcamp.Subscriptions
import com.ig.bootcamp.User
import com.ig.bootcamp.UserResourceMapping
import groovy.sql.GroovyRowResult
import groovy.sql.Sql

class UserController {

	def beforeInterceptor = [action: this.&auth, controller: UserController]

	private auth() {
		if (!session.user) {
			redirect(controller: 'login', action: 'index')
			return false
		} else if (session.user.resetPassword && !actionName.equalsIgnoreCase("forceChange")) {
			redirect(controller: "user", action: "forceChange")
			return false
		}
	}

	def index() {}

	def dashboard() {
		User user = session.user as User
		List<Subscriptions> subscriptionsList
		List<Subscriptions> ownedSubscriptionsList
		List<UserResourceMapping> unreadUserResourceMappingList
		List<UserResourceMapping> readUserResourceMappingList

		subscriptionsList = Subscriptions.findAllBySubscriber(user)
		ownedSubscriptionsList =
				subscriptionsList.findAll {
					Subscriptions subscription ->
						if (subscription.isOwner) {
							subscription
						}
				}
		unreadUserResourceMappingList =
				UserResourceMapping.findAllByIsReadAndSubscription(false, subscriptionsList)
		readUserResourceMappingList =
				UserResourceMapping.findAllByIsReadAndSubscription(true, subscriptionsList)

		println subscriptionsList
		render(controller: 'user', view: 'home',
				model: [ownedSubscriptionsList       : ownedSubscriptionsList,
				        subscriptionsList            : subscriptionsList,
				        readUserResourceMappingList  : readUserResourceMappingList,
				        unreadUserResourceMappingList: unreadUserResourceMappingList])
	}

	def forceChange() {
		if (request.method == 'POST') {
			User user = session.user as User

			String message = "Internal Server Error- Unable to change password!"
			String hash = user.password
			String current = params.currentPassword
			String password = params.newPassword
			String confirm = params.confirmPassword

			if (hash.equals(current.encodeAsSHA1())) {
				if (current.equals(password)) {
					message = "New password should not be same to current password!"
				} else if (user.userId.equals(password)) {
					message = "New password should not be same to your User Id!"
				} else if (!confirm.equals(password)) {
					message = "Password doesn't match!"
				} else {
					User.executeUpdate("update User set password = ?, lastUpdated = ? where id = ? ",
							[current.encodeAsSHA1(), new Date(), user.id])
					message = "Password changed successfully!"
				}

			} else {
				message = "Current Password is wrong!"
			}
			flash.message = message
		}
		render(controller: 'user', view: 'forceChange')
	}

	def changeItemState(UserResourceMapping userResourceMapping) {
		println params
		if (userResourceMapping && userResourceMapping.subscription) {
			def subscriberId = userResourceMapping.subscription.subscriber.id
			def userId = session.user.id
			if (userId == subscriberId) {
				userResourceMapping.isRead = userResourceMapping.isRead ? false : true
				userResourceMapping.save(flush: true)
				render new ApplicationTagLib().unreadItems(['count': params.count])
			} else {
				render errorRender(403, request.forwardURI, 'Access Denied!')
			}
		} else {
			render errorRender(404, request.forwardURI, 'Item not found!')
		}
	}

	def dataSource
	def searchTopic = {
		String searchString = params.searchString
		String scope = params.scope ? (params.scope.equalsIgnoreCase('Private') ? 'Private' : 'Public') : 'Public'
		List<GroovyRowResult> topicList = []
		if (searchString) {
			searchString = searchString.split().join("%")
			String query = "select \n" +
					"t.id\n" +
					",t.owner_id\n" +
					",t.topic_name\n" +
					",t.description\n" +
					",u.user_name \n" +
					",case when s.id is null then '0' else '1' end as \"is_subscribed\" \n" +
					"from topic t \n" +
					"left outer join \n" +
					"subscriptions s \n" +
					"on t.id = s.topic_id\n" +
					"and s.subscriber_id =${session.user.id} \n" +
					"inner join \n" +
					"user u \n" +
					"on u.id = t.owner_id\n " +
					"where t.topic_name like '%${searchString}%' \n" +
					"order by topic_name \n" +
					"limit 50"

			println query

			Sql sql = new Sql(dataSource)
			topicList = sql.rows(query)
		}

		if (topicList) {
			render template: 'TopicDisplay', model: [id: 0, title: 'Owned Topic List', dataList: topicList]
		} else {
			render '<div style="margin: 0 auto;width: 800px;">\n' +
					'\t<table style="width:100%; margin: 0 auto;border: 2px solid darkkhaki; background-color: lightcyan;">\n' +
					'\t\t<tr>\n' +
					'\t\t\t<th style="border: 1px solid darkkhaki; text-align: center;\n' +
					'\t\t\t\tbackground-image:-moz-linear-gradient(center top , #FFFFFF, #00AEAA)">\n' +
					'\t\t\t\tNo Topic found for given Search Criteria!\n' +
					'\t\t\t</th>\n' +
					'\t\t</tr>\n' +
					'\t</table>\n' +
					'</div>'
		}
	}

	def updateRatings = {
		String response = "Invalid Subscription Id!"
		def subscription_id = params.id
		if (subscription_id){
			response = "Subscription not found!"
			int rating = params.rating as Integer
			Subscriptions subscriptions = Subscriptions.findById(subscription_id)
			if (subscriptions && subscriptions.subscriber){
				subscriptions.rating = rating
				subscriptions.save flush: true
				response = "Rating updated!"
			}
		}
		render response
	}
	private errorRender = {
		code, message, description ->
			return "<html><head><title>Server - Error report</title><style><!--H1 {font-family:Tahoma," +
					"Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial," +
					"sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial," +
					"sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial," +
					"sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial," +
					"sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial," +
					"sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR " +
					"{color : #525D76;}--></style> </head><body><h1>HTTP Status $code - ${message}</h1><HR " +
					"size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> " +
					"<u>${message}</u></p><p><b>description</b> <u>${description}" +
					".</u></p><HR size=\"1\" noshade=\"noshade\"><h3>InMemory Application Server</h3></body></html>"
	}
}
