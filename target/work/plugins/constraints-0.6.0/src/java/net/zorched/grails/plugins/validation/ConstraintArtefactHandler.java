/*
* Copyright 2006-2008 the original author or authors.
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
package net.zorched.grails.plugins.validation;

import org.codehaus.groovy.grails.commons.ArtefactHandlerAdapter;


/**
 * Grails artefact handler for constraint classes.
 *
 * @author Geofff Lane
 * @since 0.1
 */
public class ConstraintArtefactHandler extends ArtefactHandlerAdapter {

    public static final String TYPE = "Constraint";

    public ConstraintArtefactHandler() {
        super(TYPE, GrailsConstraintClass.class, DefaultGrailsConstraintClass.class, null);
    }

    public boolean isArtefactClass(Class clazz) {
    if (clazz == null) return false;
        if (!clazz.getName().endsWith(DefaultGrailsConstraintClass.CONSTRAINT)) {
            return false;
        }
        return true;
    }
}
