package com.jgarin.repository

import com.jgarin.base.repository.LoginRepository
import com.jgarin.base.wrappers.Response
import kotlinx.coroutines.delay
import java.lang.Exception
import kotlin.random.Random

internal class LoginRepositoryImpl : LoginRepository {

	override suspend fun login(email: String, passowrd: String): Response<Unit> {
		delay(2000L) // emulate network call
		val random = Random(System.currentTimeMillis()).nextInt(0, 100)
		return when (random) {
			in 0 until 50 -> Response.Success(Unit) // 20% chance of successful login :)
			else -> Response.Error(Exception("Something went wrong"))
		}

	}

	override suspend fun logout(): Response<Unit> {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}

}