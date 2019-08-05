package com.jgarin.login.email

import com.jgarin.base.ui.BaseScreenState

internal data class EmailInputState(
	val email: String,
	val emailError: String?,
	val nextBtnEnabled: Boolean
) : BaseScreenState