class UrlMappings {

	static mappings = {
		"/"(controller: "login")
		"/$controller/$action?/$id?(.$format)?" {
			constraints {
				// apply constraints here
			}
		}
		"/login"(controller: "login", action: "index")
		"/reset"(controller: "login", action: "reset")
		"/register"(controller: "login", action: "register")
		"/resendEmail"(controller: "login", action: "resendEmail")
		"/dashboard"(controller: "user", action: "dashboard")
		"/stats"(controller: "admin", action: "stats")
		//"/"(view:"/index")
		"500"(view: '/error')
	}
}