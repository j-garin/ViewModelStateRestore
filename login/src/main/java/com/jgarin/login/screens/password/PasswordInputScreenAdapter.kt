package com.jgarin.login.screens.password

import androidx.lifecycle.LiveData
import com.jgarin.base.ui.screen.BaseScreenAdapter
import com.jgarin.base.validators.Validator
import com.jgarin.login.entities.State

internal class PasswordInputScreenAdapter(stateStream: LiveData<State>) :
	BaseScreenAdapter<State, PasswordInputScreenState>(stateStream) {

	override fun map(workflowState: State): PasswordInputScreenState {
		return PasswordInputScreenState(
			password = workflowState.password,
			passwordError = (workflowState.passValid as? Validator.ValidationResult.Failure)?.reason,
			loginBtnEnabled = workflowState.password.isNotBlank() && workflowState.passValid is Validator.ValidationResult.OK
		)
	}

}