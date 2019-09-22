package com.hyu.basic.mvvm.android.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyu.basicmvvmandroid.presentation.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState ?: bindLayout()
    }

    private fun bindLayout(){
        //Layout dataBinding
    }
}
