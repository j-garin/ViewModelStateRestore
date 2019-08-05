package com.jgarin.login.di

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jgarin.base.repository.LoginRepository
import com.jgarin.base.validators.Validator
import com.jgarin.extensions.viewModelFactory
import com.jgarin.login.WorkflowActivity
import com.jgarin.login.WorkflowViewModel

class LoginModule private constructor(
	private val emailValidator: Validator<String>,
	private val passwordValidator: Validator<String>,
	private val loginRepository: LoginRepository
) {

	fun startWorkflow(context: Context) {
		context.startActivity(Intent(context, WorkflowActivity::class.java))
	}

	internal fun initViewModel(activity: FragmentActivity, savedState: Bundle?): WorkflowViewModel {
		return ViewModelProviders.of(activity, viewModelFactory {
			WorkflowViewModel(
				savedState = savedState,
				emailValidator = emailValidator,
				passwordValidator = passwordValidator,
				loginRepository = loginRepository
			)
		})
			.get(WorkflowViewModel::class.java)
	}

	internal fun getViewModel(activity: FragmentActivity): WorkflowViewModel {
		return ViewModelProviders.of(activity).get(WorkflowViewModel::class.java)
	}

	companion object {
		lateinit var instance: LoginModule
			private set

		fun init(
			emailValidator: Validator<String>,
			passwordValidator: Validator<String>,
			loginRepository: LoginRepository
		): LoginModule {
			return synchronized(this) {
				require(!Companion::instance.isInitialized)
				instance =
					LoginModule(
						emailValidator,
						passwordValidator,
						loginRepository
					)
				instance
			}
		}
	}
}