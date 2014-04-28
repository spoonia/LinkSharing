package com.ig.bootcamp
import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

class DemoConstraint extends AbstractConstraint{

	public static final String CONSTRAINT_NAME = "demo"
	public static expectsParams = true
	public static defaultMessage = "Property [{0}] of class [{1}] with value [{2}] does not match the property [{3}]"

//	protected String constraintPropertyName;
//	protected Class<?> constraintOwningClass;
//	protected Object constraintParameter;
//	protected String classShortName;
//	protected MessageSource messageSource;

	protected void processValidate(Object target, Object propertyValue, Errors errors) {

		println '$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$'
		println target.getClass()
		//println target.properties
		println propertyValue
		println errors
		println constraintParameter
		println classShortName
		println messageSource


		if (propertyValue) {
			Object[] args = [constraintPropertyName, constraintOwningClass, propertyValue]
			println args
			rejectValue(target, errors, "Invalid time format", "default.time.invalidFormat.message", args)
			return
		}
	}

	boolean supports(Class type) {
		return type && String.class.isAssignableFrom(type);
	}

	String getName() {
		return CONSTRAINT_NAME;
	}
}
