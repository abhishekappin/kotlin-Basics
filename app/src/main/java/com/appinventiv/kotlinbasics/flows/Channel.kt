package com.appinventiv.kotlinbasics.flows

import android.util.Log
import kotlinx.coroutines.channels.Channel

val TAG = "Producer Consumer"
val channel = Channel<Int>()

suspend fun producer(){
    channel.send(1)
    channel.send(2)
}

suspend fun consumer(){
    Log.d("TAG", "consumer: ${channel.receive()}")
    Log.d("TAG", "consumer: ${channel.receive()}")
}