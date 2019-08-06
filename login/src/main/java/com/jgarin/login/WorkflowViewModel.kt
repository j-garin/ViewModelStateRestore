package com.jgarin.login

import android.os.Bundle
import com.jgarin.base.repository.LoginRepository
import com.jgarin.base.ui.BaseViewModel
import com.jgarin.base.validators.Validator
import com.jgarin.base.wrappers.Response
import com.jgarin.login.entities.Event
import com.jgarin.login.entities.Screen
import com.jgarin.login.entities.State
import com.jgarin.login.entities.WorkflowNavigation
import com.jgarin.login.functions.*
import com.jgarin.login.screens.email.EmailInputScreenAdapter
import com.jgarin.login.screens.loading.LoadingScreenAdapter
import com.jgarin.login.screens.password.PasswordInputScreenAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class WorkflowViewModel(
	savedState: Bundle?,
	private val emailValidator: Validator<String>,
	private val passwordValidator: Validator<String>,
	private val loginRepository: LoginRepository
) :
	BaseViewModel<Event, State, Screen, WorkflowNavigation>(savedState) {

	// Required for viewModel to work

	// Reducers
	override suspend fun reduceState(event: Event, prev: State, screen: Screen): State =
		buildNewState(event, prev, screen)

	override suspend fun reduceScreen(event: Event, prev: State, screen: Screen): Screen =
		buildNewScreen(event, prev, screen)

	override suspend fun reduceWorkflow(event: Event, prev: State, screen: Screen): WorkflowNavigation? =
		buildNewWorkflow(event, prev, screen)

	// State save-restore. Should I separate saving state and screen into different methods?
	override fun saveState(outState: Bundle, state: State) = saveWorkflowState(outState, state)

	override fun readState(savedState: Bundle?): State = readInitialState(savedState)

	override fun getInitialScreen(): Screen = Screen.EmailInput

	// Output streams for each screen
	val emailInputScreen = EmailInputScreenAdapter(stateStream)
	val passwordInputScreen = PasswordInputScreenAdapter(stateStream)
	val loadingScreen = LoadingScreenAdapter(stateStream)

	// UI events handlers
	override fun onBackPressed() {
		submit(Event.BackPressed)
	}

	fun emailEntered(email: String) {
		submit(Event.EmailInputChanged(email))

		// this is a fast operation. I've put it into a coroutine demonstration purposes
		viewModelScope.launch {
			val validationResult = emailValidator.validate(email)
			submit(Event.EmailValidated(validationResult))
		}
	}

	fun passwordEntered(password: String) {
		submit(Event.PassInputChanged(password))

		// this is a fast operation. I've put it into a coroutine demonstration purposes
		viewModelScope.launch {
			val validationResult = passwordValidator.validate(password)
			submit(Event.PasswordValidated(validationResult))
		}
	}

	fun onEmailScreenNextButtonClicked() {
		submit(Event.GoToPassword)
	}

	fun onPasswordScreenLoginButtonClicked() {
		val email = stateStream.value?.email ?: return
		val password = stateStream.value?.password ?: return

		submit(Event.Login)

		viewModelScope.launch {
			val result = loginRepository.login(email, password)
			launch(Dispatchers.Main) {
				when (result) {
					is Response.Success -> submit(Event.LoginSuccess)
					is Response.Error   -> submit(Event.LoginError(result.cause))
				}
			}
		}
	}

}