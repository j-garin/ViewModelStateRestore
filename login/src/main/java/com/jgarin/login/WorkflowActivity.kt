package com.jgarin.login

import android.os.Bundle
import com.jgarin.base.App
import com.jgarin.base.ui.BaseViewModel
import com.jgarin.base.ui.BaseWorkflowActivity
import com.jgarin.base.ui.LayoutResId
import com.jgarin.login.email.EmailInputFragment
import com.jgarin.login.loading.LoadingFragment
import com.jgarin.login.password.PasswordInputFragment

internal class WorkflowActivity : BaseWorkflowActivity<Event, State, Screen, WorkflowNavigation>() {

	private val app
		get() = application as App

	override val layout: LayoutResId = R.layout.fragment_container

	override fun getViewModel(savedState: Bundle?): BaseViewModel<Event, State, Screen, WorkflowNavigation> {
		return LoginModule.instance.initViewModel(this, savedState)
	}

	override fun handleScreenChange(screen: Screen) {
		val fragment = when (screen) {
			Screen.EmailInput -> EmailInputFragment()
			Screen.PassInput  -> PasswordInputFragment()
			Screen.Progress   -> LoadingFragment()
		}
		supportFragmentManager.beginTransaction()
			.replace(R.id.fragmentContainer, fragment)
			.commit()
	}

	override fun handleWorkFlowChange(navigationEvent: WorkflowNavigation) {
		when (navigationEvent) {
			WorkflowNavigation.EndWorkflow -> onBackPressed()
			WorkflowNavigation.WorkflowOne -> app.startWorkflowOne(this)
		}
	}
}