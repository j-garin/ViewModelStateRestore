package com.jgarin.workflowone.screens.input

import android.os.Bundle
import android.view.View
import com.jgarin.base.ui.screen.BaseScreenFragment
import com.jgarin.base.ui.LayoutResId
import com.jgarin.extensions.addAfterTextChangedListener
import com.jgarin.extensions.observeNonNull
import com.jgarin.workflowone.R
import com.jgarin.workflowone.di.WorkflowOneModule
import kotlinx.android.synthetic.main.fragment_input.*

internal class FragmentInput : BaseScreenFragment() {

	override val layout: LayoutResId = R.layout.fragment_input

	private val viewModel by lazy {
		WorkflowOneModule.instance.getViewModel(requireActivity())
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewModel.screenTwo.btnOkEnabled.observeNonNull(viewLifecycleOwner) { btnOk.isEnabled = it }

		etInput.addAfterTextChangedListener { viewModel.inputChanged(it) }
		btnOk.setOnClickListener { viewModel.saveTmpInput() }
		btnCancel.setOnClickListener { viewModel.cancelInput() }

	}

}