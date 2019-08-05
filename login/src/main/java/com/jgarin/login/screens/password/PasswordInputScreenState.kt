package com.jgarin.login.screens.password

import com.jgarin.base.ui.entities.BaseScreenState

internal data class PasswordInputScreenState(
	val password: String,
	val passwordError: String?,
	val loginBtnEnabled: Boolean
): BaseScreenState