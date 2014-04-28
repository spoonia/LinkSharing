package com.ig.bootcamp

import org.codehaus.groovy.grails.commons.*
import org.codehaus.groovy.grails.exceptions.GrailsRuntimeException
import org.codehaus.groovy.grails.lifecycle.ShutdownOperations
import org.codehaus.groovy.grails.orm.hibernate.validation.PersistentConstraintFactory
import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.codehaus.groovy.runtime.InvokerHelper
import org.hibernate.Criteria
import org.hibernate.FlushMode
import org.hibernate.HibernateException
import org.hibernate.Session
import org.hibernate.criterion.Restrictions
import org.hibernate.metadata.ClassMetadata
import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl
import org.springframework.orm.hibernate3.HibernateCallback
import org.springframework.orm.hibernate3.HibernateTemplate
import org.springframework.util.Assert
import org.springframework.validation.Errors

class CustomUniqueConstraint extends AbstractConstraint {
	private static final String DEFAULT_NOT_UNIQUE_MESSAGE_CODE = "default.not.unique.message";
	private static final String TARGET_DOMAIN_CLASS_ALIAS = "domain_";
	public static final String UNIQUE_CONSTRAINT = "cunique";
	private boolean unique;
	private List<String> uniquenessGroup = new ArrayList();

	public UniqueConstraint() {
		ShutdownOperations.addOperation(new Runnable() {
			public void run() {
				ConstrainedProperty.removeConstraint("cunique", PersistentConstraintFactory.class);
			}
		});
	}

	public boolean isUnique() {
		return unique;
	}

	public boolean isUniqueWithinGroup() {
		return !uniquenessGroup.isEmpty();
	}

	@Override
	boolean supports(Class type) {
		return true;
	}

	public void setParameter(Object constraintParameter) {
		if ((!(constraintParameter instanceof Boolean)) && (!(constraintParameter instanceof String)) && (!(constraintParameter instanceof CharSequence)) && (!(constraintParameter instanceof List))) {
			throw new IllegalArgumentException("Parameter for constraint [unique] of property [" + constraintPropertyName + "] of class [" + constraintOwningClass + "] must be a boolean or string value");
		}
		if ((constraintParameter instanceof List)) {
			for (Object parameter : (List) constraintParameter) {
				if ((!(parameter instanceof String)) && (!(parameter instanceof CharSequence))) {
					throw new IllegalArgumentException("Parameter for constraint [unique] of property [" + constraintPropertyName + "] of class [" + constraintOwningClass + "] must be a boolean or string value");
				}
				uniquenessGroup.add(parameter.toString());
			}
		} else if (((constraintParameter instanceof String)) || ((constraintParameter instanceof CharSequence))) {
			uniquenessGroup.add(constraintParameter.toString());
			unique = true;
		} else {
			unique = ((Boolean) constraintParameter).booleanValue();
		}
		if (!uniquenessGroup.isEmpty()) {
			unique = true;
			for (Object anUniquenessGroup : uniquenessGroup) {
				String propertyName = (String) anUniquenessGroup;
				if (GrailsClassUtils.getPropertyType(constraintOwningClass, propertyName) == null) {
					throw new IllegalArgumentException("Scope for constraint [unique] of property [" + constraintPropertyName + "] of class [" + constraintOwningClass + "] must be a valid property name of same class");
				}
			}
		}
		super.setParameter(constraintParameter);
	}

	public String getName() {
		return "cunique";
	}

	protected void processValidate(final Object target, final Object propertyValue, Errors errors) {
		if (!unique) {
			return;
		}
		Object id;
		try {
			id = InvokerHelper.invokeMethod(target, "ident", null);
		}
		catch (Exception e) {
			throw new GrailsRuntimeException("Target of [unique] constraints [" + target + "] is not a domain instance. Unique constraint can only be applied to " + "domain classes and not custom user types or embedded instances");
		}
		HibernateTemplate hibernateTemplate = getHibernateTemplate();
		Assert.state(hibernateTemplate != null, "Unable use [unique] constraint, no Hibernate SessionFactory found!");

		List<?> results = hibernateTemplate.executeFind(new HibernateCallback() {
			public List<?> doInHibernate(Session session)
					throws HibernateException {
				session.setFlushMode(FlushMode.MANUAL);
				try {
					boolean shouldValidate = true;
					Class<?> constraintClass = constraintOwningClass;
					if ((propertyValue != null) && (DomainClassArtefactHandler.isDomainClass(propertyValue.getClass()))) {
						shouldValidate = session.contains(propertyValue);
					}
					GrailsApplication application;
					if (shouldValidate) {
						application = (GrailsApplication) applicationContext.getBean("grailsApplication");
						GrailsDomainClass domainClass = (GrailsDomainClass) application.getArtefact("Domain", constraintClass.getName());
						if ((domainClass != null) && (!domainClass.isRoot())) {
							GrailsDomainClassProperty property = domainClass.getPropertyByName(constraintPropertyName);
							while ((property.isInherited()) && (domainClass != null)) {
								domainClass = (GrailsDomainClass) application.getArtefact("Domain", domainClass.getClazz().getSuperclass().getName());
								if (domainClass != null) {
									property = domainClass.getPropertyByName(constraintPropertyName);
								}
							}
							constraintClass = domainClass != null ? domainClass.getClazz() : constraintClass;
						}
						Criteria criteria = null;
						if (domainClass.getPersistentProperty(constraintPropertyName).isOneToOne()) {
							criteria = session.createCriteria(constraintClass, "domain_");

							String constraintPropertyAlias = constraintPropertyName + "_";
							criteria.createAlias("domain_." + constraintPropertyName, constraintPropertyAlias);

							GrailsDomainClassProperty property = domainClass.getPropertyByName(constraintPropertyName);
							ClassMetadata classMetadata = session.getSessionFactory().getClassMetadata(property.getType());
							String identifierPropertyName = classMetadata.getIdentifierPropertyName();

							BeanWrapper bean = new BeanWrapperImpl(propertyValue);
							Object identifierPropertyValue = bean.getPropertyValue(identifierPropertyName);

							criteria.add(Restrictions.eq(constraintPropertyAlias + "." + identifierPropertyName, identifierPropertyValue));
						} else {
							criteria = session.createCriteria(constraintClass).add(Restrictions.eq(constraintPropertyName, propertyValue));
						}
						if (uniquenessGroup != null) {
							for (Object anUniquenessGroup : uniquenessGroup) {
								String uniquenessGroupPropertyName = (String) anUniquenessGroup;
								Object uniquenessGroupPropertyValue = GrailsClassUtils.getPropertyOrStaticPropertyOrFieldValue(target, uniquenessGroupPropertyName);
								if ((uniquenessGroupPropertyValue != null) && (DomainClassArtefactHandler.isDomainClass(uniquenessGroupPropertyValue.getClass()))) {
									shouldValidate = session.contains(uniquenessGroupPropertyValue);
								}
								if (!shouldValidate) {
									break;
								}
								criteria.add(Restrictions.eq(uniquenessGroupPropertyName, uniquenessGroupPropertyValue));
							}
						}
						if (shouldValidate) {
							return criteria.list();
						}
						return Collections.EMPTY_LIST;
					}
					return Collections.EMPTY_LIST;
				}
				finally {
					session.setFlushMode(FlushMode.AUTO);
				}
			}
		});
		if (results.isEmpty()) {
			return;
		}
		boolean reject = false;
		if (id != null) {
			Object existing = results.get(0);
			Object existingId = null;
			try {
				existingId = InvokerHelper.invokeMethod(existing, "ident", null);
			}
			catch (Exception e) {
			}
			if (!id.equals(existingId)) {
				reject = true;
			}
		} else {
			reject = true;
		}
		if (reject) {
			Object[] args = [constraintPropertyName, constraintOwningClass, propertyValue];
			rejectValue(target, errors, "unique", args, getDefaultMessage("default.not.unique.message"));
		}
	}

	public List<String> getUniquenessGroup() {
		return uniquenessGroup;
	}
}
