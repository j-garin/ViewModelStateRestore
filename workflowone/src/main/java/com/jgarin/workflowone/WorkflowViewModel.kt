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

	override val stateReducer: (Event, State, Screen) -> State = ::reduceState
	override val screenReducer: (Event, State, Screen) -> Screen = ::reduceScreen
	override val workflowReducer: (Event, State, Screen) -> WorkflowNavigation? = ::reduceWrokflow

	override val stateSaver: (Bundle, State, Screen) -> Unit = ::saveState
	override val stateReader: (Bundle?) -> State = ::readState
	override val screenReader: (Bundle?) -> Screen = ::readScreen

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
