/* Copyright 2011-2012 the original author or authors.
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

/**
*
* @author Stephane Maldini
* @author <a href='mailto:limcheekin@vobject.com'>Lim Chee Kin</a>
*
* @since 1.7.3
*/

modules = {
    'jquery-validate' {
        dependsOn 'jquery'

        resource id:'js',
            url:[ plugin: 'jqueryValidation', dir: 'js/jquery-validation',
                  file:'jquery.validate.min.js'],
            nominify: true
    }

    'jquery-validate-dev' {
        dependsOn 'jquery'

        resource id:'js',
            url:[ plugin: 'jqueryValidation', dir: 'js/jquery-validation',
                  file:'jquery.validate.js']
    }

    'jquery-validate-additional-methods' {
        dependsOn 'jquery-validate'

        resource id:'js',
            url:[ plugin: 'jqueryValidation', dir: 'js/jquery-validation',
                  file:'additional-methods.min.js']
    }
    
    'jquery-validate-additional-methods-dev' {
        dependsOn 'jquery-validate-dev'

        resource id:'js',
            url:[ plugin: 'jqueryValidation', dir: 'js/jquery-validation',
                  file:'additional-methods.js']
    }    
}