package com.appinventiv.kotlinbasics

fun main(){
    print("Higher Order Function")
    addTwoNumber(3,5, action = { a:Int, b:Int ->
        sum(a,b)
    })
    addTwoNumber(3,5, ::sum)

    addTwoNumber(3,5, action = resultCallback)
}

val resultCallback = {a:Int, b:Int ->
        println(sum(a,b))
}

fun sum(a: Int, b: Int): Int{
    return a+b
}

fun addTwoNumber(a: Int, b: Int, action: (Int, Int) -> Unit){
    action(a, b)
}

