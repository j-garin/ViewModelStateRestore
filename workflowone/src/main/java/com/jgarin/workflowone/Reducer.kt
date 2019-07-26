package com.jgarin.workflowone

import com.jgarin.base.BaseReducer
import kotlinx.coroutines.CoroutineScope

internal class Reducer(scope: CoroutineScope, initialScreen: Screen, initialState: State)
	: BaseReducer<Event, State, Screen, WorkflowNavigation>(scope, initialState, initialScreen) {

	override suspend fun buildNewState(event: Event,
	                                   prev: State,
	                                   screen: Screen
	): State {

		return when (event) {
			Event.GoToNext        -> prev // Navigation event. The state doesn't change
			Event.GoToInput       -> prev // Navigation event. The state doesn't change
			is Event.InputChanged -> prev.copy(tmpInput = event.input)
			Event.SaveInput       -> prev.copy(input = prev.tmpInput, tmpInput = "")
			Event.CancelInput     -> prev.copy(tmpInput = prev.input)

			Event.OnBackPressed   -> if (screen == Screen.Input) {
				prev.copy(tmpInput = prev.input)
			} else {
				prev
			}
		}

	}

	override suspend fun buildNewScreen(event: Event,
	                                    prev: State,
	                                    screen: Screen
	): Screen {

		return when (event) {
			Event.GoToNext        -> when (screen) {
				Screen.Overview -> Screen.Summary
				Screen.Input    -> screen
				Screen.Summary  -> screen
			}

			Event.GoToInput       -> Screen.Input
			is Event.InputChanged -> screen // Stay on the same screen
			Event.SaveInput,
			Event.CancelInput     -> Screen.Overview

			Event.OnBackPressed   -> when (screen) {
				Screen.Overview -> screen // There's nothing before screen one
				Screen.Input,
				Screen.Summary  -> Screen.Overview
			}
		}

	}

	override suspend fun buildNewWorkflow(event: Event,
	                                      prev: State,
	                                      screen: Screen
	): WorkflowNavigation? {

		return when (event) {
			Event.GoToNext      -> // Go to the next workflow from screen three
				if (screen == Screen.Summary) {
					WorkflowNavigation.WorkflowTwo
				} else {
					null
				}

			Event.OnBackPressed -> // Go back from screen one
				if (screen == Screen.Overview) {
					WorkflowNavigation.Back
				} else {
					null
				}

			else                -> null // Do nothing otherwise
		}

	}

}