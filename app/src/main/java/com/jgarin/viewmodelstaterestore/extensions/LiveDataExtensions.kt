package com.jgarin.viewmodelstaterestore.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, crossinline callback: (T) -> Unit) {
	this.observe(owner, Observer { item -> if (item != null) callback(item) })
}