package com.ig.bootcamp

class Roles {

	String role;

	static constraints = {
		role nullable: false, blank: false, inList: ['Admin','User','Guest']
	}

	@Override
	public String toString() {
		return "Roles{$role}"
	}
}
