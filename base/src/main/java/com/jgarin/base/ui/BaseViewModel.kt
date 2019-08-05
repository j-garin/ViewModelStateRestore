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

	protected abstract val stateReducer: (E, WS, NS) -> WS
	protected abstract val screenReducer: (E, WS, NS) -> NS
	protected abstract val workflowReducer: (E, WS, NS) -> NW?

	protected abstract val stateSaver: (Bundle, WS, NS) -> Unit
	protected abstract val stateReader: (Bundle?) -> WS
	protected abstract val screenReader: (Bundle?) -> NS

	private val _stateStream by lazy {
		MutableLiveData<WS>().apply { value = stateReader(savedState) }
	}
	private val _navigationScreen by lazy {
		MutableLiveData<NS>().apply { value = screenReader(savedState) }
	}
	private val _navigationWorkflow by lazy {
		MutableLiveData<SingleLiveEvent<NW>>()
	}

	private val coroutineChannel by lazy {
		val channel = Channel<E>()

		viewModelScope.launch {
			for (event in channel) {
				// I know it throws for nulls, but there're no nulls here unless you make changes to this file.
				val prev = requireNotNull(_stateStream.value)
				val screen = requireNotNull(_navigationScreen.value)

				// Calculating these in parallel
				val newState = async { stateReducer(event, prev, screen) }
				val newScreen = async { screenReducer(event, prev, screen) }
				val newWorkflow = async { workflowReducer(event, prev, screen) }

				awaitAll(newState, newScreen, newWorkflow)

				withContext(context = Dispatchers.Main) {
					_stateStream.value = newState.await()
					_navigationScreen.value = newScreen.await()
					_navigationWorkflow.value = newWorkflow.await()?.let { SingleLiveEvent(it) }
				}
			}
		}

		channel
	}

	val stateStream: LiveData<WS> by lazy { _stateStream }
	val navigationScreen: LiveData<NS> by lazy { _navigationScreen }
	val navigationWorkflow: LiveData<SingleLiveEvent<NW>> by lazy { _navigationWorkflow }

	protected fun submit(event: E) {
		viewModelScope.launch { coroutineChannel.send(event) }
	}

	// Saving state magic is here. Basically we're relying on the framework for this.
	fun onSaveViewModelState(outState: Bundle) {
		val state = stateStream.value ?: return
		val screen = navigationScreen.value ?: return
		stateSaver(outState, state, screen)
	}

	// Back navigation is here
	abstract fun onBackPressed()

	override fun onCleared() {
		coroutineChannel.close()
		viewModelScope.cancel()
	}
}