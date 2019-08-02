package com.jgarin.login.loading

import androidx.lifecycle.LiveData
import com.jgarin.extensions.map
import com.jgarin.extensions.notNull
import com.jgarin.login.State

internal class LoadingScreen(stateStream: LiveData<State>) {

	val loginError = stateStream.map { state -> state.loginError }
		.notNull()

}