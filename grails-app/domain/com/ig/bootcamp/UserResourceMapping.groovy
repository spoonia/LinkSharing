package com.ig.bootcamp

class UserResourceMapping {

	ResourceInfo resource
	Subscriptions subscription
	boolean isRead

	static belongsTo = [resource:ResourceInfo, subscription:Subscriptions]


	@Override
	public String toString() {
		return "UserResourceMapping{" +
				"resource=" + resource.toString() +
				", subscription=" + subscription.toString() +
				", isRead=" + isRead +
				"} ";
	}
}
