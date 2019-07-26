package com.jgarin.viewmodelstaterestore.activity_based.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jgarin.viewmodelstaterestore.extensions.observeNonNull

typealias LayoutResId = Int

// I don't like having so many generic type parameters. We need to check if there's a way to reduce it
abstract class BaseWorkflowActivity<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow> : AppCompatActivity() {

	protected abstract val layout: LayoutResId
	// Not sure about the visibility modifier here. Can you think of a case where you actually need the reference to this viewModel in the activity?
	// Lateinit because we need the savedState Bundle to restore viewModel state and we don't have that until onCreate
	protected lateinit var viewModel: BaseViewModel<E, WS, NS, NW>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(layout)
		viewModel = getViewModel(savedInstanceState)
		viewModel.navigationStream.observeNonNull(this, ::handleScreenChange)
		viewModel.navigationWorkflow.observeNonNull(this) { singleLiveEvent ->
			singleLiveEvent.value?.let { handleWorkFlowChange(it) } // Unwrap SingleLiveEvent to make sure navigation is only handled once
			// Perhaps we should use the same approach with navigation inside the workflow?
		}
	}

	// Chose the default implementation in case any libraries call this method
	override fun onBackPressed() {
		viewModel.onBackPressed()
	}

	override fun onSaveInstanceState(outState: Bundle?) {
		outState?.let { viewModel.onSaveViewModelState(outState) }
		super.onSaveInstanceState(outState)
	}

	protected abstract fun getViewModel(savedState: Bundle?): BaseViewModel<E, WS, NS, NW>

	protected abstract fun handleScreenChange(screen: NS)

	protected abstract fun handleWorkFlowChange(navigationEvent: NW)

}