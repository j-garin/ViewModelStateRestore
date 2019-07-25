package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseReducer
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseViewModel
import com.jgarin.viewmodelstaterestore.extensions.map

class WorkflowOneViewModel(savedState: Bundle?) : BaseViewModel<WorkflowOneState, WorkflowOneScreen, WorkflowOneEvent>(savedState) {

	override fun onSaveViewModelState(outState: Bundle) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

	override val reducer: BaseReducer<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen>
		get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

	val screenOne = object : ScreenOne {
		override val inputText: LiveData<String> = reducer.stateStream
				.map { workflowState -> if (workflowState.input.isEmpty()) "Value will be displayed here" else workflowState.input }
		override val btnNextEnabled: LiveData<Boolean> = reducer.stateStream
				.map { workflowState -> workflowState.input.isNotEmpty() }
	}

	interface ScreenOne {
		val inputText: LiveData<String>
		val btnNextEnabled: LiveData<Boolean>
	}

	fun next() {
		reducer.submit(WorkflowOneEvent.GoToNext)
	}

	fun goToInput() {
		reducer.submit(WorkflowOneEvent.GoToInput)
	}

}
