package com.jgarin.workflowone

import com.jgarin.base.BaseWorkflowState

internal data class WorkflowOneState(
		val input: String,
		val tmpInput: String
): BaseWorkflowState