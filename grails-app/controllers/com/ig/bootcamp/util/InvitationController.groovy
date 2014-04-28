package com.ig.bootcamp.util

import com.ig.bootcamp.*
import grails.transaction.Transactional

class InvitationController {

	def mailService
	def linkshareMailService

	def index() {}

	def invite() {
		println controllerName
		Topic topic = Topic.get(params.topic)
		Subscriptions subscriptions = Subscriptions.findBySubscriberAndTopic(session.user, topic)
		Set userList = []
		String message = "No EmailId Provided!"
		boolean mailSent = false
		if (subscriptions) {
			params.each {
				String key, value ->
					if (key.toLowerCase().startsWith("email_") && value) {
						message = "Emails not registered on LinkShare!"
						User user = User.findByEmail(value)
						if (user) {
							message = "All Users are already subscribed to this Topic!"
							if (!(Subscriptions.countBySubscriberAndTopic(user, topic))) {
								message = "Earlier sent invitation still pending approval!"
								if (!(Invitations.countByTopicAndInitiatorAndReceiverAndInviteStatus(topic, subscriptions, user, "Pending"))) {
									userList.add(user)
								}
							}
						}
					}
			}
			userList.each {
				User user ->
					Invitations invitations = new Invitations(inviteStatus: "Pending", initiator: subscriptions,
							topic: topic, receiver: user)
					if (!invitations.validate()) {
						render errorRender(500, 'Internal Server Error!', 'An Error Encountered at Server End!')
						return
					}
					invitations.save flush: true

					def acceptLink = subscriptions.id + '$' + topic.id + '$' + user.id + '$' + invitations.id
					def rejectLink = subscriptions.id + '$' + topic.id + '$' + user.id + '$' + invitations.id

					acceptLink += '$Accepted'
					rejectLink += '$Rejected'

					acceptLink = "${webRequest.baseUrl}/invitation/subscribe?id=${acceptLink.encodeAsBase64()}"
					rejectLink = "${webRequest.baseUrl}/invitation/subscribe?id=${rejectLink.encodeAsBase64()}"

					println acceptLink
					println rejectLink

					String htmlMessage = """
						<style type="text/css">
							body, td{font-family:arial,sans-serif;font-size:13px}
							a:link, a:active {color:#1155CC; text-decoration:none}
							a:hover {text-decoration:underline; cursor: pointer}
							a:visited{color:##6611CC}
							img{border:0px}
							pre {
								white-space: pre; white-space: -moz-pre-wrap; white-space: -o-pre-wrap; white-space: pre-wrap;
								word-wrap: break-word; max-width: 800px; overflow: auto;
							}
						</style>
						<div class="maincontent">
							<table width=100% cellpadding=12 cellspacing=0 border=0>
								<tr>
									<td>
										<div style="overflow: hidden;">
											Dear ${user.userName},<br><br>
											Your friend, ${session.user.userName} with email address <b>
											<a href="mailto:${session.user.email}"
												target="_blank">${session.user.email}</a>
											</b> has asked you to subscribe to the below topic.<br>
											<strong>Topic</strong>:${topic.topicName}<br><br>
											<a href="${acceptLink}" target="_blank">
												Click here
											</a> to subscribe.<br><br>
											<a href="${rejectLink}" target="_blank">
												Click here
											</a> to reject the invitation.
											<br><br><br><br><i>This email has been generated automatically. Please, do not reply.</i>
										</div>
									</td>
								</tr>
							</table>
						</div>
					"""

					linkshareMailService.sendInvite((session.user as User).id, true, user.email,
							"LinkShare<sandeep.poonia@intelligrape.com>", null, null,
							"Invitation to subscribe to topic", "html", htmlMessage)

					mailSent = true
			}
			if (!mailSent && message) {
				render message
				return
			} else {
				redirect uri: "/topic/show/${topic.id}"
				return
			}
		} else {
			render errorRender(403, request.forwardURI, 'Invalid Request!')
			return
		}
	}

	@Transactional
	def subscribe() {
		String message = "x.0.1-Missing Request Body!"
		User user = session?.user as User

		if (params?.id) {
			try {
				println new String(params.id.decodeBase64())

				String[] inviteId = new String(params.id.decodeBase64()).split("[\$]")
				if (inviteId.length == 5) {
					Subscriptions subscriptions = Subscriptions.get(inviteId[0])
					Topic topic = Topic.get(inviteId[1].toLong())
					//User invitee = User.get(inviteId[2].toLong())
					Invitations invitations = Invitations.get(inviteId[3].toLong())
					String inviteStatus = inviteId[4]

					if (!user.isAttached()) {
						println("Why not attached.................")
						user.attach()
					}

					if (invitations
							&& subscriptions
							&& topic
							//&& invitee
							&& inviteStatus
							&& invitations.initiator.equals(subscriptions)
							&& invitations.receiver.equals(user)
							&& invitations.topic.equals(topic)
							&& (inviteStatus.equals("Accepted") || inviteStatus.equals("Rejected"))) {
						if (invitations.inviteStatus.equalsIgnoreCase("Pending")) {
							println ';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;'
							invitations.inviteStatus = inviteStatus
							if (invitations.validate()) {
								updateSubscriptionAndMapping(user, topic, subscriptions)
								invitations.save failOnError: true, flush: true

								redirect uri: "/topic/show/${topic.id}"
								return
							} else {
								message = "x.0.2-Invalid request data!"
							}
						} else {
							println ';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;'
							redirect uri: "/topic/show/${topic.id}"
							return
						}
					} else {
						message = "x.0.3-Invalid request data!"
					}
				} else {
					println inviteId.length
					message = "x.0.4-Invalid request data!"
				}
			} catch (Exception ex) {
				println ex
				message = "x.0.5-Invalid request data!"
			}
		}
		render errorRender(500, request.forwardURI, message)
	}

	def updateSubscription = {
		int response = 0
		def topicId = params.id
		Topic topic = Topic.findById(topicId)
		if (topic && topic.scope){
			boolean isSubscribed = params.subscribed.equals("1")
			Subscriptions subscriptions
			if (isSubscribed){
				subscriptions = Subscriptions.findByTopicAndSubscriber(topic, session.user)
				subscriptions.delete flush: true
				response = 1
			}else{
				updateSubscriptionAndMapping(session.user, topic, subscriptions)
				response = 2
			}
		}
		render response
	}

	private updateSubscriptionAndMapping(User user, Topic topic, Subscriptions subscriptions) {
		subscriptions = new Subscriptions(subscriber: user, topic: topic)
		subscriptions.save failOnError: true, flush: true

		List<ResourceInfo> resourceInfoList = ResourceInfo.findAllByTopic(topic)
		if (resourceInfoList) {
			List<UserResourceMapping> userResourceMappingList = []
			resourceInfoList.each {
				ResourceInfo resourceInfo ->
					userResourceMappingList <<
							new UserResourceMapping(subscription: subscriptions, resource: resourceInfo)
			}
			userResourceMappingList*.save flush: true, failOnError: true
		}
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
