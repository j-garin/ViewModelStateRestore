package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import android.content.Context
import android.content.Intent
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseNavigationWorkflow
import com.jgarin.viewmodelstaterestore.activity_based.workflow_two.WorkflowTwoActivity

sealed class WorkflowOneWorkflowNavigation : BaseNavigationWorkflow {

	object Back : WorkflowOneWorkflowNavigation()

	object WorkflowTwo : WorkflowOneWorkflowNavigation() {
		fun getLaunchIntent(context: Context): Intent {
			return Intent(context, WorkflowTwoActivity::class.java)
		}
	}

}