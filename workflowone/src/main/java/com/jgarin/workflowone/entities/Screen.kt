package com.jgarin.workflowone.entities

import com.jgarin.base.ui.entities.BaseNavigationScreen
import kotlinx.android.parcel.Parcelize

@Parcelize
internal enum class Screen : BaseNavigationScreen {
	Overview, Input, Summary
}