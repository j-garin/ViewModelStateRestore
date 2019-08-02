package com.jgarin.workflowtwo.di

import android.content.Context
import android.content.Intent
import com.jgarin.workflowtwo.WorkflowActivity

class WorkflowTwoModule private constructor(){

	fun startWorkflowTwo(context: Context) {
		context.startActivity(Intent(context, WorkflowActivity::class.java))
	}

	companion object {
		private val instance by lazy { WorkflowTwoModule()}

		fun init() = instance
	}

}