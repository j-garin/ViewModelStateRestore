package com.jgarin.workflowone.functions

import com.jgarin.workflowone.entities.Event
import com.jgarin.workflowone.entities.Screen
import com.jgarin.workflowone.entities.State
import com.jgarin.workflowone.entities.WorkflowNavigation

internal fun reduceState(event: Event, prev: State, screen: Screen): State {
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

internal fun reduceScreen(event: Event, prev: State, screen: Screen): Screen {
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

internal fun reduceWrokflow(event: Event, prev: State, screen: Screen): WorkflowNavigation? {
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
