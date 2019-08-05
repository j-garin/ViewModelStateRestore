package com.jgarin.workflowone.screens.overview

import android.os.Bundle
import android.view.View
import com.jgarin.base.ui.screen.BaseScreenFragment
import com.jgarin.base.ui.LayoutResId
import com.jgarin.extensions.observeNonNull
import com.jgarin.workflowone.R
import com.jgarin.workflowone.di.WorkflowOneModule
import kotlinx.android.synthetic.main.fragment_01_01.*

internal class FragmentOverview : BaseScreenFragment() {

	override val layout: LayoutResId = R.layout.fragment_01_01

	private val viewModel by lazy {
		WorkflowOneModule.instance.getViewModel(requireActivity())
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.screenOne.btnNextEnabled.observeNonNull(this) { btnNext.isEnabled = it }
		viewModel.screenOne.inputText.observeNonNull(this) { tvInput.text = it }

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		btnInput.setOnClickListener { viewModel.goToInput() }
		btnNext.setOnClickListener { viewModel.next() }

	}

}