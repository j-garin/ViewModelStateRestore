package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import com.jgarin.viewmodelstaterestore.activity_based.base.BaseReducer

class WorkflowOneReducer(initialScreen: WorkflowOneScreen, initialState: WorkflowOneState)
	: BaseReducer<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen, WorkflowOneWorkflowNavigation>(initialState, initialScreen) {

	override fun generateNewState(event: WorkflowOneEvent,
	                              prev: WorkflowOneState,
	                              screen: WorkflowOneScreen
	): WorkflowOneState {

		return when (event) {
			WorkflowOneEvent.GoToNext        -> prev
			WorkflowOneEvent.GoToInput       -> prev
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

	override fun generateNewScreen(event: WorkflowOneEvent,
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
			is WorkflowOneEvent.InputChanged -> screen
			WorkflowOneEvent.SaveInput,
			WorkflowOneEvent.CancelInput     -> WorkflowOneScreen.ScreenOne

			WorkflowOneEvent.OnBackPressed   -> when (screen) {
				WorkflowOneScreen.ScreenOne   -> screen
				WorkflowOneScreen.ScreenTwo,
				WorkflowOneScreen.ScreenThree -> WorkflowOneScreen.ScreenOne
			}
		}

	}

	override fun generateNewWorkflow(event: WorkflowOneEvent,
	                                 prev: WorkflowOneState,
	                                 screen: WorkflowOneScreen
	): WorkflowOneWorkflowNavigation? {

		return when (event) {
			WorkflowOneEvent.GoToNext      ->
				if (screen == WorkflowOneScreen.ScreenThree) {
					WorkflowOneWorkflowNavigation.WorkflowTwo
				} else {
					null
				}

			WorkflowOneEvent.GoToInput,
			is WorkflowOneEvent.InputChanged,
			WorkflowOneEvent.SaveInput,
			WorkflowOneEvent.CancelInput   -> null

			WorkflowOneEvent.OnBackPressed ->
				if (screen == WorkflowOneScreen.ScreenOne) {
					WorkflowOneWorkflowNavigation.Back
				} else {
					null
				}
		}

	}

}