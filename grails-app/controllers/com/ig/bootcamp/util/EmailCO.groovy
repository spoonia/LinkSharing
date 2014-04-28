package com.ig.bootcamp.util

import com.ig.bootcamp.MailLogger

/**
 * Created by sandeep on 28/4/14.
 */
class EmailCO {

	long userId
	String mailTo
	String mailFrom
	String mailCc
	String mailBcc
	String subject
	String contentType
	String message
	Date creationTime = new Date()

	static constraints = {
		importFrom MailLogger
	}
}