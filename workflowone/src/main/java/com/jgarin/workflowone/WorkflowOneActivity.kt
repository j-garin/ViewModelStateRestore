package com.jgarin.workflowone

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jgarin.base.BaseWorkflowActivity
import com.jgarin.base.LayoutResId
import com.jgarin.workflowone.screen_01_01.FragmentOneOne
import com.jgarin.workflowone.screen_01_02.FragmentOneTwo
import com.jgarin.workflowone.screen_01_03.FragmentOneThree

internal class WorkflowOneActivity :
	BaseWorkflowActivity<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen, WorkflowOneWorkflowNavigation>() {

	override val layout: LayoutResId = R.layout.fragment_container

	// This MUST be called first to create the viewModel with all the data required. Later on should be moved to DI.
	override fun getViewModel(savedState: Bundle?): com.jgarin.base.BaseViewModel<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen, WorkflowOneWorkflowNavigation> {
		return ViewModelProviders.of(this,
			com.jgarin.extensions.viewModelFactory { WorkflowOneViewModel(savedState) })
			.get(WorkflowOneViewModel::class.java)
	}

	override fun handleScreenChange(screen: WorkflowOneScreen) {
		// Need to find a better way to make sure we don't attach the same fragment again. Open to suggestions.
		val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
		when (screen) {
			WorkflowOneScreen.ScreenOne   -> if (currentFragment !is FragmentOneOne)
				replaceFragment(FragmentOneOne())
			WorkflowOneScreen.ScreenTwo   -> if (currentFragment !is FragmentOneTwo)
				replaceFragment(FragmentOneTwo())
			WorkflowOneScreen.ScreenThree -> if (currentFragment !is FragmentOneThree)
				replaceFragment(FragmentOneThree())
		}
	}

	override fun handleWorkFlowChange(navigationEvent: WorkflowOneWorkflowNavigation) {
		// the cool thing about this is that this activity doesn't even know where it's going. for any scenario
		when (navigationEvent) {
			WorkflowOneWorkflowNavigation.Back           -> finish()
			is WorkflowOneWorkflowNavigation.WorkflowTwo -> navigationEvent.start(this)
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// added this for testing purposes
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		return when (item?.itemId) {
			android.R.id.home -> onBackPressed().let { true } // just redirect the back press to the activity as you normally would and our state machine will do the rest
			else              -> super.onOptionsItemSelected(item)
		}
	}

	private fun replaceFragment(newFragment: Fragment) {
		supportFragmentManager
			.beginTransaction()
			.replace(
				R.id.fragmentContainer,
				newFragment
			) // no backstack required. we handle everything on our own
			.commit()
	}

}