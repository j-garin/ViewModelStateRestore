package com.jgarin.extensions

import androidx.lifecycle.*

inline fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, crossinline callback: (T) -> Unit) {
	this.observe(owner, Observer { item -> if (item != null) callback(item) })
}

fun <IN, OUT> LiveData<IN>.map(mapper: (IN) -> OUT): LiveData<OUT> {
	return Transformations.map(this, mapper)
}

fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
	var isFirstValue = true
	var lastValue: T? = null
	val result = MediatorLiveData<T>()
	result.addSource(this) {
		if (isFirstValue || lastValue != it) {
			isFirstValue = false
			lastValue = it
			result.postValue(it)
		}
	}
	return result
}