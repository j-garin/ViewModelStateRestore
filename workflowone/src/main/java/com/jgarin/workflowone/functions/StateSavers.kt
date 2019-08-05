package com.jgarin.workflowone.functions

import android.os.Bundle
import com.jgarin.workflowone.entities.Screen
import com.jgarin.workflowone.entities.State

private const val SCREEN_KEY =
	"com.jgarin.viewmodelstaterestore.activity_based.workflow_one.Screen"
private const val INPUT_KEY =
	"com.jgarin.viewmodelstaterestore.activity_based.workflow_one.Input"
private const val TMP_INPUT_KEY =
	"com.jgarin.viewmodelstaterestore.activity_based.workflow_one.TmpInput"


internal fun saveWorkflowState(outState: Bundle, state: State, screen: Screen) {
	outState.putSerializable(SCREEN_KEY, screen)
	outState.putString(INPUT_KEY, state.input)
	outState.putString(TMP_INPUT_KEY, state.tmpInput)
}

internal fun readInitialState(savedState: Bundle?): State {
	return State(
		input = savedState?.getString(INPUT_KEY) ?: "",
		tmpInput = savedState?.getString(TMP_INPUT_KEY) ?: ""
	)
}

internal fun readInitialScreen(savedState: Bundle?): Screen {
	return savedState?.getSerializable(SCREEN_KEY)as? Screen ?: Screen.Overview
}