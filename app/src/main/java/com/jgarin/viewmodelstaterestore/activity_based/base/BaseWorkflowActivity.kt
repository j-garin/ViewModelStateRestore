package com.jgarin.viewmodelstaterestore.activity_based.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.jgarin.viewmodelstaterestore.extensions.observeNonNull

typealias LayoutResId = Int

abstract class BaseWorkflowActivity<E : BaseEvent, WS : BaseWorkflowState, NS : BaseNavigationScreen, NW : BaseNavigationWorkflow> : AppCompatActivity() {

	protected abstract val layout: LayoutResId
	// Not sure about the visibility modifier here. Can you think of a case where you actually need the reference to this viewModel in the activity?
	protected lateinit var viewModel: BaseViewModel<E, WS, NS, NW>

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(layout)
		viewModel = getViewModel(savedInstanceState)
		viewModel.navigationStream.observeNonNull(this, ::handleScreenChange)
		viewModel.navigationWorkflow.observeNonNull(this) { singleLiveEvent ->
			singleLiveEvent.value?.let { handleWorkFlowChange(it) }
		}
	}

	override fun onBackPressed() {
		viewModel.onBackPressed()
	}

	override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
		outState?.let { viewModel.onSaveViewModelState(outState) }
		super.onSaveInstanceState(outState, outPersistentState)
	}

	protected abstract fun getViewModel(savedState: Bundle?): BaseViewModel<E, WS, NS, NW>

	protected abstract fun handleScreenChange(screen: NS)

	protected abstract fun handleWorkFlowChange(navigationEvent: NW)

}