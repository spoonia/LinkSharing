package com.ig.bootcamp

import grails.util.GrailsNameUtils
import org.grails.databinding.BindingFormat

class User {

	String userId
	String userName
	String email
	String password
	@BindingFormat('MMMM dd, yyyy')
	Date dateOfBirth
	Date lastLoginTime = new Date()
	Date dateCreated
	Date lastUpdated
	int failedLoginAttempts = 0
	boolean resetPassword = false
	boolean enabled = true
	boolean locked = false
	Roles role

	String plainPassword
	String confirmPassword

	static belongsTo = [role: Roles]
	static transients = ['plainPassword', 'confirmPassword']

	static constraints = {
		userId display: true, unique: true, nullable: false, blank: false, size: 5..15,
				matches: '[a-zA-Z]+[a-zA-Z0-9_-]*'
		userName display: true, nullable: false, blank: false, size: 3..100, matches: '[a-zA-Z]+[ ]?[a-zA-Z ]*'
		email display: true, email: true, unique: true, nullable: false, blank: false
		password display: false, nullable: false, blank: false
		dateOfBirth display: true, nullable: false, blank: false
		lastLoginTime disply: false
		failedLoginAttempts display: false
		resetPassword display: false
		enabled display: false
		locked display: false

		plainPassword bindable: true, display: true, nullable: false, blank: false, size: 8..24, password: true
		confirmPassword bindable: true, nullable: false, blank: false, password: true, confirmPassword: true,
				validator: {
					val, obj ->
						if (!val.equals(obj.plainPassword)) {
							String messageCode = "${GrailsNameUtils.getPropertyName(obj.class)}.password.mismatch"
							return [messageCode]
						} else if (val.equals(obj.userId)) {
							String messageCode = "${GrailsNameUtils.getPropertyName(obj.class)}.userId.password.matches"
							return [messageCode]
						}
				}
	}

	@Override
	public String toString() {
		return "User{$userId,$role,$enabled,$locked}"
	}
}