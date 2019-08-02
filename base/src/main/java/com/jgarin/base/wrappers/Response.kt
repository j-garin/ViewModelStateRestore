package com.jgarin.base.wrappers

sealed class Response<T> {
	class Success<T>(val data: T) : Response<T>()
	class Error<T>(val cause: Exception) : Response<T>()
}