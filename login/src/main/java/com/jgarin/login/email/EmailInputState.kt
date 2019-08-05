package com.jgarin.login.email

import com.jgarin.base.ui.entities.BaseScreenState

internal data class EmailInputState(
	val email: String,
	val emailError: String?,
	val nextBtnEnabled: Boolean
) : BaseScreenState