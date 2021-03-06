package com.jgarin.workflowone.entities

import android.content.Context
import com.jgarin.base.App
import com.jgarin.base.ui.entities.BaseNavigationWorkflow
import kotlinx.android.parcel.Parcelize

internal sealed class WorkflowNavigation : BaseNavigationWorkflow {

	@Parcelize
	object Back : WorkflowNavigation()

	@Parcelize
	object WorkflowTwo : WorkflowNavigation() {
		// This could be replaced with context.launch.
		// Left it this way for now so that the caller can add its own flags if needed
		fun start(context: Context) {
			(context.applicationContext as App).startWorkflowTwo(context)
		}
	}

}