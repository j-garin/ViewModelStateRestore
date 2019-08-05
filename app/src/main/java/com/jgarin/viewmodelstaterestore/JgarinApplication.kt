package com.jgarin.viewmodelstaterestore

import android.app.Application
import android.content.Context
import com.jgarin.base.App
import com.jgarin.login.di.LoginModule
import com.jgarin.repository.RepositoryModule
import com.jgarin.validators.ValidatorsModule
import com.jgarin.workflowone.di.WorkflowOneModule
import com.jgarin.workflowtwo.di.WorkflowTwoModule

class JgarinApplication : Application(), App {

	private val validatorsModule by lazy { ValidatorsModule.init() }
	private val repositoryModule by lazy { RepositoryModule.init() }
	private val loginWorkflowModule by lazy {
		LoginModule.init(
			emailValidator = validatorsModule.emailValidator,
			passwordValidator = validatorsModule.passwordValidator,
			loginRepository = repositoryModule.loginRepo
		)
	}

	private val workflowOneModule by lazy { WorkflowOneModule.init(this) }
	private val workflowTwoModule by lazy { WorkflowTwoModule.init() }

	override fun startLoginWorkflow(context: Context) {
		loginWorkflowModule.startWorkflow(context)
	}

	override fun startWorkflowOne(context: Context) {
		workflowOneModule.startWorkflowOne(context)
	}

	override fun startWorkflowTwo(context: Context) {
		workflowTwoModule.startWorkflowTwo(context)
	}


}