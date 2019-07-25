package com.jgarin.viewmodelstaterestore.activity_based.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.jgarin.viewmodelstaterestore.extensions.observeNonNull

typealias LayoutResId = Int

abstract class BaseWorkflowActivity<WS : BaseWorkflowState, NS : BaseNavigationScreen, E : BaseEvent>: AppCompatActivity() {

	protected abstract val layout: LayoutResId
	private lateinit var viewModel: BaseViewModel<WS, NS, E>

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)

		setContentView(layout)
		viewModel = getViewModel(savedInstanceState)
		viewModel.navigationStream.observeNonNull(this, ::handleScreenChange)
	}

	override fun onBackPressed() {
		// TODO think of a way to handle back navigation here
		super.onBackPressed()
	}

	override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
		outState?.let { viewModel.onSaveViewModelState(outState) }
		super.onSaveInstanceState(outState, outPersistentState)
	}

	protected abstract fun getViewModel(savedState: Bundle?): BaseViewModel<WS, NS, E>

	protected abstract fun handleScreenChange(screen: NS)

}