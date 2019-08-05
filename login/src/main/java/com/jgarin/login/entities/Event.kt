package com.jgarin.login.entities

import com.jgarin.base.ui.entities.BaseEvent
import com.jgarin.base.validators.Validator

internal sealed class Event : BaseEvent {

	object BackPressed : Event()

	class EmailInputChanged(val email: String) : Event()
	class PassInputChanged(val password: String) : Event()

	class EmailValidated(val result: Validator.ValidationResult): Event()
	class PasswordValidated(val result: Validator.ValidationResult): Event()

	object GoToPassword : Event()

	object Login : Event()

	object LoginSuccess : Event()
	class LoginError(val error: Exception) : Event()

}