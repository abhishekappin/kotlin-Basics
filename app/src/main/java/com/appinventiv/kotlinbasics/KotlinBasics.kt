package com.appinventiv.kotlinbasics

import android.app.Application
import com.appinventiv.kotlinbasics.hilts.UserRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class KotlinBasics : Application() {

    /*@Inject
    lateinit var repository: UserRepository*/

    override fun onCreate() {
        super.onCreate()


    }
}