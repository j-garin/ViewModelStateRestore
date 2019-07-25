package com.jgarin.viewmodelstaterestore.activity_based.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jgarin.viewmodelstaterestore.SingleLiveEvent

/**
 * @param E event type
 * @param WS workflow state type
 * @param NS navigation screen type
 */
abstract class BaseReducer<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow>(
		private val initialState: WS,
		private val initialScreen: NS
) {

	private val _stateStream = MutableLiveData<WS>().apply { value = initialState }
	private val _navigationScreen = MutableLiveData<NS>().apply { value = initialScreen }
	private val _navigationWorkflow = MutableLiveData<SingleLiveEvent<NW>>()

	val stateStream: LiveData<WS> = _stateStream
	val navigationScreen: LiveData<NS> = _navigationScreen
	val navigationWorkflow: LiveData<SingleLiveEvent<NW>> = _navigationWorkflow

	fun submit(event: E) {
		synchronized(this) {
			val newState = generateNewState(
					event,
					_stateStream.value!!,
					_navigationScreen.value!!
			)
			val newScreen = generateNewScreen(
					event,
					_stateStream.value!!,
					_navigationScreen.value!!
			)
			val newWorkflow = generateNewWorkflow(
					event,
					_stateStream.value!!,
					_navigationScreen.value!!
			)
			_stateStream.value = newState
			_navigationScreen.value = newScreen
			_navigationWorkflow.value = newWorkflow?.let { SingleLiveEvent(it) }
		}
	}

	protected abstract fun generateNewState(event: E, prev: WS, screen: NS): WS

	protected abstract fun generateNewScreen(event: E, prev: WS, screen: NS): NS

	protected abstract fun generateNewWorkflow(event: E, prev: WS, screen: NS): NW?

}