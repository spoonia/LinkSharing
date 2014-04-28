package com.ig.bootcamp

import com.ig.bootcamp.util.EmailCO
import org.springframework.transaction.annotation.Transactional

@Transactional
class LinkshareMailService {

	def mailService

	static transactional = false

	@Transactional(noRollbackFor = Exception.class)
	def sendMail(EmailCO emailCO, boolean asyncStatus) {

		MailLogger mailLogger = new MailLogger()
		mailLogger.properties = emailCO.properties
		mailLogger.save flush: true

		println("**************************************************************************")
		/*
		Transaction Check of Service Class
		 */
//		throw new RuntimeException('force throw to check Transactional Service....')

		mailService.sendMail {
			async asyncStatus
			to emailCO.mailTo
			from emailCO.mailFrom
			subject emailCO.subject
			html emailCO.message
		}
	}
}
