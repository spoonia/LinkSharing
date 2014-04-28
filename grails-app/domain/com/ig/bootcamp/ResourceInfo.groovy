package com.ig.bootcamp

class ResourceInfo {

	Topic topic
	String title
	String summary
	String type

	static mapping = {
		name: 'resource_id'
		tablePerHierarchy false
		summary type: 'text'
		userResourceMappings cascade: 'all-delete-orphan'
	}
	static belongsTo = [topic: Topic]

	static hasMany = [userResourceMappings: UserResourceMapping]

	static constraints = {
		type inList: ['Url','Document']
		summary nullable: false, blank: false, maxSize: 1024
		title nullable: false, blank: false
	}
	@Override
	public String toString() {
		return "ResourceInfo{${topic.toString()},$title,$type}"
	}
}