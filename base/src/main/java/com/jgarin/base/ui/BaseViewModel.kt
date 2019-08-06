package com.jgarin.base.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jgarin.base.ui.entities.BaseEvent
import com.jgarin.base.ui.entities.BaseNavigationScreen
import com.jgarin.base.ui.entities.BaseNavigationWorkflow
import com.jgarin.base.ui.entities.BaseWorkflowState
import com.jgarin.base.wrappers.SingleLiveEvent
import com.jgarin.extensions.distinctUntilChanged
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

	private val screenKey = this::class.java.name + ".Screen"
	private val screenHandledKey = this::class.java.name + ".ScreenHandled"
	private val workflowKey = this::class.java.name + ".Workflow"
	private val workflowHandledKey = this::class.java.name + ".WorkflowHandled"

	protected val viewModelScope = CoroutineScope(Dispatchers.Default)

	protected abstract suspend fun reduceState(event: E, prev: WS, screen: NS): WS
	protected abstract suspend fun reduceScreen(event: E, prev: WS, screen: NS): NS?
	protected abstract suspend fun reduceWorkflow(event: E, prev: WS, screen: NS): NW?

	protected abstract fun saveState(outState: Bundle, state: WS)
	protected abstract fun readState(savedState: Bundle?): WS
	protected abstract fun getInitialScreen(): NS

	private val _stateStream = MutableLiveData<WS>()
		.apply { value = readState(savedState) }
	private val _navigationScreen = MutableLiveData<SingleLiveEvent<NS>>()
		.apply { value = readScreen(savedState) }
	private val _navigationWorkflow = MutableLiveData<SingleLiveEvent<NW>>()
		.apply { value = readWorkflow(savedState) }

	private val coroutineChannel = Channel<E>()

	val stateStream: LiveData<WS> = _stateStream.distinctUntilChanged()
	val navigationScreen: LiveData<SingleLiveEvent<NS>> = _navigationScreen.distinctUntilChanged()
	val navigationWorkflow: LiveData<SingleLiveEvent<NW>> =
		_navigationWorkflow.distinctUntilChanged()

	init {
		viewModelScope.launch(Dispatchers.Unconfined) {
			for (event in coroutineChannel) {
				// I know it throws for nulls, but there're no nulls here unless you make changes to this file.
				val prev = requireNotNull(_stateStream.value)
				val screen = requireNotNull(_navigationScreen.value?.peek())

				// Calculating these in parallel
				val newStateD = async { reduceState(event, prev, screen) }
				val newScreenD = async { reduceScreen(event, prev, screen) }
				val newWorkflowD = async { reduceWorkflow(event, prev, screen) }

				val newState = newStateD.await()
				val newScreen = newScreenD.await()
				val newWorkflow = newWorkflowD.await()

				withContext(Dispatchers.Main) {
					_stateStream.value = newState
					if (newScreen != null) _navigationScreen.value = SingleLiveEvent(newScreen)
					_navigationWorkflow.value = newWorkflow?.let { SingleLiveEvent(it) }
				}
			}
		}
	}

	protected fun submit(event: E) {
		viewModelScope.launch(Dispatchers.Unconfined) { coroutineChannel.send(event) }
	}

	// Saving state magic is here. Basically we're relying on the framework for this.
	fun onSaveViewModelState(outState: Bundle) {
		val state = stateStream.value ?: return
		val screen = navigationScreen.value ?: return
		val workflow = navigationWorkflow.value
		saveState(outState, state)
		saveScreen(outState, screen)
		saveWorkflow(outState, workflow)
	}

	// Back navigation is here
	abstract fun onBackPressed()

	override fun onCleared() {
		coroutineChannel.close()
		viewModelScope.cancel()
	}

	private fun saveScreen(outState: Bundle, screen: SingleLiveEvent<NS>) {
		Log.d("viewModel", "attempting to save screen from the bundle")
		outState.putParcelable(screenKey, screen.peek())
		outState.putBoolean(screenHandledKey, screen.isHandled)
	}

	private fun readScreen(savedState: Bundle?): SingleLiveEvent<NS> {
		Log.d("viewModel", "attempting to read saved screen from the bundle")
		return savedState?.getParcelable<NS>(screenKey)?.let {
			val screen = SingleLiveEvent(it)
			if (savedState.getBoolean(screenHandledKey, false)) screen.value // mark as handled
			screen
		} ?: SingleLiveEvent(getInitialScreen())
	}

	private fun saveWorkflow(outState: Bundle, workflow: SingleLiveEvent<NW>?) {
		Log.d("viewModel", "attempting to save workflow from the bundle")
		outState.putParcelable(workflowKey, workflow?.peek())
		outState.putBoolean(workflowHandledKey, workflow?.isHandled ?: true)
	}

	private fun readWorkflow(savedState: Bundle?): SingleLiveEvent<NW>? {
		Log.d("viewModel", "attempting to read saved workflow from the bundle")
		return savedState?.getParcelable<NW>(workflowKey)?.let {
			val screen = SingleLiveEvent(it)
			if (savedState.getBoolean(workflowHandledKey, false)) screen.value // mark as handled
			screen
		}
	}

}







