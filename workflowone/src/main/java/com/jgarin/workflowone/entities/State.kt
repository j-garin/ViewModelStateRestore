package com.jgarin.workflowone.entities

import com.jgarin.base.ui.entities.BaseWorkflowState

internal data class State(
		val input: String,
		val tmpInput: String
): BaseWorkflowState