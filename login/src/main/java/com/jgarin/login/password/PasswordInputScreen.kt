package com.jgarin.login.password

import androidx.lifecycle.LiveData
import com.jgarin.base.validators.Validator
import com.jgarin.extensions.distinctUntilChanged
import com.jgarin.extensions.map
import com.jgarin.login.State

internal class PasswordInputScreen(stateStream: LiveData<State>) {

	val password = stateStream.map { state -> state.password }
		.distinctUntilChanged()

	val passwordError = stateStream
		.map { state -> (state.passValid as? Validator.ValidationResult.Failure)?.reason }
		.distinctUntilChanged()

	val loginBtnEnabled = stateStream
		.map { state -> state.password.isNotBlank() && state.passValid is Validator.ValidationResult.OK }
		.distinctUntilChanged()

}