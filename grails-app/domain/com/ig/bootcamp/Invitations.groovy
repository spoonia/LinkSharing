package com.ig.bootcamp

class Invitations {

	Subscriptions initiator
	User receiver
	Topic topic
	String inviteStatus

	static belongsTo = [initiator: Subscriptions, receiver: User, topic: Topic]
	static constraints = {
		inviteStatus inList: ['Pending', 'Accepted', 'Rejected']
	}

	@Override
	public String toString() {
		return "Invitations{${initiator.subscriber.userId},${receiver.userId},${topic.topicName},$inviteStatus}"
	}
}
