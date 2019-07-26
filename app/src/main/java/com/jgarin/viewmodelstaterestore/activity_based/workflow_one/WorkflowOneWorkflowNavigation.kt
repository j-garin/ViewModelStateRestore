package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import android.content.Context
import android.content.Intent
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseNavigationWorkflow
import com.jgarin.viewmodelstaterestore.activity_based.workflow_two.WorkflowTwoActivity

sealed class WorkflowOneWorkflowNavigation : BaseNavigationWorkflow {

	object Back : WorkflowOneWorkflowNavigation()

	object WorkflowTwo : WorkflowOneWorkflowNavigation() {
		// This could be replaced with context.launch.
		// Left it this way for now so that the caller can add its own flags if needed
		fun getLaunchIntent(context: Context): Intent {
			return Intent(context, WorkflowTwoActivity::class.java)
		}
	}

}