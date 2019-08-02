package com.jgarin.login

import com.jgarin.base.ui.BaseWorkflowState
import com.jgarin.base.validators.Validator
import com.jgarin.base.wrappers.SingleLiveEvent
import java.lang.Exception

internal data class State(
	val email: String,
	val password: String,
	val emailValid: Validator.ValidationResult,
	val passValid: Validator.ValidationResult,
	val loginError: SingleLiveEvent<String>?
) : BaseWorkflowState