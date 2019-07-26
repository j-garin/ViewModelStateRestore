package com.jgarin.workflowone

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.jgarin.base.BaseReducer
import com.jgarin.base.BaseViewModel
import com.jgarin.extensions.distinctUntilChanged
import com.jgarin.extensions.map

internal class WorkflowOneViewModel(savedState: Bundle?) : BaseViewModel<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen, WorkflowOneWorkflowNavigation>(savedState) {

	override fun onSaveViewModelState(outState: Bundle) {
		outState.putSerializable(SCREEN_KEY, reducer.navigationScreen.value)
		outState.putString(INPUT_KEY, reducer.stateStream.value?.input)
	}

	private fun initialState(savedState: Bundle?): WorkflowOneState {
		return WorkflowOneState(
				input = savedState?.getString(INPUT_KEY) ?: "",
				tmpInput = savedState?.getString(TMP_INPUT_KEY) ?: ""
		)
	}

	override fun buildReducer(savedState: Bundle?): BaseReducer<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen, WorkflowOneWorkflowNavigation> {
		return WorkflowOneReducer(
				initialScreen = savedState?.getSerializable(SCREEN_KEY)
						as? WorkflowOneScreen ?: WorkflowOneScreen.ScreenOne,
				initialState = initialState(savedState)
		)
	}

	val screenOne = object : ScreenOne {
		override val inputText: LiveData<String> = reducer.stateStream
				.map { workflowState -> if (workflowState.input.isEmpty()) "Value will be displayed here" else workflowState.input }
				.distinctUntilChanged()
		override val btnNextEnabled: LiveData<Boolean> = reducer.stateStream
				.map { workflowState -> workflowState.input.isNotEmpty() }
				.distinctUntilChanged()
	}

	val screenTwo = object : ScreenTwo {
		override val btnOkEnabled: LiveData<Boolean> = reducer.stateStream
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
		reducer.submit(WorkflowOneEvent.OnBackPressed)
	}

	fun next() {
		reducer.submit(WorkflowOneEvent.GoToNext)
	}

	fun goToInput() {
		reducer.submit(WorkflowOneEvent.GoToInput)
	}

	fun inputChanged(input: String) {
		reducer.submit(WorkflowOneEvent.InputChanged(input))
	}

	fun saveTmpInput() {
		reducer.submit(WorkflowOneEvent.SaveInput)
	}

	fun cancelInput() {
		reducer.submit(WorkflowOneEvent.CancelInput)
	}

	companion object {
		private const val SCREEN_KEY = "com.jgarin.viewmodelstaterestore.activity_based.workflow_one.WorkflowOneScreen"
		private const val INPUT_KEY = "com.jgarin.viewmodelstaterestore.activity_based.workflow_one.Input"
		private const val TMP_INPUT_KEY = "com.jgarin.viewmodelstaterestore.activity_based.workflow_one.TmpInput"
	}

}
