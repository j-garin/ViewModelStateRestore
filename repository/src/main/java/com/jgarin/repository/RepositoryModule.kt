package com.jgarin.repository

import com.jgarin.base.repository.LoginRepository

class RepositoryModule private constructor() {

	val loginRepo: LoginRepository by lazy { LoginRepositoryImpl() }

	companion object {

		private lateinit var instance: RepositoryModule

		fun init(): RepositoryModule {
			return synchronized(this) {
				require(!::instance.isInitialized)
				instance = RepositoryModule()
				instance
			}
		}
	}

}