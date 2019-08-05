package com.jgarin.login.loading

import com.jgarin.base.ui.entities.BaseScreenState
import com.jgarin.base.wrappers.SingleLiveEvent

internal data class LoadingScreenState(val loginError: SingleLiveEvent<String>?):
	BaseScreenState