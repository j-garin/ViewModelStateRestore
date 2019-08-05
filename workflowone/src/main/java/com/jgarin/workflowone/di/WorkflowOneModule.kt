package com.jgarin.workflowone.di

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jgarin.extensions.viewModelFactory
import com.jgarin.workflowone.WorkflowActivity
import com.jgarin.workflowone.WorkflowViewModel

class WorkflowOneModule private constructor() {

	fun startWorkflowOne(context: Context) {
		val intent = Intent(context, WorkflowActivity::class.java)
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
		context.startActivity(intent)
	}

	internal fun createViewModel(
		activity: WorkflowActivity,
		savedState: Bundle?
	): WorkflowViewModel {
		return ViewModelProviders.of(activity,
			viewModelFactory { WorkflowViewModel(savedState) })
			.get(WorkflowViewModel::class.java)
	}

	internal fun getViewModel(activity: FragmentActivity): WorkflowViewModel {
		return ViewModelProviders.of(activity).get(WorkflowViewModel::class.java)
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