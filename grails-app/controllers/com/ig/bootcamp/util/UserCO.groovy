package com.ig.bootcamp.util
import com.ig.bootcamp.Roles
import com.ig.bootcamp.User
import grails.validation.Validateable
import org.grails.databinding.BindingFormat
/**
 * Created by sandeep on 20/4/14.
 */
@Validateable
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

//		userId cunique: true
//		plainPassword equalsTo: "#userId"
	}

	@Override
	public String toString() {
		return "UserCO{$userId,$userName,$email,$plainPassword,$dateOfBirth,$confirmPassword}"
	}
}