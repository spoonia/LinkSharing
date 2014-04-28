package com.ig.bootcamp

class MailLogger {
	long userId
	String mailTo
	String mailFrom
	String mailCc
	String mailBcc
	String mailSubject
	String contentType
	String mailMessage
	Date mailCreationTime


	static mapping = {
		mailMessage type: 'text'
	}

	static constraints = {
		userId nullable: false, blank: false
		mailTo nullable: false, blank: false
		mailFrom nullable: false, blank: false
		mailCc nullable: true, blank: true
		mailBcc nullable: true, blank: true
		mailSubject nullable: false, blank: false
		contentType nullable: false, blank: false
		mailMessage nullable: false, blank: false
		mailCreationTime nullable: false, blank: false
	}


	@Override
	public String toString() {
		return "MailLogger{" +
				"userId=" + userId +
				", mailTo='" + mailTo + '\'' +
				", mailFrom='" + mailFrom + '\'' +
				", mailCc='" + mailCc + '\'' +
				", mailBcc='" + mailBcc + '\'' +
				", mailSubject='" + mailSubject + '\'' +
				", contentType='" + contentType + '\'' +
				", mailMessage='" + mailMessage + '\'' +
				", mailCreationTime=" + mailCreationTime +
				", id=" + id +
				"} " + super.toString();
	}
}
