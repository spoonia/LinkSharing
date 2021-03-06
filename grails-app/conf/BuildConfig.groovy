grails.servlet.version = "3.0" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.work.dir = "target/work"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
		// configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
		//  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

		// configure settings for the test-app JVM, uses the daemon by default
		test   : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon: true],
		// configure settings for the run-app JVM
		run    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
		// configure settings for the run-war JVM
		war    : [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve: false],
		// configure settings for the Console UI JVM
		console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
	// inherit Grails' default dependencies
	inherits("global") {
		// specify dependency exclusions here; for example, uncomment this to disable ehcache:
		// excludes 'ehcache'
	}
	log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
	checksums true // Whether to verify checksums on resolve
	legacyResolve false
	// whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

	repositories {
		inherits true // Whether to inherit repository definitions from plugins

		grailsPlugins()
		grailsHome()
		mavenLocal()
		grailsCentral()
		mavenCentral()
		// uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
		//mavenRepo "http://repository.codehaus.org"
		//mavenRepo "http://download.java.net/maven/2/"
		//mavenRepo "http://repository.jboss.com/maven2/"

		// For the Searchable plugin's 'compass' dependency
		mavenRepo "http://repo.grails.org/grails/core"
	}

	dependencies {
		// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.
		runtime 'mysql:mysql-connector-java:5.1.27'
		// runtime 'org.postgresql:postgresql:9.3-1100-jdbc41'
//		compile "org.apache.lucene:lucene-spellchecker:3.6.2"
//		compile "org.apache.lucene:lucene-core:4.7.2"
//		compile "org.apache.lucene:lucene-queries:4.7.2"
//		compile "org.apache.lucene:lucene-queryparser:4.7.2"
//		compile "org.apache.lucene:lucene-analyzers-common:4.7.2"
	}

	plugins {
		// plugins for the build system only
		build ":tomcat:7.0.50"

		// plugins for the compile step
		compile ":scaffolding:2.0.1"
		compile ':cache:1.1.1'
		compile ":mail:1.0.4"
//		compile ":searchable:0.6.6"
//		compile ":codenarc:0.20"
//		compile ":gmetrics:0.3.1"
//		compile ":simian:0.1-SNAPSHOT"

		// plugins needed at runtime but not for compilation
		runtime ":hibernate:3.6.10.7" // or ":hibernate4:4.1.11.6"
		runtime ":database-migration:1.3.8"
		runtime ":jquery:1.11.0.1"
		runtime ":jquery-ui:1.10.3"
		runtime ":jquery-validation:1.9"
//		runtime ":jquery-validation-ui:latest.release"
		runtime ":jquery-validation-ui:1.5.0"
		runtime ":resources:1.2.1"
		// Uncomment these (or add new ones) to enable additional resources capabilities
		//runtime ":zipped-resources:1.0.1"
		//runtime ":cached-resources:1.1"
		//runtime ":yui-minify-resources:0.1.5"
		compile ":console:latest.release"
	}
}

codenarc {
	ruleSetFiles = "file:grails-app/conf/CodeNarcRules.groovy" // Ruleset file path
	reports = {
		HtmlReport('html') {    // Report type is 'html'
			outputFile = 'target/CodeNarcReport.html' //Output file name
			title = 'My Test Code Narc Report' // Title of the file
		}
	}
}

