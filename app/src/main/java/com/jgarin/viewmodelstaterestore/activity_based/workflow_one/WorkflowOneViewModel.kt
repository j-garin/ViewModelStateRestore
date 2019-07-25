package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseReducer
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseViewModel
import com.jgarin.viewmodelstaterestore.extensions.map

class WorkflowOneViewModel(savedState: Bundle?) : BaseViewModel<WorkflowOneState, WorkflowOneScreen, WorkflowOneEvent>(savedState) {

	override fun onSaveViewModelState(outState: Bundle) {
		outState.putSerializable(SCREEN_KEY, reducer.navigationStream.value)
		outState.putString(INPUT_KEY, reducer.stateStream.value?.input)
	}

	private val initialState by lazy {
		WorkflowOneState(
				input = savedState?.getString(INPUT_KEY) ?: "",
				tmpInput = savedState?.getString(TMP_INPUT_KEY) ?: ""
		)
	}

	override val reducer: BaseReducer<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen> by lazy {
		WorkflowOneReducer(
				initialScreen = savedState?.getSerializable(SCREEN_KEY)
						as? WorkflowOneScreen ?: WorkflowOneScreen.ScreenOne,
				initialState = initialState
		)
	}

	val screenOne = object : ScreenOne {
		override val inputText: LiveData<String> = reducer.stateStream
				.map { workflowState -> if (workflowState.input.isEmpty()) "Value will be displayed here" else workflowState.input }
		override val btnNextEnabled: LiveData<Boolean> = reducer.stateStream
				.map { workflowState -> workflowState.input.isNotEmpty() }
	}

	val screenTwo = object : ScreenTwo {
		override val btnOkEnabled: LiveData<Boolean> = reducer.stateStream
				.map { workflowState -> workflowState.tmpInput.isNotEmpty() }
	}

	interface ScreenOne {
		val inputText: LiveData<String>
		val btnNextEnabled: LiveData<Boolean>
	}

	interface ScreenTwo {
		val btnOkEnabled: LiveData<Boolean>
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
