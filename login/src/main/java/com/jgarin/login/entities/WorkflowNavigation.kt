package com.jgarin.login.entities

import android.content.Context
import com.jgarin.base.App
import com.jgarin.base.ui.entities.BaseNavigationWorkflow
import kotlinx.android.parcel.Parcelize

internal sealed class WorkflowNavigation : BaseNavigationWorkflow {
	@Parcelize
	object EndWorkflow : WorkflowNavigation()

	@Parcelize
	object WorkflowOne : WorkflowNavigation() {
		fun start(context: Context) {
			(context.applicationContext as App).startWorkflowOne(context)
		}
	}
}