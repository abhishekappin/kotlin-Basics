package com.appinventiv.kotlinbasics

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.appinventiv.kotlinbasics.flows.consumer
import com.appinventiv.kotlinbasics.flows.flowProducer
import com.appinventiv.kotlinbasics.flows.flowProducerException
import com.appinventiv.kotlinbasics.flows.mutableSharedProducer
import com.appinventiv.kotlinbasics.flows.mutableStateFlowProducer
import com.appinventiv.kotlinbasics.flows.producer
import com.appinventiv.kotlinbasics.hilts.UserRepository
import com.appinventiv.kotlinbasics.ui.theme.KotlinBasicsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var repository: UserRepository
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinBasicsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Greeting("Android")

//                        Channel Producer Consumer
                        producers()
                        consumers()
//                        Flow Producer
                        flowProducerConsumer()
                        flowProducerConsumer1()
//                        Flow Operators
                        flowOperators()
                        bufferOperator()
//                        Flowon Operators
                        flowOnOperator()
//                        Mutable Shared Flow
                        mutableSharedFlowCollector()
//                        State Flow Collector
                        stateFlowCollector()
//                        Exception Handling
                        handleException()

//                        Hilt Dependency Injection
//                        constructor Injection and Field Injection
                        constructorInjection()
                    }

                }
            }
        }
    }

    private fun constructorInjection() {
//        repository.saveUser("Shri Ram", "Chandra ji Maharaj")

    }

    // State flow is like a hot flow and multiple consumers shared data with it,
// it is hot flow as well, but it also maintains the state of the flow
// it just maintains the last value of its producer
// All the transformation in Livedata done on Main Thread (thus it impact the performance)
// Live Data is lifecycle dependent but state flow is not, so Live need lifecycle like Activity and
// Fragment, so if we have to work with Repository we have to work with Flowswe can work in State flow
    private fun stateFlowCollector() {
        GlobalScope.launch {
            val result = mutableStateFlowProducer()
            delay(6000)
            result.collect {
                Log.d("State Flow 1", it.toString())
            }
        }
    }


//    mutable shared flows are hot flows and there are
//    multiple consumers can receive same data once flow starts emitting data
    private fun mutableSharedFlowCollector() {
        GlobalScope.launch {
            val result = mutableSharedProducer()
            result.collect {
                Log.d("Mutable Shared Flow 1", it.toString())
            }
        }

        GlobalScope.launch {
            val result = mutableSharedProducer()
            delay(2500)
            result.collect {
                Log.d("Mutable Shared Flow 2", it.toString())
            }
        }
    }

    private fun handleException() {
        GlobalScope.launch {
            try {
                flowProducerException()
                    .collect {
                        Log.d(
                            "Exception Handling",
                            "Current Thread: ${Thread.currentThread().name}"
                        )
                    }
            } catch (ex: Exception) {
                Log.d("Exception Handling", "Exception: ${ex.message}")
            }
        }
    }

    // Flow On Operator is used whenever we need to change the context of the coroutine
// code above flowOn will run on the thread defined by flowOn Operator
// flowOn works UpStream
    private fun flowOnOperator() {
        GlobalScope.launch(Dispatchers.Main) {
            flowProducer()
                .map {
                    delay(1000)
                    it * 2
                    Log.d("Map Operator", "$it ${Thread.currentThread().name}")
                }
                .filter {
                    delay(1000)
                    Log.d("Filter Operator", "$it ${Thread.currentThread().name}")
                    it < 15
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.d("Flow On Operator", "$it ${Thread.currentThread().name}")
                }
        }
    }

    private fun flowProducerConsumer() {
//        cancel Coroutine after certain time
        val job = GlobalScope.launch(Dispatchers.Main) {
            val result = flowProducer()
            result.collect {
                Log.d("Flow Producer", it.toString())
            }
        }
// Once we cancel the job object flow does not receive any further data from the producer
        GlobalScope.launch {
            delay(5000)
            job.cancel()
        }
    }


    //    Multiple Consumers can received the data from the same producer
//    Even though they can receive the data after some delay
//    so there will not be any loss of data.
    @OptIn(DelicateCoroutinesApi::class)
    private fun flowProducerConsumer1() {
        GlobalScope.launch(Dispatchers.Main) {
            val data = flowProducer()
            data.collect {
                delay(2500)
                Log.d("Flow Producer", "Producer 1:  ${it}")
            }
        }

    }

    //    first() -> return the first element from the list
//    toList() -> convert and return the list of elements getting from the producer
    private fun flowOperators() {
        GlobalScope.launch(Dispatchers.Main) {
            flowProducer()
                .onStart {
                    Log.d("Flow Operators", "Started")
                }   // at the beginning of receiving items if we want to do some operation
                .onCompletion {
                    Log.d("Flow Operators", "Completed")
                }// after the end of receiving items if we want to do some operation
                .onEach {
                    Log.d("Flow Operators about to emit", it.toString())
                } // at the start of receiving the each item if we want to do some operation
                .map {
                    it * 2
                }
                .filter { it > 10 }
                .collect {
                    Log.d("Flow Operators", it.toString())
                }
        }
    }

    private fun bufferOperator() {
        val time = measureTimeMillis {
            GlobalScope.launch {
                flowProducer()
                    .buffer()
                    .collect {
                        delay(2500)
                        Log.d("Flow Buffer", it.toString())
                    }
            }
        }
    }

    fun producers() {
        CoroutineScope(Dispatchers.Main).launch {
            producer()
        }
    }

    fun consumers() {
        CoroutineScope(Dispatchers.Main).launch {
            consumer()
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        KotlinBasicsTheme {
            Greeting("Android")
        }
    }
}