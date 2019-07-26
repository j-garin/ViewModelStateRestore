package com.jgarin.workflowtwo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_workflow_two.*

internal class WorkflowActivity: AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_workflow_two)

		btnBack.setOnClickListener { onBackPressed() }

	}
}