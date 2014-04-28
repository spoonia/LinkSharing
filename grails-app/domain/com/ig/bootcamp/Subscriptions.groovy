package com.ig.bootcamp

class Subscriptions {

	User subscriber
	Topic topic
	boolean isOwner
	boolean isRead
	int rating

	static belongsTo = [subscriber:User, topic:Topic]

	static hasMany = [userResourceMappings:UserResourceMapping]

	static constraints = {
		rating range: 0..10
	}

	static mapping = {
		userResourceMappings cascade: 'all-delete-orphan'
	}

	@Override
	public String toString() {
		return "Subscriptions{${subscriber.userId},${topic.toString()}}"
	}
}
