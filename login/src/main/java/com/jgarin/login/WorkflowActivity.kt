package com.jgarin.login

import android.os.Bundle
import com.jgarin.base.ui.BaseViewModel
import com.jgarin.base.ui.BaseWorkflowActivity
import com.jgarin.base.ui.LayoutResId

internal class WorkflowActivity : BaseWorkflowActivity<Event, State, Screen, WorkflowNavigation>() {

	override val layout: LayoutResId = R.layout.fragment_container

	override fun getViewModel(savedState: Bundle?): BaseViewModel<Event, State, Screen, WorkflowNavigation> {
		return LoginModule.instance.initViewModel(this, savedState)
	}

	override fun handleScreenChange(screen: Screen) {

	}

	override fun handleWorkFlowChange(navigationEvent: WorkflowNavigation) {

	}
}