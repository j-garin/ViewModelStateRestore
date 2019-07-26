package com.jgarin.workflowone.di

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jgarin.extensions.viewModelFactory
import com.jgarin.workflowone.WorkflowOneActivity
import com.jgarin.workflowone.WorkflowOneViewModel

class WorkflowOneModule private constructor() {

	fun startWorkflowOne(context: Context) {
		context.startActivity(Intent(context, WorkflowOneActivity::class.java))
	}

	internal fun createViewModel(
		activity: WorkflowOneActivity,
		savedState: Bundle?
	): WorkflowOneViewModel {
		return ViewModelProviders.of(activity,
			viewModelFactory { WorkflowOneViewModel(savedState) })
			.get(WorkflowOneViewModel::class.java)
	}

	internal fun getViewModel(activity: FragmentActivity): WorkflowOneViewModel {
		return ViewModelProviders.of(activity).get(WorkflowOneViewModel::class.java)
	}

	companion object {
		lateinit var instance: WorkflowOneModule // This will produce runtime exceptions when trying to access before initializing
			private set

		fun init(
			//this parameter is not really required here, just showing how injection can be done from the main app module
			context: Context
		): WorkflowOneModule {
			return synchronized(this) {
				require(!::instance.isInitialized) // make sure we initialize only once
				instance = WorkflowOneModule()
				instance
			}
		}
	}
}