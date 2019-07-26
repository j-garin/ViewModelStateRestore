package com.jgarin.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jgarin.base.wrappers.SingleLiveEvent
import com.jgarin.extensions.distinctUntilChanged

/**
 * @param E event type
 * @param WS workflow state type
 * @param NS navigation screen type
 */
abstract class BaseReducer<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow>(
		initialState: WS,
		initialScreen: NS
) {

	private val _stateStream = MutableLiveData<WS>().apply { value = initialState }
	private val _navigationScreen = MutableLiveData<NS>().apply { value = initialScreen }
	private val _navigationWorkflow = MutableLiveData<SingleLiveEvent<NW>>()

	val stateStream: LiveData<WS> = _stateStream.distinctUntilChanged()
	val navigationScreen: LiveData<NS> = _navigationScreen.distinctUntilChanged()
	val navigationWorkflow: LiveData<SingleLiveEvent<NW>> = _navigationWorkflow.distinctUntilChanged()

	fun submit(event: E) {
		// I want to find a way to put this into a coroutine to make all the calculations happen on a background thread and to get rid of this synchronized function
		synchronized(this) {
			val prev = requireNotNull(_stateStream.value) // I know it throws for nulls, but there're no nulls here unless you make changes to this file.
			val screen = requireNotNull(_navigationScreen.value)

			// I know these can be inlined. Left it this way for coroutines.
			val newState = buildNewState(event, prev, screen)
			val newScreen = buildNewScreen(event, prev, screen)
			val newWorkflow = buildNewWorkflow(event, prev, screen)

			_stateStream.value = newState
			_navigationScreen.value = newScreen
			_navigationWorkflow.value = newWorkflow?.let { SingleLiveEvent(it) }
		}
	}

	// I've split one `reduce` method into three. I think it simplifies things a lot on the implementation end

	protected abstract fun buildNewState(event: E, prev: WS, screen: NS): WS

	// Hate the naming here. Any suggestions?
	protected abstract fun buildNewScreen(event: E, prev: WS, screen: NS): NS

	// Hate the naming here. Any suggestions?
	protected abstract fun buildNewWorkflow(event: E, prev: WS, screen: NS): NW?

}