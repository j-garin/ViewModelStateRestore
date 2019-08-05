package com.jgarin.base.ui

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jgarin.base.ui.entities.BaseEvent
import com.jgarin.base.ui.entities.BaseNavigationScreen
import com.jgarin.base.ui.entities.BaseNavigationWorkflow
import com.jgarin.base.ui.entities.BaseWorkflowState
import com.jgarin.base.wrappers.SingleLiveEvent
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * @param E event type
 * @param WS workflow state type
 * @param NS navigation screen type
 * @param NW workflow navigation type
 */
// I don't like having so many generic type parameters. We need to check if there's a way to reduce it
abstract class BaseViewModel<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow>(
/*a hook to restore state*/
	savedState: Bundle?
) : ViewModel() {


	protected val viewModelScope = CoroutineScope(Dispatchers.Default)

	protected abstract fun reduceState(event: E, prev: WS, screen: NS): WS
	protected abstract fun reduceScreen(event: E, prev: WS, screen: NS): NS
	protected abstract fun reduceWorkflow(event: E, prev: WS, screen: NS): NW?

	protected abstract fun saveState(outState: Bundle, state: WS, screen: NS)
	protected abstract fun readState(savedState: Bundle?): WS
	protected abstract fun readScreen(savedState: Bundle?): NS

	private val _stateStream = MutableLiveData<WS>()
		.apply { value = readState(savedState) }
	private val _navigationScreen = MutableLiveData<NS>()
		.apply { value = readScreen(savedState) }
	private val _navigationWorkflow = MutableLiveData<SingleLiveEvent<NW>>()

	private val coroutineChannel = Channel<E>()

	val stateStream: LiveData<WS> = _stateStream
	val navigationScreen: LiveData<NS> = _navigationScreen
	val navigationWorkflow: LiveData<SingleLiveEvent<NW>> = _navigationWorkflow

	init {
		viewModelScope.launch {
			for (event in coroutineChannel) {
				// I know it throws for nulls, but there're no nulls here unless you make changes to this file.
				val prev = requireNotNull(_stateStream.value)
				val screen = requireNotNull(_navigationScreen.value)

				// Calculating these in parallel
				val newState = async { reduceState(event, prev, screen) }
				val newScreen = async { reduceScreen(event, prev, screen) }
				val newWorkflow = async { reduceWorkflow(event, prev, screen) }

				awaitAll(newState, newScreen, newWorkflow)

				withContext(context = Dispatchers.Main) {
					_stateStream.value = newState.await()
					_navigationScreen.value = newScreen.await()
					_navigationWorkflow.value = newWorkflow.await()?.let { SingleLiveEvent(it) }
				}
			}
		}
	}

	protected fun submit(event: E) {
		viewModelScope.launch { coroutineChannel.send(event) }
	}

	// Saving state magic is here. Basically we're relying on the framework for this.
	fun onSaveViewModelState(outState: Bundle) {
		val state = stateStream.value ?: return
		val screen = navigationScreen.value ?: return
		saveState(outState, state, screen)
	}

	// Back navigation is here
	abstract fun onBackPressed()

	override fun onCleared() {
		coroutineChannel.close()
		viewModelScope.cancel()
	}

}







