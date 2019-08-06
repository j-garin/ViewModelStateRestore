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

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewModel.screenOne.btnNextEnabled.observeNonNull(viewLifecycleOwner) { btnNext.isEnabled = it }
		viewModel.screenOne.inputText.observeNonNull(viewLifecycleOwner) { tvInput.text = it }

		btnInput.setOnClickListener { viewModel.goToInput() }
		btnNext.setOnClickListener { viewModel.next() }

	}

}