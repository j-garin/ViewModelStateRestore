package com.jgarin.login.functions

import android.os.Bundle
import com.jgarin.base.validators.Validator
import com.jgarin.login.WorkflowViewModel
import com.jgarin.login.entities.State

private val BaseKey = WorkflowViewModel::class.java.name
private val EmailKey = "$BaseKey.email"
private val PasswordKey = "$BaseKey.password"
private val EmailValidKey = "$BaseKey.emailValid"
private val PasswordValidKey = "$BaseKey.passwordValid"

internal fun saveWorkflowState(outState: Bundle, state: State) {
	val emailValidValue = when (val emailValid = state.emailValid) {
		Validator.ValidationResult.OK         -> null
		is Validator.ValidationResult.Failure -> emailValid.reason
	}
	val passValidValue = when (val passValid = state.emailValid) {
		Validator.ValidationResult.OK         -> null
		is Validator.ValidationResult.Failure -> passValid.reason
	}

	outState.putString(EmailKey, state.email)
	outState.putString(PasswordKey, state.password)
	outState.putString(EmailValidKey, emailValidValue)
	outState.putString(EmailValidKey, passValidValue)
}

internal fun readInitialState(savedState: Bundle?): State {
	val email = savedState?.getString(EmailKey) ?: ""
	val password = savedState?.getString(PasswordKey) ?: ""
	val emailValid = savedState?.getString(EmailValidKey)
		?.let { Validator.ValidationResult.Failure(it) }
		?: Validator.ValidationResult.OK
	val passwordValid = savedState?.getString(PasswordValidKey)
		?.let { Validator.ValidationResult.Failure(it) }
		?: Validator.ValidationResult.OK

	return State(
		email = email,
		password = password,
		emailValid = emailValid,
		passValid = passwordValid,
		loginError = null
	)
}