package com.jgarin.viewmodelstaterestore.activity_based.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * @param E event type
 * @param WS workflow state type
 * @param NS navigation screen type
 */
abstract class BaseReducer<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen>(
		private val initialState: WS,
		private val initialScreen: NS
) {

	private val _stateStream = MutableLiveData<WS>().apply { value = initialState }
	private val _navigationStream = MutableLiveData<NS>().apply { value = initialScreen }

	val stateStream: LiveData<WS> = _stateStream
	val navigationStream: LiveData<NS> = _navigationStream

	fun submit(event: E) {
		synchronized(this) {
			val (newState, newScreen) = reduce(
					event,
					_stateStream.value!!,
					_navigationStream.value!!
			)
			_stateStream.value = newState
			newScreen?.let { _navigationStream.value = newScreen }
		}
	}

	protected abstract fun reduce(event: E, prev: WS, screen: NS): Pair<WS, NS?>

}