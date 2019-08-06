package com.jgarin.base.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jgarin.base.ui.entities.BaseEvent
import com.jgarin.base.ui.entities.BaseNavigationScreen
import com.jgarin.base.ui.entities.BaseNavigationWorkflow
import com.jgarin.base.ui.entities.BaseWorkflowState
import com.jgarin.extensions.observeNonNull

typealias LayoutResId = Int

// I don't like having so many generic type parameters. We need to check if there's a way to reduce it
abstract class BaseWorkflowActivity<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow> :
	AppCompatActivity() {

	/**
	 * Layout resource to be used as this activity's view
	 */
	protected abstract val layout: LayoutResId

	/**
	 * Use this viewModel instance if you ever need to access it from the Activity
	 */
	protected lateinit var viewModel: BaseViewModel<E, WS, NS, NW>
		private set

	/**
	 * Make sure you call super if you override this.
	 */
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(layout)
		viewModel = getViewModel(savedInstanceState)
		viewModel.navigationScreen.observe(this, Observer { it?.value?.let(::handleScreenChange) })
		viewModel.navigationWorkflow.observeNonNull(this) { singleLiveEvent ->
			singleLiveEvent.value?.let { handleWorkFlowChange(it) } // Unwrap SingleLiveEvent to make sure navigation is only handled once
			// Perhaps we should use the same approach with navigation inside the workflow?
		}
	}

	// Chose the default implementation in case any libraries call this method
	/**
	 * Do NOT override. All back navigation should be handled by the state machine.
	 */
	override fun onBackPressed() {
		viewModel.onBackPressed()
	}

	/**
	 * Make sure you call super if you override this.
	 */
	override fun onSaveInstanceState(outState: Bundle?) {
		outState?.let { viewModel.onSaveViewModelState(outState) }
		super.onSaveInstanceState(outState)
	}

	/**
	 * Use [ViewModelProviders] to build instance of the viewModel
	 */
	protected abstract fun getViewModel(savedState: Bundle?): BaseViewModel<E, WS, NS, NW>

	/**
	 * Navigate to other screens inside this workflow.
	 * This is fired only when the screen change event happened and has not yet been handled.
	 * No additional checks required.
	 */
	protected abstract fun handleScreenChange(screen: NS)

	/**
	 * Navigate to other workflows.
	 * This is fired only when the workflow change event happened and has not yet been handled.
	 * No additional checks required.
	 * */
	protected abstract fun handleWorkFlowChange(navigationEvent: NW)

}