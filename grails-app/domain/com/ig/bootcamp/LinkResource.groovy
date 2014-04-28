package com.ig.bootcamp

class LinkResource extends ResourceInfo{

	String url

	static constraints = {
		url url: true,nullable: false, blank: false
	}

	@Override
	public String toString() {
		return "LinkResource{${super.toString()}}"
	}
}
