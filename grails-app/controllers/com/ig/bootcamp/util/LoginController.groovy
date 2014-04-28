package com.ig.bootcamp.util

import com.ig.bootcamp.Roles
import com.ig.bootcamp.User

import javax.servlet.http.HttpServletRequest

class LoginController {

	def mailService

	def beforeInterceptor = [action: this.&auth, controller: LoginController]

	private auth() {
		flash.message = ""
		if (session.user && !actionName.equalsIgnoreCase("logout") && !actionName.equalsIgnoreCase("wow")) {
			redirect(controller: 'user', action: 'dashboard')
			return false
		}
	}

	def index() {
	}

	def register(UserCO userCO) {
		// new user posts his registration details
		if (request.method == 'POST') {
			if (User.countByUserId(userCO.userId)) {
				flash.message = "UserId unavailable!"
			} else if (User.countByEmail(userCO.email)) {
				flash.message = "Email Id already registered!"
			} else {
				println('*********************************************************')
				if (!userCO.validate()) {
					// validation failed, render registration page again
					if (userCO.hasErrors()) {
						userCO.errors.each {
							println it.toString()
						}
						respond(userCO.errors, view: 'register')
						return [user: userCO]
					}
				} else {
					User user = new User(params)
					user.password = userCO.plainPassword.encodeAsSHA1()
					user.role = Roles.findByRole('User')
					user.enabled = false;

					user.save flush: true

					def hex = user.userId + '$' + user.lastUpdated
					hex = hex.encodeAsBase64()
					hex = "${webRequest.baseUrl}/login/activate?id=${hex}"
					println hex

					mailService.sendMail {
						async true
						to user.email
						from "LinkShare<sandeep.poonia@intelligrape.com>"
						subject "Welcome to LinkShare - User account activation"
						html """
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
												Hi ${user.userName},<br><br>
												Your new user account (${user.userId}) with the email address <b>
												<a href="mailto:${user.email}" target="_blank">${user.email}</a>
												</b> is now set up.<br><br>
												Please use the link below to activate your account.<br>
												<a href="${hex}"
												target="_blank">Click here</a><br><br>
												You will be able to change your parameters (password, ...) once your account is activated.<br><br><br><br>
												If you have not requested the creation of a LinkShare account,
												or if you think this is an unauthorized use of your email address,
												please forward this email to <b>
												<a href="mailto:sandeep.poonia@intelligrape.com" target="_blank">accounts@linkshare.com</a></b>.
												<br><br><br><br><i>This email has been generated automatically. Please, do not reply.</i>
											</div>
										</td>
									</tr>
								</table>
							</div>
						"""
					}
					flash.message = "Registration successful. Follow the steps mailed to your registered mail account" +
							" to activate your account!"
					flash.put('id', user.id)
					flash.put('success', true)
					flash.put('mailSentCount', 1)
				}
			}
		}
		render(controller: "login", view: "register")
	}

	def login = {
		if (request.method == 'POST') {
			def passwordHashed = params.password.encodeAsSHA1()
			User user = User.findByUserIdOrEmail(params.username, params.username)
			String message
			if (user) {
				boolean enabled = user.enabled
				boolean locked = user.locked
				boolean resetPassword = user.resetPassword
				String password = user.@password
				int failedAttempts = user.failedLoginAttempts

				if (locked) {
					message = "Account locked. Reset your password!"
				} else if (!enabled) {
					if (user.dateCreated.equals(user.lastUpdated)) {
						message = "Please activate the account before login! Follow the steps mailed to your " +
								"registered email account!"
					} else {
						message = "Account has been deactivated! Contact support to activate your account!"
					}
				} else if (password.equals(passwordHashed)) {
					user.lastLoginTime = new Date()
					User.executeUpdate("update User set lastLoginTime = ? where id = ? ", [new Date(), user.id])

					session.user = user

					if (resetPassword) {
						redirect(controller: "user", action: "forceChange")
					} else {
						redirect(controller: "user", action: "dashboard")
					}
				} else {
					message = "Invalid Password!"
					failedAttempts = failedAttempts + 1

					if (failedAttempts >= 5) {
						User.executeUpdate("update User set failedLoginAttempts = ?, locked = ?, lastUpdated = ? where id = ? ",
								[failedAttempts, true, new Date(), user.id])
					} else {
						User.executeUpdate("update User set failedLoginAttempts = ?, lastUpdated = ? where id = ? ", [failedAttempts, new Date(), user.id])
					}
				}
			} else {
				message = "User not registered!"
			}
			flash.message = message
		}
		render(controller: "login", view: "index")
	}

	def reset = {
		if (request.method == 'POST') {
			def message = "EmailId not registered!"

			def email = params.email
			if (User.countByEmailAndEnabled(email, true)) {
				User user = User.findByEmail(email)
				user.password = passwordGenerator((('A'..'Z') + ('0'..'9') + ('a'..'z')).join(), 8).encodeAsSHA1()
				user.resetPassword = true
				if (user.validate()) {
					user.save()
					message = "An Email has been sent to the given address to reset the password!"
					mailService.sendMail {
						async true
						to "${email}"
						from "LinkShare<sandeep.poonia@intelligrape.com>"
						subject "Welcome to LinkShare - Password Reset"
						html """
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
												Hi ${user.userName},<br><br>
												You have requested to have your password reset for your account 
												from [${getClientIpAddress(request)}]<br><br>
												Please use below details to login to your account.<br>
												<strong>UserId</strong>-${user.userId}<br>
												<strong>Email-Id</strong>-${user.email}<br>
												<strong>Password</strong>-${user.plainPassword}<br>
												<br><br>
												Change your password once you login.<br><br><br><br>
												If you have not made this request,
												or if you think this is an unauthorized use of your email address,
												please forward this email to <b>
												<a href="mailto:sandeep.poonia@intelligrape.com" target="_blank">accounts@linkshare.com</a></b>.
												<br><br><br><br><i>This email has been generated automatically. Please, do not reply.</i>
											</div>
										</td>
									</tr>
								</table>
							</div>
						"""
					}
				} else if (user.hasErrors()) {
					user.errors.each {
						errors ->
							println errors.toString()
					}
					message = "An error has occurred while processing the request!"
				}
			}
			flash.message = message
		}
		render(controller: "login", view: "reset")
	}

	def resendEmail = {
		User user = User.findById(flash?.id)
		if (user) {
			flash.put('mailSentCount', flash.mailSentCount)
			def hex = user.userId + '$' + (new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
					.parse(user.lastUpdated.toString()))
			hex = hex.encodeAsBase64()
			hex = "${webRequest.baseUrl}/login/activate?id=${hex}"

			mailService.sendMail {
				async true
				to user.email
				from "LinkShare<sandeep.poonia@intelligrape.com>"
				subject "Welcome to LinkShare - User account activation"
				html """
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
										Hi ${user.userName},<br><br>
										Your new user account (${user.userId}) with the email address <b>
										<a href="mailto:${user.email}" target="_blank">${user.email}</a>
										</b> is now set up.<br><br>
										Please use the link below to activate your account.<br>
										<a href="${hex}"
										target="_blank">Click here</a><br><br>
										You will be able to change your parameters (password, ...) once your account is activated.<br><br><br><br>
										If you have not requested the creation of a LinkShare account,
										or if you think this is an unauthorized use of your email address,
										please forward this email to <b>
										<a href="mailto:sandeep.poonia@intelligrape.com" target="_blank">accounts@linkshare.com</a></b>.
										<br><br><br><br><i>This email has been generated automatically. Please, do not reply.</i>
									</div>
								</td>
							</tr>
						</table>
					</div>
				"""
			}
			flash.message = "Email Sent. Follow the steps mailed to your registered mail account" +
					" to activate your account!"
			flash.put('success', true)
			flash.put('id', flash.id)
		} else {
			flash.message = "Internal Server Error- Unable to send mail!"
			flash.put('success', false)
			flash.put('id', flash.id)
		}
		render(controller: "login", view: "register")
	}

	def activate = {
		String message = "x.0.1-Internal Server Error- Unable to activate account!"
		if (params?.id) {
			try {
				println new String(params.id.decodeBase64())
				String[] id = new String(params.id.decodeBase64()).split("[\$]")
				if (id.length == 2) {
					String userId = id[0]
					Date date = new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(id[1])
					println userId
					println date
					User user = User.findByUserId(userId)
					if (user) {
						Date lastUpdateDate = user.lastUpdated
						if (lastUpdateDate.compareTo(date) != 0) {
							message = "x.0.2-Link expired!"
						} else {
							user.lastLoginTime = new Date()
							User.executeUpdate("update User set enabled=?, lastLoginTime=?, lastUpdated=? where id = ? ",
									[true, new Date(), new Date(), user.id])
							session.user = user
							redirect(controller: "user", action: "dashboard")
							return
						}
					} else {
						message = "x.0.3-Invalid request!"
					}
				} else {
					println id.length
					message = "x.0.4-Invalid request!"
				}
			} catch (Exception ex) {
				println ex
				message = "x.0.5-Invalid request!"
			}
		}
		render errorRender(500, message)
	}

	def logout = {
		session.invalidate()
		redirect(controller: 'login')
	}

	private passwordGenerator = { String alphabet, int n ->
		new Random().with {
			(1..n).collect { alphabet[nextInt(alphabet.length())] }.join()
		}
	}

	private errorRender = {
		code, message ->
			return "<html><head><title>Server - Error report</title><style><!--H1 {font-family:Tahoma," +
					"Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial," +
					"sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial," +
					"sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial," +
					"sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial," +
					"sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial," +
					"sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR " +
					"{color : #525D76;}--></style> </head><body><h1>HTTP Status $code - ${webRequest.contextPath}</h1><HR " +
					"size=\"1\" noshade=\"noshade\"><p><b>type</b> Status report</p><p><b>message</b> " +
					"<u>${webRequest.contextPath}</u></p><p><b>description</b> <u>$message" +
					".</u></p><HR size=\"1\" noshade=\"noshade\"><h3>InMemory Application Server</h3></body></html>"
	}

	private static final String[] HEADERS_TO_TRY = ("X-Forwarded-For,Proxy-Client-IP,WL-Proxy-Client-IP," +
			"HTTP_X_FORWARDED_FOR,HTTP_X_FORWARDED,HTTP_X_CLUSTER_CLIENT_IP,HTTP_CLIENT_IP,HTTP_FORWARDED_FOR," +
			"HTTP_FORWARDED,HTTP_VIA,REMOTE_ADDR").split(",")

	private getClientIpAddress = {
		HttpServletRequest request ->
			for (String header : HEADERS_TO_TRY) {
				String ip = request.getHeader(header);
				if (ip && !"unknown".equalsIgnoreCase(ip)) {
					return ip;
				}
			}
			return request.getRemoteAddr();
	}
}