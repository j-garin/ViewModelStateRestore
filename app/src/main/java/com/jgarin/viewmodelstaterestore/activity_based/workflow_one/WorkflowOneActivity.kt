package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.jgarin.viewmodelstaterestore.R
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseViewModel
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseWorkflowActivity
import com.jgarin.viewmodelstaterestore.activity_based.base.LayoutResId
import com.jgarin.viewmodelstaterestore.extensions.viewModelFactory

class WorkflowOneActivity: BaseWorkflowActivity<WorkflowOneState, WorkflowOneScreen, WorkflowOneEvent>() {

	override val layout: LayoutResId = R.layout.fragment_container

	override fun getViewModel(savedState: Bundle?): BaseViewModel<WorkflowOneState, WorkflowOneScreen, WorkflowOneEvent> {
		return ViewModelProviders.of(this, viewModelFactory { WorkflowOneViewModel(savedState) })
			.get(WorkflowOneViewModel::class.java)
	}

	override fun handleScreenChange(screen: WorkflowOneScreen) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}