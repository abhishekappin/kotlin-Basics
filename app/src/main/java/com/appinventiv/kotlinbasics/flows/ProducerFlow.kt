package com.appinventiv.kotlinbasics.flows

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun flowProducer() = flow<Int> {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    list.forEach {
        delay(1000)
        emit(it)
    }
}

fun flowProducerException() = flow<Int> {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    list.forEach {
        delay(1000)
        emit(it)
    }
}.catch {
    Log.d("Exception thrown in Emitter", "${it.message}")
    emit(-1)
}


// We can pass replay parameter to MutableSharedFlow<Int>() constructor,
// it will store that much previous emitted numbers, and if someone joins little late,
// it will get that much previous emitted numbers.
suspend fun mutableSharedProducer() : Flow<Int> {
    val mutableSharedFlow = MutableSharedFlow<Int>()
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    list.forEach {
            delay(1000)
            mutableSharedFlow.emit(it)
    }
    return mutableSharedFlow
}

suspend fun mutableStateFlowProducer() : Flow<Int> {
     val mutableStateFlow = MutableSharedFlow<Int>(10)
        delay(1000)
        mutableStateFlow.emit(20)
        delay(2000)
    mutableStateFlow.emit(30)
    return mutableStateFlow
}