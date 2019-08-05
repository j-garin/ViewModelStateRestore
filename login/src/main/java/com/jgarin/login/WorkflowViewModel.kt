package com.jgarin.login

import android.os.Bundle
import com.jgarin.base.repository.LoginRepository
import com.jgarin.base.ui.BaseReducer
import com.jgarin.base.ui.BaseViewModel
import com.jgarin.base.validators.Validator
import com.jgarin.base.wrappers.Response
import com.jgarin.login.email.EmailInputScreenAdapter
import com.jgarin.login.loading.LoadingScreenAdapter
import com.jgarin.login.password.PasswordInputScreenAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class WorkflowViewModel(
	savedState: Bundle?,
	private val emailValidator: Validator<String>,
	private val passwordValidator: Validator<String>,
	private val loginRepository: LoginRepository
) :
	BaseViewModel<Event, State, Screen, WorkflowNavigation>(savedState) {

	override fun buildReducer(savedState: Bundle?): BaseReducer<Event, State, Screen, WorkflowNavigation> {
		val email = savedState?.getString(EmailKey) ?: ""
		val password = savedState?.getString(PasswordKey) ?: ""
		val emailValid = savedState?.getString(EmailValidKey)
			?.let { Validator.ValidationResult.Failure(it) }
			?: Validator.ValidationResult.OK
		val passwordValid = savedState?.getString(PasswordValidKey)
			?.let { Validator.ValidationResult.Failure(it) }
			?: Validator.ValidationResult.OK

		val initialState = State(
			email = email,
			password = password,
			emailValid = emailValid,
			passValid = passwordValid,
			loginError = null
		)

		val initialScreen = savedState?.getSerializable(ScreenKey) as? Screen ?: Screen.EmailInput

		return Reducer(
			viewModelScope,
			initialState,
			initialScreen
		)
	}

	override fun onSaveViewModelState(outState: Bundle) {
		val emailValid = reducer.stateStream.value?.emailValid
		val emailValidValue = when (emailValid) {
			null,
			Validator.ValidationResult.OK         -> null
			is Validator.ValidationResult.Failure -> emailValid.reason
		}
		val passValid = reducer.stateStream.value?.emailValid
		val passValidValue = when (passValid) {
			null,
			Validator.ValidationResult.OK         -> null
			is Validator.ValidationResult.Failure -> passValid.reason
		}

		outState.putString(EmailKey, reducer.stateStream.value?.email)
		outState.putString(PasswordKey, reducer.stateStream.value?.password)
		outState.putString(EmailValidKey, emailValidValue)
		outState.putString(EmailValidKey, passValidValue)
	}

	override fun onBackPressed() {
		reducer.submit(Event.BackPressed)
	}

	val emailInputScreen = EmailInputScreenAdapter(reducer.stateStream)

	val passwordInputScreen = PasswordInputScreenAdapter(reducer.stateStream)

	val loadingScreen = LoadingScreenAdapter(reducer.stateStream)

	fun emailEntered(email: String) {
		reducer.submit(Event.EmailInputChanged(email))

		// this is a fast operation. I've put it into a coroutine demonstration purposes
		viewModelScope.launch {
			val validationResult = emailValidator.validate(email)
			launch(Dispatchers.Main) { reducer.submit(Event.EmailValidated(validationResult)) }
		}
	}

	fun passwordEntered(password: String) {
		reducer.submit(Event.PassInputChanged(password))

		// this is a fast operation. I've put it into a coroutine demonstration purposes
		viewModelScope.launch {
			val validationResult = passwordValidator.validate(password)
			launch(Dispatchers.Main) { reducer.submit(Event.PasswordValidated(validationResult)) }
		}
	}

	fun onEmailScreenNextButtonClicked() {
		reducer.submit(Event.GoToPassword)
	}

	fun onPasswordScreenLoginButtonClicked() {
		val email = reducer.stateStream.value?.email ?: return
		val password = reducer.stateStream.value?.password ?: return

		reducer.submit(Event.Login)

		viewModelScope.launch {
			val result = loginRepository.login(email, password)
			launch(Dispatchers.Main) {
				when (result) {
					is Response.Success -> reducer.submit(Event.LoginSuccess)
					is Response.Error   -> reducer.submit(Event.LoginError(result.cause))
				}
			}
		}
	}

	companion object {
		private val BaseKey = WorkflowViewModel::class.java.name
		private val ScreenKey = "$BaseKey.screen"
		private val EmailKey = "$BaseKey.email"
		private val PasswordKey = "$BaseKey.password"
		private val EmailValidKey = "$BaseKey.emailValid"
		private val PasswordValidKey = "$BaseKey.passwordValid"
	}
}