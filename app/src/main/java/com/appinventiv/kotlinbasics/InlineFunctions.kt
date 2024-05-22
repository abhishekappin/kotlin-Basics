package com.appinventiv.kotlinbasics

fun main(){
    printNewData ({ println("Calling Main Method") })
}

inline fun printNewData(callback:()->Unit){
    callback()
    println("Calling PrintNewData Method")
}