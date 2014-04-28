package com.ig.bootcamp

class MailLogger {
	long userId
	String mailTo
	String mailFrom
	String mailCc
	String mailBcc
	String subject
	String contentType
	String message
	Date creationTime

	static mapping = {
		message type: 'text'
	}

	static constraints = {
		userId nullable: false, blank: false
		mailTo nullable: false, blank: false
		mailFrom nullable: false, blank: false
		mailCc nullable: true, blank: true
		mailBcc nullable: true, blank: true
		subject nullable: false, blank: false
		contentType nullable: false, blank: false
		message nullable: false, blank: false
		creationTime nullable: false, blank: false
	}


	@Override
	public String toString() {
		return "MailLogger{" +
				"id=" + id +
				", userId=" + userId +
				", to='" + mailTo + '\'' +
				", from='" + mailFrom + '\'' +
				", cc='" + mailCc + '\'' +
				", bcc='" + mailBcc + '\'' +
				", subject='" + subject + '\'' +
				", contentType='" + contentType + '\'' +
				", message='" + message + '\'' +
				", creationTime=" + creationTime +
				", version=" + version +
				"} ";
	}
}
