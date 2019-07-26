package com.jgarin.workflowtwo.di

import android.content.Context
import android.content.Intent
import com.jgarin.workflowtwo.WorkflowTwoActivity

class WorkflowTwoModule private constructor(){

	fun startWorkflowTwo(context: Context) {
		context.startActivity(Intent(context, WorkflowTwoActivity::class.java))
	}

	companion object {
		val instance = WorkflowTwoModule()
	}

}