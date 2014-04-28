package com.ig.bootcamp.filter

class LinkShareFilters {

	def filters = {
		auth(controller: "*", action: "*", controllerExclude: 'login|JQueryRemoteValidator|console',) {
			before = {
				if (!session.user) {
					redirect(controller: 'login', action: 'index')
					return false
				}
			}
			after = { Map model ->

			}
			afterView = { Exception e ->

			}
		}

		login(controller: 'login',) {
			before = {

			}
			after = { Map model ->

			}
			afterView = { Exception e ->

			}
		}
	}
}
