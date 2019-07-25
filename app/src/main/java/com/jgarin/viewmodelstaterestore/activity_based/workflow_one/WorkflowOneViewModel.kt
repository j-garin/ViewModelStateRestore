package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import android.os.Bundle
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseReducer
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseViewModel

class WorkflowOneViewModel(savedState: Bundle?): BaseViewModel<WorkflowOneState, WorkflowOneScreen, WorkflowOneEvent>(savedState) {
	override val reducer: BaseReducer<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen>
		get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

	override fun onSaveViewModelState(outState: Bundle) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}