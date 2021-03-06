package com.jgarin.workflowone.screens.summary

import android.os.Bundle
import android.view.View
import com.jgarin.base.ui.screen.BaseScreenFragment
import com.jgarin.base.ui.LayoutResId
import com.jgarin.workflowone.R
import com.jgarin.workflowone.di.WorkflowOneModule
import kotlinx.android.synthetic.main.fragment_01_03.*

internal class FragmentSummary : BaseScreenFragment() {

	override val layout: LayoutResId = R.layout.fragment_01_03

	private val viewModel by lazy {
		WorkflowOneModule.instance.getViewModel(requireActivity())
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		btnNext.setOnClickListener { viewModel.next() }

	}

}