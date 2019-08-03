package com.jgarin.base.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jgarin.base.wrappers.SingleLiveEvent
import com.jgarin.extensions.distinctUntilChanged
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * @param E event type
 * @param WS workflow state type
 * @param NS navigation screen type
 */
abstract class BaseReducer<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow>(
	private val scope: CoroutineScope,
	initialState: WS,
	initialScreen: NS
) {

	private val _stateStream = MutableLiveData<WS>().apply { value = initialState }
	private val _navigationScreen = MutableLiveData<NS>().apply { value = initialScreen }
	private val _navigationWorkflow = MutableLiveData<SingleLiveEvent<NW>>()

	private val coroutineChannel = Channel<E>()

	init {
		scope.launch {
			for (event in coroutineChannel) {
				// I know it throws for nulls, but there're no nulls here unless you make changes to this file.
				val prev = requireNotNull(_stateStream.value)
				val screen = requireNotNull(_navigationScreen.value)

				// Calculating these in parallel
				val newState = async { buildNewState(event, prev, screen) }
				val newScreen = async { buildNewScreen(event, prev, screen) }
				val newWorkflow = async { buildNewWorkflow(event, prev, screen) }

				awaitAll(newState, newScreen, newWorkflow)

				withContext(context = Dispatchers.Main) {
					_stateStream.value = newState.await()
					_navigationScreen.value = newScreen.await()
					_navigationWorkflow.value = newWorkflow.await()?.let { SingleLiveEvent(it) }
				}
			}
		}
	}

	val stateStream: LiveData<WS> = _stateStream.distinctUntilChanged()
	val navigationScreen: LiveData<NS> = _navigationScreen.distinctUntilChanged()
	val navigationWorkflow: LiveData<SingleLiveEvent<NW>> =
		_navigationWorkflow.distinctUntilChanged()

	fun submit(event: E) {
		scope.launch { coroutineChannel.send(event) }
	}

	// I've split one `reduce` method into three. I think it simplifies things a lot on the implementation end

	protected abstract suspend fun buildNewState(event: E, prev: WS, screen: NS): WS

	// Hate the naming here. Any suggestions?
	protected abstract suspend fun buildNewScreen(event: E, prev: WS, screen: NS): NS

	// Hate the naming here. Any suggestions?
	protected abstract suspend fun buildNewWorkflow(event: E, prev: WS, screen: NS): NW?

}