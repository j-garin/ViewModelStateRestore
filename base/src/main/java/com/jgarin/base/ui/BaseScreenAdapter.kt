package com.jgarin.base.ui

import androidx.lifecycle.LiveData
import com.jgarin.extensions.distinctUntilChanged
import com.jgarin.extensions.map

abstract class BaseScreenAdapter<WS : BaseWorkflowState, SS : BaseScreenState>(workflowStateStream: LiveData<WS>) {

	val screenState = workflowStateStream
		.map(::map)
		.distinctUntilChanged()

	abstract fun map(workflowState: WS): SS

}