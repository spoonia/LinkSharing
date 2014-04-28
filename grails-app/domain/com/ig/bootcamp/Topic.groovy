package com.ig.bootcamp

class Topic {

	User owner
	String topicName
	String description
	String scope
	Date dateCreated
	Date lastUpdated

//	static searchable = {
//		mapping {
//			boost 2.0
//			spellCheck "include"
//		}
//	}

	static mapping = {
		name: 'topic_id'
	}

	static belongsTo = [owner:User]

	static constraints = {
		topicName unique: true,nullable: false,blank: false
		scope inList: ['Private','Public']
	}

	@Override
	public String toString() {
		return "Topic{$topicName,$scope}"
//		return topicName
	}
}
