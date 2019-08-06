package com.jgarin.login.screens.password

import android.os.Bundle
import android.view.View
import com.jgarin.base.ui.screen.BaseScreenFragment
import com.jgarin.base.ui.LayoutResId
import com.jgarin.extensions.addAfterTextChangedListener
import com.jgarin.extensions.observeNonNull
import com.jgarin.login.di.LoginModule
import com.jgarin.login.R
import kotlinx.android.synthetic.main.fragment_password_input.*

internal class PasswordInputFragment : BaseScreenFragment() {

	private val viewModel by lazy { LoginModule.instance.getViewModel(requireActivity()) }

	override val layout: LayoutResId = R.layout.fragment_password_input

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		viewModel.passwordInputScreen.screenState.observeNonNull(viewLifecycleOwner, ::renderState)

		etPass.setText(viewModel.passwordInputScreen.screenState.value?.password)

		etPass.addAfterTextChangedListener { viewModel.passwordEntered(it) }

		btnLogin.setOnClickListener { viewModel.onPasswordScreenLoginButtonClicked() }
	}

	private fun renderState(state: PasswordInputScreenState) {
		etPass.error = state.passwordError
		btnLogin.isEnabled = state.loginBtnEnabled
	}

}