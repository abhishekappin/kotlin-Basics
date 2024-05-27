package com.appinventiv.kotlinbasics.hilts

import android.util.Log
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

interface UserRepository{
    fun saveUser(user: String, password: String)
}

class SQLRepository @Inject constructor(val loggerService: LoggerService) : UserRepository {
    override
    fun saveUser(user: String, password: String){
        Log.d("UserRepository", "User Saved in DB")
    }

    fun readUser(message: String){
        loggerService.log("User Read from DB")
    }
}

class FirebaseRepository : UserRepository {
    override fun saveUser(user: String, password: String) {
        Log.d("Firebase Repository", "user saved in Firebase")
    }
}