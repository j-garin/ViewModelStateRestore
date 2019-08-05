package com.jgarin.login.email

import androidx.lifecycle.LiveData
import com.jgarin.base.ui.screen.BaseScreenAdapter
import com.jgarin.base.validators.Validator
import com.jgarin.login.State

internal class EmailInputScreenAdapter(stateStream: LiveData<State>) :
	BaseScreenAdapter<State, EmailInputState>(stateStream) {

	override fun map(workflowState: State): EmailInputState {
		return EmailInputState(
			email = workflowState.email,
			emailError = (workflowState.emailValid as? Validator.ValidationResult.Failure)?.reason,
			nextBtnEnabled = workflowState.email.isNotBlank() && workflowState.emailValid is Validator.ValidationResult.OK
		)
	}

}