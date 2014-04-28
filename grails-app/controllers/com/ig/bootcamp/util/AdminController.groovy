package com.ig.bootcamp.util
import com.ig.bootcamp.Subscriptions
import com.ig.bootcamp.User

class AdminController {

	static defaultAction = "stats"

	def beforeInterceptor = [action: this.&auth, only: 'stats']

	private auth() {
		if (!session.user) {
			redirect(controller: 'login', action: 'index')
			return false
		} else {
			User user = session.user as User
			if (!user.email.equalsIgnoreCase('admin@intelligrape.com')){
				redirect(action: accessDenied)
				return false
			}
		}
	}

	def accessDenied = {
		render "<html><head><title>Server - Error report</title><style><!--H1 {font-family:Tahoma," +
				"Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial," +
				"sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial," +
				"sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial," +
				"sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial," +
				"sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial," +
				"sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR " +
				"{color : #525D76;}--></style> </head><body><h1>HTTP Status 403 - /Linksharing/admin/stats</h1><HR " +
				"size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> " +
				"<u>/Linksharing/admin/stats</u></p><p><b>description</b> <u>Access Denied!" +
				".</u></p><HR size=\"1\" noshade=\"noshade\"><h3>InMemory Application Server</h3></body></html>"
	}

	def stats = {
		def criteria = Subscriptions.createCriteria()
		def results = criteria {
			projections {
				groupProperty('subscriber')
				count('topic')
			}
			order 'topic'
		}
		render view: 'userStats', model: [data: results]
	}
}
