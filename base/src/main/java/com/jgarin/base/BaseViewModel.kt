package com.jgarin.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jgarin.base.wrappers.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

/**
 * @param E event type
 * @param WS workflow state type
 * @param NS navigation screen type
 */
// I don't like having so many generic type parameters. We need to check if there's a way to reduce it
abstract class BaseViewModel<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow>(/*a hook to restore state*/savedState: Bundle?) : ViewModel() {

	protected val reducer: BaseReducer<E, WS, NS, NW> by lazy { buildReducer(savedState) }

	protected val viewModelScope = CoroutineScope(Dispatchers.Default)

	val navigationStream: LiveData<NS> = reducer.navigationScreen

	val navigationWorkflow: LiveData<SingleLiveEvent<NW>> = reducer.navigationWorkflow

	// Restoring state magic happens here
	protected abstract fun buildReducer(savedState: Bundle?): BaseReducer<E, WS, NS, NW>

	// Saving state magic is here. Basically we're relying on the framework for this.
	abstract fun onSaveViewModelState(outState: Bundle)

	// Back navigation is here
	abstract fun onBackPressed()

	override fun onCleared() {
		viewModelScope.cancel()
	}

}







