package com.jgarin.login.entities

import com.jgarin.base.ui.entities.BaseNavigationScreen
import kotlinx.android.parcel.Parcelize

@Parcelize
internal enum class Screen : BaseNavigationScreen {
	EmailInput, PassInput, Progress
}