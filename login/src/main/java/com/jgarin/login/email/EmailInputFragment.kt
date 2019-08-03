package com.jgarin.login.email

import android.os.Bundle
import android.view.View
import com.jgarin.base.ui.BaseScreenFragment
import com.jgarin.base.ui.LayoutResId
import com.jgarin.extensions.addAfterTextChangedListener
import com.jgarin.extensions.observeNonNull
import com.jgarin.login.LoginModule
import com.jgarin.login.R
import kotlinx.android.synthetic.main.fragment_email_input.*

internal class EmailInputFragment : BaseScreenFragment() {

	private val viewModel by lazy { LoginModule.instance.getViewModel(requireActivity()) }

	override val layout: LayoutResId = R.layout.fragment_email_input

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.emailInputScreen.emailError.observeNonNull(this) { etEmail.error = it }
		viewModel.emailInputScreen.nextBtnEnabled.observeNonNull(this) { btnNext.isEnabled = it }

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		etEmail.setText(viewModel.emailInputScreen.email.value)

		etEmail.addAfterTextChangedListener { viewModel.emailEntered(it) }

		btnNext.setOnClickListener { viewModel.onEmailScreenNextButtonClicked() }

	}

}