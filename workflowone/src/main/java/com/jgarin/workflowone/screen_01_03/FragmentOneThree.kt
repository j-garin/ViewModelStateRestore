package com.jgarin.workflowone.screen_01_03

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.jgarin.base.LayoutResId
import com.jgarin.workflowone.R
import com.jgarin.workflowone.WorkflowOneViewModel
import kotlinx.android.synthetic.main.fragment_01_03.*

internal class FragmentOneThree : com.jgarin.base.BaseScreenFragment() {

	override val layout: LayoutResId = R.layout.fragment_01_03

	private val viewModel by lazy {
		ViewModelProviders.of(requireActivity())
			.get(WorkflowOneViewModel::class.java)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		btnNext.setOnClickListener { viewModel.next() }

	}

}