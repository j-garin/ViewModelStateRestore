package com.jgarin.base.wrappers

data class SingleLiveEvent<T>(private val wrapped: T) {

	var isHandled = false
		private set

	fun peek() = wrapped

	val value: T?
		get() = if (isHandled) {
			null
		} else {
			isHandled = true
			wrapped
		}

}