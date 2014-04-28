package com.ig.bootcamp.util

import com.ig.bootcamp.*

class UtilController {

	def index() {
		Roles roles
		User user
		Topic topic
		Subscriptions subscriptions
		ResourceInfo resourceInfo
		UserResourceMapping userResourceMapping
		List<UserResourceMapping> list

		//=============================================================================================================
		if (Roles.countByRole('User') == 0) {
			roles = new Roles(role: 'User')
			if (roles.validate()) {
				roles.save()
			}
		}
		if (Roles.countByRole('Admin') == 0) {
			roles = new Roles(role: 'Admin')
			if (roles.validate()) {
				roles.save()
			}
		} else {
			roles = Roles.findByRole('Admin')
		}
		if (roles.hasErrors()) {
			println "Error while getting/saving Role::${roles.toString()}"
			roles.getErrors().getAllErrors().each {
				error ->
					println error.toString(); 
			}
		}
		//-------------------------------------------------------------------------------------------------------------
		if (User.countByUserId('Admin') == 0) {
			user = new User(userId: 'admin', userName: 'Admin User', email: 'admin@intelligrape.com',
					password: 'admin123'.encodeAsSHA1(), role: roles, enabled: true, dateOfBirth: new Date() - (21 * 365))
			user.plainPassword = "admin123"
			user.confirmPassword = user.plainPassword
			if (user.validate()) {
				user.save();
			}
		} else {
			user = User.findByUserId('Admin')
		}
		if (user.hasErrors()) {
			println "Error while getting/saving User::${user.toString()}"
			user.getErrors().getAllErrors().each {
				error ->
					println error.toString(); 
			}
		}
		//-------------------------------------------------------------------------------------------------------------
		if (Topic.countByTopicName('Sample-1') == 0) {
			topic = new Topic(topicName: 'Sample-1', owner: user, description: 'Sample Topic Created for Testing Purpose...', scope: 'Public')
			if (topic.validate()) {
				topic.save()
			}
		} else {
			topic = Topic.findByTopicName('Sample-1')
		}
		if (topic.hasErrors()) {
			println "Error while getting/saving Topic::${topic.toString()}"
			topic.getErrors().getAllErrors().each {
				error ->
					println error.toString(); 
			}
		}
		//-------------------------------------------------------------------------------------------------------------
		if (Subscriptions.countByTopicAndSubscriber(topic, user) == 0) {
			subscriptions = new Subscriptions(isOwner: true, subscriber: user, topic: topic)
			if (subscriptions.validate()) {
				subscriptions.save()
			}
		} else {
			subscriptions = Subscriptions.findByTopicAndSubscriber(topic, user)
		}
		if (subscriptions.hasErrors()) {
			println "Error while getting/saving Subscription::${subscriptions.toString()}"
			subscriptions.getErrors().getAllErrors().each {
				error ->
					println error.toString(); 
			}
		}
		//-------------------------------------------------------------------------------------------------------------
		createResources(topic, subscriptions)
		markResourcesAsRead(subscriptions)

		//=============================================================================================================
		if (Roles.countByRole('Admin') == 0) {
			roles = new Roles(role: 'Admin')
			if (roles.validate()) {
				roles.save()
			}
		} else {
			roles = Roles.findByRole('Admin')
		}
		if (roles.hasErrors()) {
			println "Error while getting/saving Role::${roles.toString()}"
			roles.getErrors().getAllErrors().each {
				error ->
					println error.toString(); 
			}
		}
		//-------------------------------------------------------------------------------------------------------------
		if (User.countByUserId('Supervisor') == 0) {
			user = new User(userId: 'supervisor', userName: 'Supervisor', email: 'no-reply@intelligrape.com',
					password: 'supervisor351'.encodeAsSHA1(), role: roles, enabled: true,
					dateOfBirth: new Date() - (25 * 365))
			user.plainPassword = "supervisor351"
			user.confirmPassword = user.plainPassword
			if (user.validate()) {
				user.save();
			}
		} else {
			user = User.findByUserId('Supervisor')
		}
		if (user.hasErrors()) {
			println "Error while getting/saving User::${user.toString()}"
			user.getErrors().getAllErrors().each {
				error ->
					println error.toString(); 
			}
		}
		//-------------------------------------------------------------------------------------------------------------
		if (Topic.countByTopicName('Sample-2') == 0) {
			topic = new Topic(topicName: 'Sample-2', owner: user, description: 'Sample Topic Created for Testing ' +
					'Purpose...', scope: 'Public')
			if (topic.validate()) {
				topic.save()
			}
		} else {
			topic = Topic.findByTopicName('Sample-2')
		}
		if (topic.hasErrors()) {
			println "Error while getting/saving Topic::${topic.toString()}"
			topic.getErrors().getAllErrors().each {
				error ->
					println error.toString(); 
			}
		}
		//-------------------------------------------------------------------------------------------------------------
		if (Subscriptions.countByTopicAndSubscriber(topic, user) == 0) {
			subscriptions = new Subscriptions(isOwner: true, subscriber: user, topic: topic)
			if (subscriptions.validate()) {
				subscriptions.save()
			}
		} else {
			subscriptions = Subscriptions.findByTopicAndSubscriber(topic, user)
		}
		if (subscriptions.hasErrors()) {
			println "Error while getting/saving Subscription::${subscriptions.toString()}"
			subscriptions.getErrors().getAllErrors().each {
				error ->
					println error.toString(); 
			}
		}
		//-------------------------------------------------------------------------------------------------------------
		createResources(topic, subscriptions)
		markResourcesAsRead(subscriptions)
		//*************************************************************************************************************
		//Fetch unread items for a user
		println '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
		def listUnread = {
			String userId ->
				user = User.findByUserId(userId)
				subscriptions = Subscriptions.findBySubscriber(user)
				list = UserResourceMapping
						.findAllBySubscriptionAndIsRead(subscriptions, false)
				list.each {
					print it.subscription.toString()
					println it.resource.toString()
				}
		}
		listUnread('admin')
	}

	private markResourcesAsRead(Subscriptions subscriptions) {
		List<UserResourceMapping> list
		list = UserResourceMapping
				.findAllBySubscriptionAndIsRead(subscriptions, false)
				.sort { Math.random() }
				.with { it.size() <= 3 ?: it.subList(0, 3) }
		list.each {
			it.setIsRead(true)
			if (it.validate()) {
				it.save(flush: true)
			}
			if (it.hasErrors()) {
				println "Error while marking resource as read::${it.toString()}"
				it.getErrors().getAllErrors().each {
					error ->
						println error.toString();
				}
			}
		}
	}

	private createResources(Topic topic, Subscriptions subscriptions) {
		ResourceInfo resourceInfo
		UserResourceMapping userResourceMapping
		Integer count = ResourceInfo.countByTopic(topic) + 1
		for (int i = count; i <= 10; i++) {
//			if (i % 2) {
			resourceInfo = new LinkResource(topic: topic, title: "ResourceTitle_$i",
					summary: "Providing a sample summary to topic '$topic' with title 'ResourceTitle_$i'",
					type: 'Url', url: "http://www.google.com/?q=$i")
//			} else {
//				resourceInfo = new DocumentResource(topic: topic, title: "ResourceTitle_$i",
//						summary: "Providing a sample summary to topic '$topic' with title 'ResourceTitle_$i'",
//						type: 'Document', docName: "Document_$i", contentType: "Text/HTML", size: "10${i}KB")
//			}
			if (resourceInfo.validate()) {
				resourceInfo.save()
			}
			if (resourceInfo.hasErrors()) {
				println "Error while getting/saving Resource::${resourceInfo.toString()}"
				resourceInfo.getErrors().getAllErrors().each {
					error ->
						println error.toString();
				}
			} else {
				userResourceMapping = new UserResourceMapping(resource: resourceInfo, subscription: subscriptions,
						isRead: false)
				if (userResourceMapping.validate()) {
					userResourceMapping.save()
				}
				if (userResourceMapping.hasErrors()) {
					println "Error while getting/saving ResourceMapping::${userResourceMapping.toString()}"
					userResourceMapping.getErrors().getAllErrors().each {
						error ->
							println error.toString();
					}
				}
			}
		}
	}
}