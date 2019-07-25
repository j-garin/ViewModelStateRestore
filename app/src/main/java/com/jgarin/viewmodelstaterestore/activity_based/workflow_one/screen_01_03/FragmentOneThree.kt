package com.jgarin.viewmodelstaterestore.activity_based.workflow_one.screen_01_03

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.jgarin.viewmodelstaterestore.R
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseScreenFragment
import com.jgarin.viewmodelstaterestore.activity_based.base.LayoutResId
import com.jgarin.viewmodelstaterestore.activity_based.workflow_one.WorkflowOneViewModel
import kotlinx.android.synthetic.main.fragment_01_03.*

class FragmentOneThree: BaseScreenFragment() {

	override val layout: LayoutResId = R.layout.fragment_01_03

	private val viewModel by lazy {
		ViewModelProviders.of(requireActivity())
				.get(WorkflowOneViewModel::class.java)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		btnNext.setOnClickListener { viewModel.next() }

	}

}