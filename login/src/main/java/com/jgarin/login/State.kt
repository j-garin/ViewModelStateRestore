package com.jgarin.login

import com.jgarin.base.ui.entities.BaseWorkflowState
import com.jgarin.base.validators.Validator
import com.jgarin.base.wrappers.SingleLiveEvent

internal data class State(
	val email: String,
	val password: String,
	val emailValid: Validator.ValidationResult,
	val passValid: Validator.ValidationResult,
	val loginError: SingleLiveEvent<String>?
) : BaseWorkflowState