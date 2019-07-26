package com.jgarin.viewmodelstaterestore

import android.app.Application
import android.content.Context
import com.jgarin.workflowone.di.WorkflowOneModule
import com.jgarin.workflowtwo.di.WorkflowTwoModule

class JgarinApplication : Application(), com.jgarin.base.App {

	private lateinit var workflowOneModule: WorkflowOneModule
	private lateinit var workflowTwoModule: WorkflowTwoModule

	override fun onCreate() {
		super.onCreate()

		workflowOneModule = WorkflowOneModule.init(this)
		workflowTwoModule = WorkflowTwoModule.instance
	}

	override fun startWorkflowOne(context: Context) {
		workflowOneModule.startWorkflowOne(context)
	}

	override fun startWorkflowTwo(context: Context) {
		workflowTwoModule.startWorkflowTwo(context)
	}

}