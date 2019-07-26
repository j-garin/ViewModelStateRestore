package com.jgarin.workflowone

import com.jgarin.base.BaseWorkflowState

internal data class State(
		val input: String,
		val tmpInput: String
): BaseWorkflowState