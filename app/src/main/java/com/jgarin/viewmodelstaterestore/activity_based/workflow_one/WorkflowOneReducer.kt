package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import com.jgarin.viewmodelstaterestore.activity_based.base.BaseReducer

class WorkflowOneReducer(initialScreen: WorkflowOneScreen, initialState: WorkflowOneState)
	: BaseReducer<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen>(initialState, initialScreen) {

	override fun reduce(event: WorkflowOneEvent,
	                    prev: WorkflowOneState,
	                    screen: WorkflowOneScreen
	): Pair<WorkflowOneState, WorkflowOneScreen?> {
		return when (event) {
			WorkflowOneEvent.GoToNext -> prev to when (screen) {
				WorkflowOneScreen.ScreenOne -> WorkflowOneScreen.ScreenThree
				WorkflowOneScreen.ScreenTwo -> TODO()
				WorkflowOneScreen.ScreenThree -> TODO()
			}
			WorkflowOneEvent.GoToInput -> prev to WorkflowOneScreen.ScreenTwo
			is WorkflowOneEvent.InputChanged -> prev.copy(tmpInput = event.input) to null
			WorkflowOneEvent.SaveInput -> prev.copy(input = prev.tmpInput, tmpInput = "") to WorkflowOneScreen.ScreenOne
			WorkflowOneEvent.CancelInput -> prev.copy(tmpInput = prev.input) to WorkflowOneScreen.ScreenOne
		}

	}

}