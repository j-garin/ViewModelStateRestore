package com.jgarin.viewmodelstaterestore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jgarin.base.App
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	private val app: App
		get() = application as App

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		btnActivityBased.setOnClickListener {
			app.startWorkflowOne(this)
		}
	}
}
