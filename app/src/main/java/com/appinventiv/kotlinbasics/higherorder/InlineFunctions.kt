package com.appinventiv.kotlinbasics.higherorder

fun main(){
    printNewData ({ println("Calling Main Method") })
}

inline fun printNewData(callback:()->Unit){
    callback()
    println("Calling PrintNewData Method")
}