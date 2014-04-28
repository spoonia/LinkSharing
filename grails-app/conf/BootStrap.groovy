class BootStrap {

	def bootstrapService
	def init = { servletContext ->
//	    UtilController.newInstance().index()
		bootstrapService.appLoad()
	}
	def destroy = {
	}
}
