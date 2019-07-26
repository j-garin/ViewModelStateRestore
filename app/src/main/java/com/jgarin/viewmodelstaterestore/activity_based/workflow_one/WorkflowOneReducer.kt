package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import com.jgarin.viewmodelstaterestore.activity_based.base.BaseReducer

class WorkflowOneReducer(initialScreen: WorkflowOneScreen, initialState: WorkflowOneState)
	: BaseReducer<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen, WorkflowOneWorkflowNavigation>(initialState, initialScreen) {

	override fun buildNewState(event: WorkflowOneEvent,
	                           prev: WorkflowOneState,
	                           screen: WorkflowOneScreen
	): WorkflowOneState {

		return when (event) {
			WorkflowOneEvent.GoToNext        -> prev // Navigation event. The state doesn't change
			WorkflowOneEvent.GoToInput       -> prev // Navigation event. The state doesn't change
			is WorkflowOneEvent.InputChanged -> prev.copy(tmpInput = event.input)
			WorkflowOneEvent.SaveInput       -> prev.copy(input = prev.tmpInput, tmpInput = "")
			WorkflowOneEvent.CancelInput     -> prev.copy(tmpInput = prev.input)

			WorkflowOneEvent.OnBackPressed   -> if (screen == WorkflowOneScreen.ScreenTwo) {
				prev.copy(tmpInput = prev.input)
			} else {
				prev
			}
		}

	}

	override fun buildNewScreen(event: WorkflowOneEvent,
	                            prev: WorkflowOneState,
	                            screen: WorkflowOneScreen
	): WorkflowOneScreen {

		return when (event) {
			WorkflowOneEvent.GoToNext        -> when (screen) {
				WorkflowOneScreen.ScreenOne   -> WorkflowOneScreen.ScreenThree
				WorkflowOneScreen.ScreenTwo   -> screen
				WorkflowOneScreen.ScreenThree -> screen
			}

			WorkflowOneEvent.GoToInput       -> WorkflowOneScreen.ScreenTwo
			is WorkflowOneEvent.InputChanged -> screen // Stay on the same screen
			WorkflowOneEvent.SaveInput,
			WorkflowOneEvent.CancelInput     -> WorkflowOneScreen.ScreenOne

			WorkflowOneEvent.OnBackPressed   -> when (screen) {
				WorkflowOneScreen.ScreenOne   -> screen // There's nothing before screen one
				WorkflowOneScreen.ScreenTwo,
				WorkflowOneScreen.ScreenThree -> WorkflowOneScreen.ScreenOne
			}
		}

	}

	override fun buildNewWorkflow(event: WorkflowOneEvent,
	                              prev: WorkflowOneState,
	                              screen: WorkflowOneScreen
	): WorkflowOneWorkflowNavigation? {

		return when (event) {
			WorkflowOneEvent.GoToNext      -> // Go to the next workflow from screen three
				if (screen == WorkflowOneScreen.ScreenThree) {
					WorkflowOneWorkflowNavigation.WorkflowTwo
				} else {
					null
				}

			WorkflowOneEvent.OnBackPressed -> // Go back from screen one
				if (screen == WorkflowOneScreen.ScreenOne) {
					WorkflowOneWorkflowNavigation.Back
				} else {
					null
				}

			else                           -> null // Do nothing otherwise
		}

	}

}