package com.ig.bootcamp

import grails.transaction.Transactional
import org.springframework.web.multipart.commons.CommonsMultipartFile

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class DocumentResourceController {

	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

	def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		respond DocumentResource.list(params), model: [documentResourceInstanceCount: DocumentResource.count()]
	}

	def show(DocumentResource documentResourceInstance) {
		respond documentResourceInstance
	}

	def create() {
		respond new DocumentResource(params)
	}

	def download() {
		println params
		def file = new File(servletContext.getRealPath("/uploads/${params.id}.${params.format}"))

		println file.toString()

		if (file.exists()) {
			response.setContentType("application/octet-stream")
			response.setHeader("Content-disposition", "Attachment;filename=${file.name}")
			response.setHeader("Content-Length", "${file.size()}")
			response.outputStream << file.bytes
			return
		}else{
			response.outputStream << "File not found!"
			return
		}
	}

	@Transactional
	def upload() {
		println params
		CommonsMultipartFile file = request.getFile('file') as CommonsMultipartFile

		if (file.empty || !file.originalFilename.toLowerCase().endsWith(".pdf")) {
			flash.message = 'Please upload a valid pdf file!'
		} else {
			flash.message = 'Document Uploaded Successfuly!'
			File path = new File(servletContext.getRealPath('/uploads'))
			if (!path.exists()) {
				path.mkdirs()
			}
			String fileName = file.originalFilename.substring(0, file.originalFilename.toLowerCase().indexOf('.pdf'))
			fileName += "_${System.currentTimeMillis()}_.pdf"

			file.transferTo(new File("${path}/${fileName}"))

			Topic topic = Topic.findById(params.get('topic.id').toString().toLong())

			params.topic = topic
			params.docName = fileName
			params.contentType = file.contentType
			params.size = file.size


			createDocumentResource(params)
			println '============file uploaded...........'
		}

		render(view: 'create')
		return
	}

	@org.springframework.transaction.annotation.Transactional
	private createDocumentResource(Map params) {
		DocumentResource documentResource = new DocumentResource(params)
		try {
			if (documentResource.validate()) {
				List<Subscriptions> subscriptionsList = Subscriptions.findAllByTopic(documentResource.topic)
				List<UserResourceMapping> userResourceMappingList = []

				if (subscriptionsList) {
					subscriptionsList.each {
						Subscriptions subscriptions ->
							userResourceMappingList <<
									new UserResourceMapping(subscription: subscriptions, resource: documentResource)
					}
				}

				documentResource.save flush: true, failOnError: true

				if (userResourceMappingList) {
					userResourceMappingList*.save flush: true, failOnError: true
				}
			}
			if (documentResource.hasErrors()) {
				println "Error while getting/saving Resource::${documentResource.toString()}"
				documentResource.getErrors().getAllErrors().each {
					error ->
						println error.toString(); System.exit(0);
				}
			}
		} catch (Exception ex) {
			flash.message = 'Internal Server Error- Not able to upload document!'
		}
	}

	@Transactional
	def save(DocumentResource documentResourceInstance) {
		if (documentResourceInstance == null) {
			notFound()
			return
		}

		if (documentResourceInstance.hasErrors()) {
			respond documentResourceInstance.errors, view: 'create'
			return
		}

		documentResourceInstance.save flush: true

		request.withFormat {
			form {
				flash.message = message(code: 'default.created.message', args: [message(code: 'documentResourceInstance.label', default: 'DocumentResource'), documentResourceInstance.id])
				redirect documentResourceInstance
			}
			'*' { respond documentResourceInstance, [status: CREATED] }
		}
	}

	def edit(DocumentResource documentResourceInstance) {
		respond documentResourceInstance
	}

	@Transactional
	def update(DocumentResource documentResourceInstance) {
		if (documentResourceInstance == null) {
			notFound()
			return
		}

		if (documentResourceInstance.hasErrors()) {
			respond documentResourceInstance.errors, view: 'edit'
			return
		}

		documentResourceInstance.save flush: true

		request.withFormat {
			form {
				flash.message = message(code: 'default.updated.message', args: [message(code: 'DocumentResource.label', default: 'DocumentResource'), documentResourceInstance.id])
				redirect documentResourceInstance
			}
			'*' { respond documentResourceInstance, [status: OK] }
		}
	}

	@Transactional
	def delete(DocumentResource documentResourceInstance) {

		if (documentResourceInstance == null) {
			notFound()
			return
		}

		documentResourceInstance.delete flush: true

		request.withFormat {
			form {
				flash.message = message(code: 'default.deleted.message', args: [message(code: 'DocumentResource.label', default: 'DocumentResource'), documentResourceInstance.id])
				redirect action: "index", method: "GET"
			}
			'*' { render status: NO_CONTENT }
		}
	}

	protected void notFound() {
		request.withFormat {
			form {
				flash.message = message(code: 'default.not.found.message', args: [message(code: 'documentResourceInstance.label', default: 'DocumentResource'), params.id])
				redirect action: "index", method: "GET"
			}
			'*' { render status: NOT_FOUND }
		}
	}
}
