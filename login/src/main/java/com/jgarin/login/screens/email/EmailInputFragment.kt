package com.jgarin.login.screens.email

import android.os.Bundle
import android.view.View
import com.jgarin.base.ui.screen.BaseScreenFragment
import com.jgarin.base.ui.LayoutResId
import com.jgarin.extensions.addAfterTextChangedListener
import com.jgarin.extensions.observeNonNull
import com.jgarin.login.di.LoginModule
import com.jgarin.login.R
import kotlinx.android.synthetic.main.fragment_email_input.*

internal class EmailInputFragment : BaseScreenFragment() {

	private val viewModel by lazy { LoginModule.instance.getViewModel(requireActivity()) }

	override val layout: LayoutResId = R.layout.fragment_email_input

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewModel.emailInputScreen.screenState.observeNonNull(viewLifecycleOwner, ::renderState)

		etEmail.setText(viewModel.emailInputScreen.screenState.value?.email)

		etEmail.addAfterTextChangedListener { viewModel.emailEntered(it) }

		btnNext.setOnClickListener { viewModel.onEmailScreenNextButtonClicked() }
	}

	private fun renderState(state: EmailInputState) {
		btnNext.isEnabled = state.nextBtnEnabled
		etEmail.error = state.emailError
	}

}