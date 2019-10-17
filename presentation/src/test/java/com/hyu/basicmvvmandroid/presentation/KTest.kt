package com.hyu.basicmvvmandroid.presentation


import org.junit.Assert.*

import org.junit.Test


class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
    }

    @Test
    fun test(){
        val input = 1041
        val sizeOfInt = 4 * 8
        var zeroCount = 0
        var saveCount = 0

        for(index in 0 until sizeOfInt){
            if((input.shr(index) and 1) == 1){
                if(saveCount < zeroCount){
                    saveCount = zeroCount
                }
                zeroCount = 0
            }
            else{
                if(zeroCount >= 0) zeroCount++
            }
        }

        print(saveCount)
    }

    @Test
    fun test1(){
        Thread{
            var a = 0
            a += 1
            println("hello")
            println("hello $a")
        }.start()


            runa (Runnable {
                var a = 0
                a += 1
                println("hello $a")
            })
    }

    fun runa(run : Runnable){
        run.run()
    }


}