package com.jgarin.login

import com.jgarin.base.ui.BaseReducer
import com.jgarin.base.wrappers.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope

internal class Reducer(scope: CoroutineScope, initialState: State, initialScreen: Screen) :
	BaseReducer<Event, State, Screen, WorkflowNavigation>(scope, initialState, initialScreen) {

	override suspend fun buildNewState(event: Event, prev: State, screen: Screen): State {
		return when (event) {
			Event.BackPressed          -> prev
			is Event.EmailInputChanged -> prev.copy(email = event.email)
			is Event.PassInputChanged  -> prev.copy(password = event.password)
			is Event.EmailValidated    -> prev.copy(emailValid = event.result)
			is Event.PasswordValidated -> prev.copy(passValid = event.result)
			Event.GoToPassword         -> prev
			Event.Login                -> prev
			Event.LoginSuccess         -> prev
			is Event.LoginError        -> prev.copy(
				loginError = SingleLiveEvent(event.error.message ?: "Unknown error message")
			)
		}
	}

	override suspend fun buildNewScreen(event: Event, prev: State, screen: Screen): Screen {
		return when (event) {
			Event.BackPressed          -> when (screen) {
				Screen.EmailInput -> screen
				Screen.PassInput  -> Screen.EmailInput
				Screen.Progress   -> screen
			}
			is Event.EmailInputChanged -> screen
			is Event.PassInputChanged  -> screen
			is Event.EmailValidated    -> screen
			is Event.PasswordValidated -> screen
			Event.GoToPassword         -> Screen.PassInput
			Event.Login                -> Screen.Progress
			Event.LoginSuccess         -> screen
			is Event.LoginError        -> Screen.PassInput
		}
	}

	override suspend fun buildNewWorkflow(
		event: Event,
		prev: State,
		screen: Screen
	): WorkflowNavigation? {
		return when (event) {
			Event.BackPressed   -> if (screen == Screen.EmailInput) WorkflowNavigation.EndWorkflow else null
			is Event.EmailInputChanged,
			is Event.PassInputChanged,
			is Event.EmailValidated,
			is Event.PasswordValidated,
			Event.GoToPassword,
			Event.Login,
			is Event.LoginError -> null
			Event.LoginSuccess  -> WorkflowNavigation.WorkflowOne
		}
	}

}