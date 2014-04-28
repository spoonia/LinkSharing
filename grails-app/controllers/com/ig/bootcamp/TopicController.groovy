package com.ig.bootcamp

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class TopicController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond Topic.findAllByOwner(session?.user, [max: params.max]), model: [topicInstanceCount: Topic.count()]
	}

	def show(Topic topicInstance) {
		if (Subscriptions.countBySubscriberAndTopic(session?.user, topicInstance)) {
			respond topicInstance
		} else {
			render errorRender(403, request.forwardURI, 'Access Denied!')
		}
	}

	def create() {
		respond new Topic(params)
	}

	@Transactional
	def save(Topic topicInstance) {
		if (topicInstance == null) {
			notFound()
			return
		}

		if (topicInstance.hasErrors()) {
			respond topicInstance.errors, view: 'create'
			return
		}

		topicInstance.save flush: true

		Subscriptions subscriptions = new Subscriptions(subscriber: session?.user,
				topic: topicInstance, isOwner: true, isRead: false, rating: 0)
		subscriptions.save flush: true

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'topicInstance.label', default: 'Topic'), topicInstance.id])
				redirect topicInstance
			}
			'*' { respond topicInstance, [status: CREATED] }
		}
	}

	def edit(Topic topicInstance) {
		if (Subscriptions.countBySubscriberAndTopic(session?.user, topicInstance)) {
			respond topicInstance
		} else {
			render errorRender(403, request.forwardURI, 'Access Denied!')
		}
	}

	@Transactional
	def update(Topic topicInstance) {
		if (Subscriptions.countBySubscriberAndTopic(session?.user, topicInstance)) {
		} else {
			render errorRender(403, request.forwardURI, 'Access Denied!')
			return
		}
		if (topicInstance == null) {
			notFound()
			return
		}

		if (topicInstance.hasErrors()) {
			respond topicInstance.errors, view: 'edit'
			return
		}

		topicInstance.save flush: true

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'Topic.label', default: 'Topic'), topicInstance.id])
				redirect topicInstance
			}
			'*' { respond topicInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(Topic topicInstance) {
		if (Subscriptions.countBySubscriberAndTopic(session?.user, topicInstance)) {
		} else {
			render errorRender(403, request.forwardURI, 'Access Denied!')
			return
		}
		if (topicInstance == null) {
			notFound()
			return
		}

		Subscriptions subscriptions = Subscriptions.findBySubscriberAndTopic(session?.user, topicInstance)
		subscriptions.delete flush: true

		topicInstance.delete flush: true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'Topic.label', default: 'Topic'), topicInstance.id])
				redirect action: "index", method: "GET"
			}
			'*' { render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'topicInstance.label', default: 'Topic'), params.id])
				redirect action: "index", method: "GET"
			}
			'*' { render status: NOT_FOUND }
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
