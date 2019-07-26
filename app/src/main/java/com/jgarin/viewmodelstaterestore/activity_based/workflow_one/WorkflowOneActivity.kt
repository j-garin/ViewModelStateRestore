package com.jgarin.viewmodelstaterestore.activity_based.workflow_one

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.jgarin.viewmodelstaterestore.R
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseViewModel
import com.jgarin.viewmodelstaterestore.activity_based.base.BaseWorkflowActivity
import com.jgarin.viewmodelstaterestore.activity_based.base.LayoutResId
import com.jgarin.viewmodelstaterestore.activity_based.workflow_one.screen_01_01.FragmentOneOne
import com.jgarin.viewmodelstaterestore.activity_based.workflow_one.screen_01_02.FragmentOneTwo
import com.jgarin.viewmodelstaterestore.activity_based.workflow_one.screen_01_03.FragmentOneThree
import com.jgarin.viewmodelstaterestore.extensions.viewModelFactory

class WorkflowOneActivity : BaseWorkflowActivity<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen, WorkflowOneWorkflowNavigation>() {

	override val layout: LayoutResId = R.layout.fragment_container

	override fun getViewModel(savedState: Bundle?): BaseViewModel<WorkflowOneEvent, WorkflowOneState, WorkflowOneScreen, WorkflowOneWorkflowNavigation> {
		return ViewModelProviders.of(this, viewModelFactory { WorkflowOneViewModel(savedState) })
				.get(WorkflowOneViewModel::class.java)
	}

	override fun handleScreenChange(screen: WorkflowOneScreen) {
		val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
		when (screen) {
			WorkflowOneScreen.ScreenOne   -> if (currentFragment !is FragmentOneOne) replaceFragment(FragmentOneOne())
			WorkflowOneScreen.ScreenTwo   -> if (currentFragment !is FragmentOneTwo) replaceFragment(FragmentOneTwo())
			WorkflowOneScreen.ScreenThree -> if (currentFragment !is FragmentOneThree) replaceFragment(FragmentOneThree())
		}
	}

	override fun handleWorkFlowChange(navigationEvent: WorkflowOneWorkflowNavigation) {
		// the cool thing about this is that this activity doesn't even know where it's going. for any scenario
		when (navigationEvent) {
			WorkflowOneWorkflowNavigation.Back           -> finish()
			is WorkflowOneWorkflowNavigation.WorkflowTwo -> startActivity(navigationEvent.getLaunchIntent(this))
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// added this for testing purposes
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		return when (item?.itemId) {
			android.R.id.home -> onBackPressed().let { true } //just redirect the back press to the activity as you normally would
			else              -> super.onOptionsItemSelected(item)
		}
	}

	private fun replaceFragment(newFragment: Fragment) {
		supportFragmentManager
				.beginTransaction()
				.replace(R.id.fragmentContainer, newFragment) // no backstack required. we handle everything on our own
				.commit()
	}

}