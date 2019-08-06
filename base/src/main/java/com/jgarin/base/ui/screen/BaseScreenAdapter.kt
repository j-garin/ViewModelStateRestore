package com.jgarin.base.ui.screen

import androidx.lifecycle.LiveData
import com.jgarin.base.ui.entities.BaseScreenState
import com.jgarin.base.ui.entities.BaseWorkflowState
import com.jgarin.extensions.distinctUntilChanged
import com.jgarin.extensions.map

/**
 * Base class for workflowState -> screenState adapters.
 */
abstract class BaseScreenAdapter<WS : BaseWorkflowState, SS : BaseScreenState>(workflowStateStream: LiveData<WS>) {

	val screenState = workflowStateStream
		.map(::map)
		.distinctUntilChanged()

	abstract fun map(workflowState: WS): SS

}