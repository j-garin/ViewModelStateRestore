package com.jgarin.workflowone.screen_01_01

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.jgarin.extensions.observeNonNull
import com.jgarin.workflowone.R
import com.jgarin.workflowone.WorkflowOneViewModel
import com.jgarin.workflowone.di.WorkflowOneModule
import kotlinx.android.synthetic.main.fragment_01_01.*

internal class FragmentOneOne : com.jgarin.base.BaseScreenFragment() {

	override val layout: com.jgarin.base.LayoutResId = R.layout.fragment_01_01

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