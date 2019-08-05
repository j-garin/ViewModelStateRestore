package com.jgarin.login.loading

import androidx.lifecycle.LiveData
import com.jgarin.base.ui.BaseScreenAdapter
import com.jgarin.login.State

internal class LoadingScreenAdapter(stateStream: LiveData<State>): BaseScreenAdapter<State, LoadingScreenState>(stateStream) {

	override fun map(workflowState: State): LoadingScreenState {
		return LoadingScreenState(workflowState.loginError)
	}

}