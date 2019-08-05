package com.jgarin.login

import android.content.Context
import com.jgarin.base.App
import com.jgarin.base.ui.entities.BaseNavigationWorkflow

internal sealed class WorkflowNavigation : BaseNavigationWorkflow {
	object EndWorkflow : WorkflowNavigation()

	object WorkflowOne : WorkflowNavigation() {
		fun start(context: Context) {
			(context.applicationContext as App).startWorkflowOne(context)
		}
	}
}