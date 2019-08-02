package com.jgarin.validators

import com.jgarin.base.validators.Validator

class ValidatorsModule private constructor(){

	val emailValidator: Validator<String> by lazy { EmailValidator() }

	val passwordValidator: Validator<String> by lazy { PasswordValidator() }

	companion object {
		private val instance by lazy { ValidatorsModule() }

		fun init() = instance
	}
}