package com.jgarin.login.password

import android.os.Bundle
import android.view.View
import com.jgarin.base.ui.BaseScreenFragment
import com.jgarin.base.ui.LayoutResId
import com.jgarin.extensions.addAfterTextChangedListener
import com.jgarin.extensions.observeNonNull
import com.jgarin.login.LoginModule
import com.jgarin.login.R
import kotlinx.android.synthetic.main.fragment_password_input.*

internal class PasswordInputFragment : BaseScreenFragment() {

	private val viewModel by lazy { LoginModule.instance.getViewModel(requireActivity()) }

	override val layout: LayoutResId = R.layout.fragment_password_input

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.passwordInputScreen.screenState.observeNonNull(this, ::renderState)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

		etPass.addAfterTextChangedListener { viewModel.passwordEntered(it) }

		btnLogin.setOnClickListener { viewModel.onPasswordScreenLoginButtonClicked() }

	}

	private fun renderState(state: PasswordInputScreenState) {
		etPass.error = state.passwordError
		btnLogin.isEnabled = state.loginBtnEnabled
	}

}