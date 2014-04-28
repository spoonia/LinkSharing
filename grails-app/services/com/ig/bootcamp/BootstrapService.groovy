package com.ig.bootcamp

import grails.transaction.Transactional

import javax.xml.bind.ValidationException

@Transactional
class BootstrapService {

	private boolean failOnError = false

	def appLoad() {

		println("Inside BootstrapService appLoad Method!")

		Roles roles
		User user
		Topic topic
		Subscriptions subscriptions
		Integer count
//		================================================================================================================
		createRole('Admin')
		createRole('User')
		roles = Roles.findByRole('Admin')
//		================================================================================================================
		user = createAdminUser('admin', 'admin@intelligrape.com', roles)
		//-------------------------------------------------------------------------------------------------------------
		topic = createSampleTopic('Sample-1', user)
		//-------------------------------------------------------------------------------------------------------------
		subscriptions = subscribeTopic(topic, user, true)
		//-------------------------------------------------------------------------------------------------------------
		count = ResourceInfo.countByTopic(topic) + 1
		for (int i = count; i <= 10; i++) {
			createURLResourceForTopicAndSubscription(topic, subscriptions, i)
		}
		markReadRandomResources(subscriptions, 3)

//		================================================================================================================
		user = createAdminUser('supervisor', 'supervisors@intelligrape.com', roles)
		//-------------------------------------------------------------------------------------------------------------
		topic = createSampleTopic('Sample-2', user)
		//-------------------------------------------------------------------------------------------------------------
		subscriptions = subscribeTopic(topic, user, true)
		//-------------------------------------------------------------------------------------------------------------
		count = ResourceInfo.countByTopic(topic) + 1
		for (int i = count; i <= 10; i++) {
			createURLResourceForTopicAndSubscription(topic, subscriptions, i)
		}
		markReadRandomResources(subscriptions, 3)
//		================================================================================================================
		//Fetch unread items for a user
		listUnreadResource('admin')
	}

	private Subscriptions subscribeTopic(Topic topic, User user, boolean isOwner) {
		if (Subscriptions.countByTopicAndSubscriber(topic, user) == 0) {
			Subscriptions subscriptions = new Subscriptions(isOwner: isOwner, subscriber: user, topic: topic, rating: 8)
			if (subscriptions.validate()) {
				subscriptions.save(flush: true, failOnError: failOnError)
			}
			if (subscriptions.hasErrors()) {
				println "Error while creating Subscription::${subscriptions.toString()}"
				subscriptions.getErrors().getAllErrors().each {
					error ->
						println error.toString();
				}
				throw new ValidationException("Error while creating Subscription!", subscriptions.errors)
			}
		}
		return Subscriptions.findByTopicAndSubscriber(topic, user)
	}

	private Topic createSampleTopic(String topicName, User owner) {
		if (Topic.countByTopicName(topicName) == 0) {
			Topic topic = new Topic(topicName: topicName, owner: owner,
					description: 'Sample Topic Created for Testing Purpose...', scope: 'Public')
			if (topic.validate()) {
				topic.save(flush: true, failOnError: failOnError)
			}
			if (topic.hasErrors()) {
				println "Error while creating Topic::${topic.toString()}"
				topic.getErrors().getAllErrors().each {
					error ->
						println error.toString();
				}
				throw new ValidationException("Error while creating Topic!", topic.errors)
			}
		}
		return Topic.findByTopicName(topicName)
	}

	private User createAdminUser(String userId, String email, Roles roles) {
		if (User.countByUserId(userId) == 0) {
			User user = new User(userId: userId, userName: 'Admin User', password: 'admin123'.encodeAsSHA1(),
					email: email, role: roles, enabled: true, dateOfBirth: new Date() - (22 * 365))

			user.plainPassword = "admin123"
			user.confirmPassword = user.plainPassword

			if (user.validate()) {
				user.save(flush: true, failOnError: failOnError);
			}

			if (user.hasErrors()) {
				println "Error while creating User::${user.toString()}"
				user.getErrors().getAllErrors().each {
					error ->
						println error.toString();
				}
				throw new ValidationException("Error while creating User!", user.errors)
			}
		}
		return User.findByUserId(userId)
	}

	private def createRole(String role) {
		if (Roles.countByRole(role) == 0) {
			Roles roles = new Roles(role: role)
			if (roles.validate()) {
				roles.save(flush: true, failOnError: failOnError)
			}

			if (roles.hasErrors()) {
				println "Error while getting/saving Role::${roles.toString()}"
				roles.getErrors().getAllErrors().each {
					error ->
						println error.toString();
				}
				throw new ValidationException('Not able to create role!', roles.errors)
			}
		}
	}

	private def listUnreadResource(String userId) {
		User user = User.findByUserId(userId)
		Subscriptions subscriptions = Subscriptions.findBySubscriber(user)
		List<UserResourceMapping> list = UserResourceMapping
				.findAllBySubscriptionAndIsRead(subscriptions, false)
		list.each {
			UserResourceMapping resourceMapping ->
				println("""Subscription:${resourceMapping.subscription.toString()}
					Resource:${resourceMapping.resource.topic.toString()}""")
		}
	}

	private def markReadRandomResources(Subscriptions subscriptions, int mark) {
		List<UserResourceMapping> list = UserResourceMapping
				.findAllBySubscriptionAndIsRead(subscriptions, false)
				.sort { Math.random() }
				.with { it.size() <= mark ?: it.subList(0, mark) }
		list.each {
			UserResourceMapping resourceMapping ->
				resourceMapping.setIsRead(true)
				if (resourceMapping.validate()) {
					resourceMapping.save(flush: true)
				}
				if (resourceMapping.hasErrors()) {
					println "Error while marking resource as read::${resourceMapping.toString()}"
					resourceMapping.getErrors().getAllErrors().each {
						error ->
							println error.toString();
					}
					throw new ValidationException("Unable to mark resource as read!", resourceMapping.errors)
				}
		}
	}

	private def createURLResourceForTopicAndSubscription(Topic topic, Subscriptions subscriptions, int i) {
		ResourceInfo resourceInfo = new LinkResource(topic: topic, title: "ResourceTitle_$i",
				summary: "Providing a sample summary to topic '$topic' with title 'ResourceTitle_$i'",
				type: 'Url', url: "http://www.google.com/?q=$i")
		if (resourceInfo.validate()) {
			resourceInfo.save(flush: true, failOnError: failOnError)
		}
		if (resourceInfo.hasErrors()) {
			println "Error while getting/saving Resource::${resourceInfo.toString()}"
			resourceInfo.getErrors().getAllErrors().each {
				error ->
					println error.toString();
			}
			throw new ValidationException("Unable to create URL Resource!", resourceInfo.errors)
		} else {
			UserResourceMapping userResourceMapping = new UserResourceMapping(resource: resourceInfo,
					subscription: subscriptions,
					isRead: false)
			if (userResourceMapping.validate()) {
				userResourceMapping.save(flush: true, failOnError: failOnError)
			}
			if (userResourceMapping.hasErrors()) {
				println "Error while getting/saving ResourceMapping::${userResourceMapping.toString()}"
				userResourceMapping.getErrors().getAllErrors().each {
					error ->
						println error.toString();
				}
				throw new ValidationException("Unable to create UserResourceMapping!", userResourceMapping.errors)
			}
		}
	}
}