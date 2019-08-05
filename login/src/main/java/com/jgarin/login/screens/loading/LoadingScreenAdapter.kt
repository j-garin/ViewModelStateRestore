package com.jgarin.login.screens.loading

import androidx.lifecycle.LiveData
import com.jgarin.base.ui.screen.BaseScreenAdapter
import com.jgarin.login.entities.State

internal class LoadingScreenAdapter(stateStream: LiveData<State>): BaseScreenAdapter<State, LoadingScreenState>(stateStream) {

	override fun map(workflowState: State): LoadingScreenState {
		return LoadingScreenState(workflowState.loginError)
	}

}