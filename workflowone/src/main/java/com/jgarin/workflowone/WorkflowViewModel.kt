package com.jgarin.workflowone

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.jgarin.base.ui.BaseViewModel
import com.jgarin.extensions.distinctUntilChanged
import com.jgarin.extensions.map
import com.jgarin.workflowone.entities.Event
import com.jgarin.workflowone.entities.Screen
import com.jgarin.workflowone.entities.State
import com.jgarin.workflowone.entities.WorkflowNavigation
import com.jgarin.workflowone.functions.*

internal class WorkflowViewModel(savedState: Bundle?) :
	BaseViewModel<Event, State, Screen, WorkflowNavigation>(
		savedState
	) {

	override fun reduceState(event: Event, prev: State, screen: Screen): State =
		buildNewState(event, prev, screen)

	override fun reduceScreen(event: Event, prev: State, screen: Screen): Screen =
		buildNewScreen(event, prev, screen)

	override fun reduceWorkflow(event: Event, prev: State, screen: Screen): WorkflowNavigation? =
		buildNewWorkflow(event, prev, screen)

	override fun saveState(outState: Bundle, state: State, screen: Screen): Unit =
		saveWorkflowState(outState, state, screen)

	override fun readState(savedState: Bundle?): State = readInitialState(savedState)

	override fun readScreen(savedState: Bundle?): Screen = readInitialScreen(savedState)

	val screenOne = object : ScreenOne {
		override val inputText: LiveData<String> = stateStream
			.map { workflowState -> if (workflowState.input.isEmpty()) "Value will be displayed here" else workflowState.input }
			.distinctUntilChanged()
		override val btnNextEnabled: LiveData<Boolean> = stateStream
			.map { workflowState -> workflowState.input.isNotEmpty() }
			.distinctUntilChanged()
	}

	val screenTwo = object : ScreenTwo {
		override val btnOkEnabled: LiveData<Boolean> = stateStream
			.map { workflowState -> workflowState.tmpInput.isNotEmpty() }
			.distinctUntilChanged()
	}

	interface ScreenOne {
		val inputText: LiveData<String>
		val btnNextEnabled: LiveData<Boolean>
	}

	interface ScreenTwo {
		val btnOkEnabled: LiveData<Boolean>
	}

	override fun onBackPressed() {
		submit(Event.OnBackPressed)
	}

	fun next() {
		submit(Event.GoToNext)
	}

	fun goToInput() {
		submit(Event.GoToInput)
	}

	fun inputChanged(input: String) {
		submit(Event.InputChanged(input))
	}

	fun saveTmpInput() {
		submit(Event.SaveInput)
	}

	fun cancelInput() {
		submit(Event.CancelInput)
	}

}
