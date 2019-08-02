package com.jgarin.base.validators

interface Validator<T> {

	fun validate(value: T): ValidationResult

	sealed class ValidationResult {
		object OK : ValidationResult()
		class Failure(val reason: String) : ValidationResult()
	}

}