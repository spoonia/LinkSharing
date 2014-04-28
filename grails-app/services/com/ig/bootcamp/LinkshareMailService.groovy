package com.ig.bootcamp

import org.springframework.transaction.annotation.Transactional

@Transactional
class LinkshareMailService {

	def mailService

	static transactional = false

	@Transactional(noRollbackFor = Exception.class)
	def sendInvite(long userId
	               , boolean asyncStatus
	               , String mailTo
	               , String mailFrom
	               , String mailCC
	               , String mailBCC
	               , String mailSubject
	               , String contentType
	               , String message) {

		MailLogger mailLogger = new MailLogger(userId: userId, mailTo: mailTo, mailFrom: mailFrom, mailCc: mailCC, mailBcc: mailBCC,
				mailSubject: mailSubject, contentType: contentType, mailMessage: message, mailCreationTime: new Date())
		mailLogger.save flush: true

		println MailLogger.list().each {
			println it.toString()
		}

		println("**************************************************************************")
		/*
		Transaction Check of Service Class
		 */
//		throw new RuntimeException('force throw to check Transactional Service....')

		mailService.sendMail {
			async asyncStatus
			to mailTo
			from mailFrom
			subject mailSubject
			html message
		}
	}
}
