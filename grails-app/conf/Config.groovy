import com.ig.bootcamp.EqualsToConstraint
import com.ig.bootcamp.CustomUniqueConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty

// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = 'com.ig.bootcamp'//appName // change this to alter the default package name and Maven
// publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
                      all          : '*/*', // 'all' maps to '*' or the first available format in withFormat
                      atom         : 'application/atom+xml',
                      css          : 'text/css',
                      csv          : 'text/csv',
                      form         : 'application/x-www-form-urlencoded',
                      html         : ['text/html', 'application/xhtml+xml'],
                      js           : 'text/javascript',
                      json         : ['application/json', 'text/json'],
                      multipartForm: 'multipart/form-data',
                      rss          : 'application/rss+xml',
                      text         : 'text/plain',
                      hal          : ['application/hal+json', 'application/hal+xml'],
                      xml          : ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
	views {
		gsp {
			encoding = 'UTF-8'
			htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
			codecs {
				expression = 'html' // escapes values inside ${}
				scriptlet = 'html' // escapes output from scriptlets in GSPs
				taglib = 'none' // escapes output from taglibs
				staticparts = 'none' // escapes output from static template parts
			}
		}
		// escapes all not-encoded output at final stage of outputting
		filteringCodecForContentType {
			//'text/html' = 'html'
		}
	}
	mail {
		host = "smtp.gmail.com"
		port = 465
		username = "sandeep.poonia@intelligrape.com"
		password = ""
		props = ["mail.smtp.auth"                  : "true",
		         "mail.smtp.socketFactory.port"    : "465",
		         "mail.smtp.socketFactory.class"   : "javax.net.ssl.SSLSocketFactory",
		         "mail.smtp.socketFactory.fallback": "false"]

	}
}

grails.mail.overrideAddress = "sandeep.poonia@intelligrape.com"
//default.date.format = MM-dd-yyyy // By default it shows hour minutes seconds and time zone,
//// which is most of the time is irrelevant.
//default.boolean.true = Yes
//default.boolean.false = No//Boolean default is 'True' and 'False' which doesn't look readable

grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
	development {
		grails.logging.jul.usebridge = true
	}
	production {
		grails.logging.jul.usebridge = false
		// TODO: grails.serverURL = "http://www.changeme.com"
	}
}

// log4j configuration
log4j = {
	// Example of changing the log pattern for the default console appender:
	//
	//appenders {
	//    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
	//}

	//Hibernate logging....
//	trace 'org.hibernate.type.descriptor.sql.BasicBinder'

	debug 'org.hibernate.SQL',
			'grails.app.controllers'

	error 'org.codehaus.groovy.grails.web.servlet',        // controllers
			'org.codehaus.groovy.grails.web.pages',          // GSP
			'org.codehaus.groovy.grails.web.sitemesh',       // layouts
			'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
			'org.codehaus.groovy.grails.web.mapping',        // URL mapping
			'org.codehaus.groovy.grails.commons',            // core / classloading
			'org.codehaus.groovy.grails.plugins',            // plugins
			'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
			'org.springframework',
			'org.hibernate',
			'net.sf.ehcache.hibernate'
}

// Added by the JQuery Validation UI plugin:
jqueryValidationUi {
	errorClass = 'error'
	validClass = 'valid'
	onsubmit = true
	renderErrorsOnTop = false
	qtip = true

	qTip {
		packed = true
		classes = 'ui-tooltip-red ui-tooltip-shadow ui-tooltip-rounded'
	}

	/*
	  Grails constraints to JQuery Validation rules mapping for client side validation.
	  Constraint not found in the ConstraintsMap will trigger remote AJAX validation.
	*/
	StringConstraintsMap = [
			blank     : 'required', // inverse: blank=false, required=true
			creditCard: 'creditcard',
			email     : 'email',
			inList    : 'inList',
			minSize   : 'minlength',
			maxSize   : 'maxlength',
			size      : 'rangelength',
			matches   : 'matches',
			notEqual  : 'notEqual',
			url       : 'url',
			nullable  : 'required',
			unique    : 'unique',
			validator : 'validator'
	]

	// Long, Integer, Short, Float, Double, BigInteger, BigDecimal
	NumberConstraintsMap = [
			min      : 'min',
			max      : 'max',
			range    : 'range',
			notEqual : 'notEqual',
			nullable : 'required',
			inList   : 'inList',
			unique   : 'unique',
			validator: 'validator'
	]

	CollectionConstraintsMap = [
			minSize  : 'minlength',
			maxSize  : 'maxlength',
			size     : 'rangelength',
			nullable : 'required',
			validator: 'validator'
	]

	DateConstraintsMap = [
			min      : 'minDate',
			max      : 'maxDate',
			range    : 'rangeDate',
			notEqual : 'notEqual',
			nullable : 'required',
			inList   : 'inList',
			unique   : 'unique',
			validator: 'validator'
	]

	ObjectConstraintsMap = [
			nullable : 'required',
			validator: 'validator'
	]

	CustomConstraintsMap = [
			phone               : 'true', // International phone number validation
			phoneUS             : 'true',
			alphanumeric        : 'true',
			letterswithbasicpunc: 'true',
			lettersonly         : 'true',
			equalsTo            : 'EqualsTo',
	]
}

// File: grails-app/conf/Config.groovy
grails.resources.modules = {
	core {
		dependsOn 'jquery-ui'
	}
	// Define reference to custom jQuery UI theme
	overrides {
		'jquery-theme' {
			resource id: 'theme', url: '/css/errors.css'
		}
	}
}

//Register my custom constraint---------
ConstrainedProperty.registerNewConstraint(
		EqualsToConstraint.CONSTRAINT_NAME,
		EqualsToConstraint.class)
ConstrainedProperty.registerNewConstraint(
		CustomUniqueConstraint.UNIQUE_CONSTRAINT,
		CustomUniqueConstraint.class)

simian {
	reportsDir = "target/simian-reports"
	fileDir = "."
	cludesFiles = [includes  : "**/*.groovy **/*.java"
	               , excludes: "**/*Spec.groovy **/*Test.groovy **/Config.groovy **/*.class **/*.xml **/*.log " +
			"**/*.properties **/plugins/* **/WEB-INF/* **/target/work/* **/web-app/*"]
//	cludesFiles = [includes: "**/*.groovy **/*.java"
//	               , excludes: "**/*Spec.groovy **/*Test.groovy **/Config.groovy **/WEB-INF/* **/src/templates/* **/target/work/* **/web-app/*"]
	methodParams = [threshold: 3]
}