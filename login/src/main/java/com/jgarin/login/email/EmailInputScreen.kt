package com.jgarin.login.email

import androidx.lifecycle.LiveData
import com.jgarin.base.validators.Validator
import com.jgarin.extensions.distinctUntilChanged
import com.jgarin.extensions.map
import com.jgarin.login.State

internal class EmailInputScreen(stateStream: LiveData<State>) {

	val email = stateStream.map { state -> state.email }
		.distinctUntilChanged()

	val emailError = stateStream.map { state ->
		(state.emailValid as? Validator.ValidationResult.Failure)?.reason
	}

	val nextBtnEnabled = stateStream
		.map { state -> state.email.isNotBlank() && state.emailValid is Validator.ValidationResult.OK }
		.distinctUntilChanged()

}