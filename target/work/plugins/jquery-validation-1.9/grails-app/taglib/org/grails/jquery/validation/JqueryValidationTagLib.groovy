/* Copyright 2010-2012 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.grails.jquery.validation

/**
*
* @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
*
* @since 1.7
*/
class JqueryValidationTagLib {
	static namespace = "jqval"
	
	static writeAttrs( attrs, output) {
		// Output any remaining user-specified attributes
		attrs.each { k, v ->
			output << k
			output << '="'
			output << v.encodeAsHTML()
			output << '" '
		}
	}
	
	static LINK_WRITERS = [
		js: { url, constants, attrs ->
			def o = new StringBuilder()
			o << "<script src=\"${url.encodeAsHTML()}\" "
			
			// Output info from the mappings
			writeAttrs(constants, o)
			writeAttrs(attrs, o)
			
			o << '></script>'
			return o
		},
		
		link: { url, constants, attrs ->
			def o = new StringBuilder()
			o << "<link href=\"${url.encodeAsHTML()}\" "
			
			// Output info from the mappings
			writeAttrs(constants, o)
			writeAttrs(attrs, o)
			
			o << '/>'
			return o
		}
	]
	
	static RESOURCE_MAPPINGS = [
		js:[writer:'js', type:'text/javascript']
	]    
	
	static CDN_URLS = [
		microsoft: [
			js:{version, minified, additionalMethodsSupport -> 
				def links = [
					"http://ajax.aspnetcdn.com/ajax/jquery.validate/${version}/jquery.validate.${minified ? 'min.js' : 'js'}"
				]
				if (additionalMethodsSupport) {
					links << "http://ajax.aspnetcdn.com/ajax/jquery.validate/${version}/additional-methods.${minified ? 'min.js' : 'js'}"
				}
				return links
			}
		]
	] 
	
	def pluginManager

	def resources = { attrs ->
		def plugin = pluginManager.getGrailsPlugin('jqueryValidation')
		def version = plugin.version
		
		def minified = grailsApplication.config.jqueryValidation.get('minified', true)
		// must point to a key in CDN_URLS (currently 'microsoft' is the only key there)
		def cdn = grailsApplication.config.jqueryValidation.cdn?:false
		def additionalMethods = grailsApplication.config.jqueryValidation.additionalMethods?:false
		if (cdn && !CDN_URLS[cdn]) {
			throwTagError "Unknown CDN name: ${cdn}"
		}
		
		// use the .js always from cdn if requested
		if (cdn) {
			out << cdnLink (cdn:cdn, type:'js', version:version, minified:minified, additionalMethods:additionalMethods)
		}
		else {
			out << resourceLink(plugin: 'jqueryValidation', type:'js', dir:'js/jquery-validation',
					file:"jquery.validate.${minified ? 'min.js' : 'js'}")
			if (additionalMethods) {
				out << resourceLink(plugin: 'jqueryValidation', type:'js', dir:'js/jquery-validation',
						file:"additional-methods.${minified ? 'min.js' : 'js'}")
			}
		}
	}
	
	def resourceLink = { attrs ->
		def t = attrs.remove('type')
		def typeInfo = [:] + RESOURCE_MAPPINGS[t]
		if (!typeInfo) {
			throwTagError "Unknown resourceLink type: ${t}"
		}
		def url = attrs.remove('url')
		if (!url) {
			url = g.resource(plugin:attrs.remove('plugin'), dir:attrs.remove('dir'), file:attrs.remove('file'))
		}
		
		def writerName = typeInfo.remove('writer')
		def writer = LINK_WRITERS[writerName ?: 'link']
		out << writer(url, typeInfo, attrs)
	}
	
	def cdnLink = { attrs ->
		def t = attrs.type
		def cdnName = attrs.remove('cdn')
		def version = attrs.remove('version')
		def minified = attrs.remove('minified')
		def additionalMethods = attrs.remove('additionalMethods')
		def cdnUrlHandler = CDN_URLS[cdnName]
		if (!cdnUrlHandler) {
			throwTagError "Unknown CDN name: ${cdnName}"
		}
		
		def urls = cdnUrlHandler."$t"(version, minified, additionalMethods)
		
		urls.each { url ->
			out << resourceLink (attrs + [url:url])
		}
	}
}
