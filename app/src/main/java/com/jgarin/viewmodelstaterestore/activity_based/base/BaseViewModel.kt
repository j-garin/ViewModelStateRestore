package com.jgarin.viewmodelstaterestore.activity_based.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 * @param E event type
 * @param WS workflow state type
 * @param NS navigation screen type
 */
abstract class BaseViewModel<WS : BaseWorkflowState, NS : BaseNavigationScreen, E : BaseEvent>(savedState: Bundle?) : ViewModel() {

	protected abstract val reducer: BaseReducer<E, WS, NS>

	val navigationStream: LiveData<NS>
		get() = reducer.navigationStream

	abstract fun onSaveViewModelState(outState: Bundle)


}







