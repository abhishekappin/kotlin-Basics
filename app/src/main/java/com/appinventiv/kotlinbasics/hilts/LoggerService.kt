package com.appinventiv.kotlinbasics.hilts

import android.util.Log
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped   // the instance of this class exists upto the activity level
class LoggerService @Inject constructor(){
    fun log(message: String){
        Log.d("LoggerService", message)
    }
}