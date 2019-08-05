package com.jgarin.login.loading

import android.os.Bundle
import android.widget.Toast
import com.jgarin.base.ui.screen.BaseScreenFragment
import com.jgarin.base.ui.LayoutResId
import com.jgarin.extensions.observeNonNull
import com.jgarin.login.LoginModule
import com.jgarin.login.R

internal class LoadingFragment : BaseScreenFragment() {

	private val viewModel by lazy { LoginModule.instance.getViewModel(requireActivity()) }

	override val layout: LayoutResId = R.layout.fragment_loading

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.loadingScreen.screenState.observeNonNull(this, ::renderState)
	}

	private fun renderState(state: LoadingScreenState) {
		state.loginError?.value?.let { errorMessage ->
			Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
		}
	}

}