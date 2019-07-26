package com.jgarin.base.wrappers

class SingleLiveEvent<T>(value: T) {

	private val internalValue = value

	var isHandled = false
		private set

	fun peek() = internalValue

	val value: T?
		get() = if (isHandled) {
			null
		} else {
			isHandled = true
			internalValue
		}

}