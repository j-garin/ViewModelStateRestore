package com.jgarin.viewmodelstaterestore.activity_based.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jgarin.viewmodelstaterestore.SingleLiveEvent

/**
 * @param E event type
 * @param WS workflow state type
 * @param NS navigation screen type
 */
abstract class BaseViewModel<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow>(savedState: Bundle?) : ViewModel() {

	protected val reducer: BaseReducer<E, WS, NS, NW> by lazy { buildReducer(savedState) }

	val navigationStream: LiveData<NS>
		get() = reducer.navigationScreen

	val navigationWorkflow: LiveData<SingleLiveEvent<NW>>
		get() = reducer.navigationWorkflow

	protected abstract fun buildReducer(savedState: Bundle?): BaseReducer<E, WS, NS, NW>

	abstract fun onSaveViewModelState(outState: Bundle)

	abstract fun onBackPressed()

}







