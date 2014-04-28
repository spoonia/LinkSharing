package com.ig.bootcamp
import org.codehaus.groovy.grails.commons.GrailsClassUtils
import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.springframework.validation.Errors

class EqualsToConstraint extends AbstractConstraint {

	EqualsToConstraint() {
		ConstrainedProperty.metaClass.getEqualsTo << {
			->
			ConstrainedProperty.DEFAULT_MESSAGES.put(DEFAULT_EQUAL_MESSAGE_CODE, DEFAULT_EQUAL_MESSAGE);
			constraintParameter
		}
	}
	public static final String CONSTRAINT_NAME = "equalsTo";
	public static final DEFAULT_EQUAL_MESSAGE_CODE = "default.${CONSTRAINT_NAME}.error";
	public static final DEFAULT_EQUAL_MESSAGE = "Value of [{0}] should be equal to value of [{1}]";

/*	protected String constraintPropertyName;
	protected Class<?> constraintOwningClass;
	protected Object constraintParameter;
	protected String classShortName;
	protected MessageSource messageSource;*/

	@Override
	protected void processValidate(Object target, Object propertyValue, Errors errors) {

		println 'Inside equalTo constraint'
		println target.getClass()
		//println target.properties
		println propertyValue
		println constraintPropertyName
		println constraintParameter
		println classShortName
		println messageSource

		if (!constraintParameter.equals(propertyValue)) {
			Object[] args = [constraintPropertyName, constraintParameter]

			rejectValue(target, errors, DEFAULT_EQUAL_MESSAGE_CODE, CONSTRAINT_NAME, args)
		}
	}

	@Override
	@SuppressWarnings("rawtypes")
	boolean supports(Class type) {
		return type && String.class.isAssignableFrom(type);
	}

	@Override
	String getName() {
		return CONSTRAINT_NAME
	}

	@Override
	public void setParameter(Object constraintParameter) {
		if (constraintParameter == null) {
			throw new IllegalArgumentException("Parameter for constraint [" + CONSTRAINT_NAME +
					"] of property [" + constraintPropertyName + "] of class [" +
					constraintOwningClass + "] cannot be null");
		}

		Class<?> propertyClass = GrailsClassUtils.getPropertyType(constraintOwningClass, constraintPropertyName);
		// TODO: Find an alternative way to do the UrlMapping check!
		if (!GrailsClassUtils.isAssignableOrConvertibleFrom(constraintParameter.getClass(), propertyClass) && propertyClass != null) {
			throw new IllegalArgumentException("Parameter for constraint [" +
					CONSTRAINT_NAME + "] of property [" +
					constraintPropertyName + "] of class [" + constraintOwningClass +
					"] must be the same type as property: [" + propertyClass.getName() + "]");
		}
		super.setParameter(constraintParameter);

	}
}
