package com.jgarin.workflowone

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.jgarin.base.ui.BaseViewModel
import com.jgarin.base.ui.BaseWorkflowActivity
import com.jgarin.base.ui.LayoutResId
import com.jgarin.workflowone.di.WorkflowOneModule
import com.jgarin.workflowone.overview.FragmentOverview
import com.jgarin.workflowone.input.FragmentInput
import com.jgarin.workflowone.summary.FragmentSummary

internal class WorkflowActivity :
	BaseWorkflowActivity<Event, State, Screen, WorkflowNavigation>() {

	override val layout: LayoutResId = R.layout.fragment_container

	// This MUST be called first to create the viewModel with all the data required. Later on should be moved to DI.
	override fun getViewModel(savedState: Bundle?): BaseViewModel<Event, State, Screen, WorkflowNavigation> {
		return WorkflowOneModule.instance.createViewModel(this, savedState)
	}

	override fun handleScreenChange(screen: Screen) {
		// Need to find a better way to make sure we don't attach the same fragment again. Open to suggestions.
		val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
		when (screen) {
			Screen.Overview -> if (currentFragment !is FragmentOverview)
				replaceFragment(FragmentOverview())
			Screen.Input    -> if (currentFragment !is FragmentInput)
				replaceFragment(FragmentInput())
			Screen.Summary  -> if (currentFragment !is FragmentSummary)
				replaceFragment(FragmentSummary())
		}
	}

	override fun handleWorkFlowChange(navigationEvent: WorkflowNavigation) {
		// the cool thing about this is that this activity doesn't even know where it's going. for any scenario
		when (navigationEvent) {
			WorkflowNavigation.Back           -> finish()
			is WorkflowNavigation.WorkflowTwo -> navigationEvent.start(this)
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