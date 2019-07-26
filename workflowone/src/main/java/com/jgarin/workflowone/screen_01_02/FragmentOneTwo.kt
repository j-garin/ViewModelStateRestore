package com.jgarin.workflowone.screen_01_02

import android.os.Bundle
import android.view.View
import com.jgarin.base.LayoutResId
import com.jgarin.extensions.addAfterTextChangedListener
import com.jgarin.extensions.observeNonNull
import com.jgarin.workflowone.R
import com.jgarin.workflowone.di.WorkflowOneModule
import kotlinx.android.synthetic.main.fragment_input.*

internal class FragmentOneTwo : com.jgarin.base.BaseScreenFragment() {

	override val layout: LayoutResId = R.layout.fragment_input

	private val viewModel by lazy {
		WorkflowOneModule.instance.getViewModel(requireActivity())
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.screenTwo.btnOkEnabled.observeNonNull(this) { btnOk.isEnabled = it }

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		etInput.addAfterTextChangedListener { viewModel.inputChanged(it) }
		btnOk.setOnClickListener { viewModel.saveTmpInput() }
		btnCancel.setOnClickListener { viewModel.cancelInput() }

	}

}