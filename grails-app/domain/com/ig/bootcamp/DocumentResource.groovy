package com.ig.bootcamp

class DocumentResource extends ResourceInfo {

	String docName
	String contentType
	String size

	static constraints = {
		docName nullable: false, blank: false
		contentType nullable: false, blank: false

	}

	@Override
	public String toString() {
		return "DocumentResource{${super.toString()}}"
	}
}