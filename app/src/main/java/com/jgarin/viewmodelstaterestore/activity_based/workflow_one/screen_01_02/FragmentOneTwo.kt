package com.jgarin.viewmodelstaterestore.activity_based.workflow_one.screen_01_02

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.jgarin.viewmodelstaterestore.R
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseScreenFragment
import com.jgarin.viewmodelstaterestore.activity_based.base.LayoutResId
import com.jgarin.viewmodelstaterestore.activity_based.workflow_one.WorkflowOneViewModel
import com.jgarin.viewmodelstaterestore.extensions.addAfterTextChangedListener
import com.jgarin.viewmodelstaterestore.extensions.observeNonNull
import kotlinx.android.synthetic.main.fragment_input.*

class FragmentOneTwo : BaseScreenFragment() {

	override val layout: LayoutResId = R.layout.fragment_input

	private val viewModel by lazy {
		ViewModelProviders.of(requireActivity())
				.get(WorkflowOneViewModel::class.java)
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