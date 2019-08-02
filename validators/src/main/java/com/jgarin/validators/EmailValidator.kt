package com.jgarin.validators

import android.util.Patterns
import com.jgarin.base.validators.Validator

internal class EmailValidator : Validator<String> {

	override fun validate(value: String): Validator.ValidationResult {
		return when {
			value.isBlank()               -> Validator.ValidationResult
				.Failure(EmptyEmail)
			!Patterns.EMAIL_ADDRESS
				.matcher(value).matches() ->
				Validator.ValidationResult.Failure(InvalidEmail)
			else                          -> Validator.ValidationResult.OK
		}
	}

	companion object {
		private const val EmptyEmail = "Email is empty"
		private const val InvalidEmail = "Invalid email format"
	}
}