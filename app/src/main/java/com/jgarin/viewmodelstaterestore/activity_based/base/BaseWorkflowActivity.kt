package com.jgarin.viewmodelstaterestore.activity_based.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

typealias ViewId = Int
typealias LayoutResId = Int

abstract class BaseWorkflowActivity<WS : BaseWorkflowState, NS : BaseNavigationScreen, E : BaseEvent>: AppCompatActivity() {

	protected abstract val layout: LayoutResId
	protected abstract val fragmentContainer: ViewId
	private lateinit var viewModel: BaseViewModel<WS, NS, E>

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)

		setContentView(layout)
		viewModel = getViewModel(savedInstanceState)
	}

	override fun onBackPressed() {
		// TODO think of a way to handle back navigation here
		super.onBackPressed()
	}

	override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
		outState?.let { viewModel.onSaveViewModelState(outState) }
		super.onSaveInstanceState(outState, outPersistentState)
	}

	abstract fun getViewModel(savedState: Bundle?): BaseViewModel<WS, NS, E>

}