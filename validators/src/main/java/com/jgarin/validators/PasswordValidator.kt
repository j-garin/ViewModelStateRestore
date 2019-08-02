package com.jgarin.validators

import com.jgarin.base.validators.Validator

internal class PasswordValidator : Validator<String> {

	override fun validate(value: String): Validator.ValidationResult {
		return when {
			value.isBlank()                  -> Validator.ValidationResult
				.Failure(EmptyPassword)
			value.length < 6 -> Validator.ValidationResult
				.Failure(ShortPassword)
			 !value.contains(lowercaseRegex) -> Validator.ValidationResult
				 .Failure(NotContainsLowercase)
			 !value.contains(uppercaseRegex) -> Validator.ValidationResult
				 .Failure(NotContainsUppercase)
			 !value.contains(numberRegex)   -> Validator.ValidationResult
				 .Failure(NotContainsNumber)
			else                             -> Validator.ValidationResult.OK
		}
	}

	companion object {

		private val numberRegex = "[0-9]".toRegex()
		private val uppercaseRegex = "[A-Z]".toRegex()
		private val lowercaseRegex = "[a-z]".toRegex()

		private const val EmptyPassword = "Password is empty"
		private const val ShortPassword = "Password is short"
		private const val NotContainsLowercase = "Password doesn't contain a lowercase symbol"
		private const val NotContainsUppercase = "Password doesn't contain an uppercase symbol"
		private const val NotContainsNumber = "Password doesn't contain a number"

	}
}