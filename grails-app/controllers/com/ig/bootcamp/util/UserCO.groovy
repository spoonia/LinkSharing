package com.ig.bootcamp.util
import com.ig.bootcamp.Roles
import com.ig.bootcamp.User
import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.grails.databinding.BindingFormat
/**
 * Created by sandeep on 20/4/14.
 */
@grails.validation.Validateable
class UserCO {

	String userId
	String userName
	String email
	@BindingFormat('MMMM dd, yyyy')
	Date dateOfBirth
	String plainPassword
	String confirmPassword

	static belongsTo = [role: Roles]

	static constraints = {
		importFrom User

		userId cunique: true
		plainPassword equalsTo: "#userId"
	}

	@Override
	public String toString() {
		ConstrainedProperty constrainedProperty;
		return "UserCO{$userId,$userName,$email,$plainPassword,$dateOfBirth,$confirmPassword}"
	}
}