package com.appinventiv.kotlinbasics

fun main(){
    println(3.addTwoNumber(5))
    println(3 addTwoNumbers 5)
}

infix fun Int.addTwoNumbers(number:Int) = this + number

fun Int.addTwoNumber(value:Int) = this + value