package com.jgarin.viewmodelstaterestore.activity_based.workflow_one.screen_01_01

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.jgarin.viewmodelstaterestore.R
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseScreenFragment
import com.jgarin.viewmodelstaterestore.activity_based.base.LayoutResId
import com.jgarin.viewmodelstaterestore.activity_based.workflow_one.WorkflowOneViewModel
import com.jgarin.viewmodelstaterestore.extensions.observeNonNull
import kotlinx.android.synthetic.main.fragment_01_01.*

class FragmentOneOne : BaseScreenFragment() {

	override val layout: LayoutResId = R.layout.fragment_01_01

	private val viewModel by lazy {
		ViewModelProviders.of(requireActivity())
				.get(WorkflowOneViewModel::class.java)
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