package com.jgarin.base.repository

import com.jgarin.base.wrappers.Response

interface LoginRepository {

	suspend fun login(email: String, passowrd: String): Response<Unit>

	suspend fun logout(): Response<Unit>

}