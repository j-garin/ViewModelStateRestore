package com.jgarin.workflowone

import android.content.Context
import com.jgarin.base.App
import com.jgarin.base.ui.BaseNavigationWorkflow

internal sealed class WorkflowNavigation : BaseNavigationWorkflow {

	object Back : WorkflowNavigation()

	object WorkflowTwo : WorkflowNavigation() {
		// This could be replaced with context.launch.
		// Left it this way for now so that the caller can add its own flags if needed
		fun start(context: Context) {
			(context.applicationContext as App).startWorkflowTwo(context)
		}
	}

}