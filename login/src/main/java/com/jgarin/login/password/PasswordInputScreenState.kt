package com.jgarin.login.password

import com.jgarin.base.ui.BaseScreenState

internal data class PasswordInputScreenState(
	val passwordError: String?,
	val loginBtnEnabled: Boolean
): BaseScreenState