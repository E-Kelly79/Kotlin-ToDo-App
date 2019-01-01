package com.kotlin.chores.choresapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.chores.choresapp.R.layout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
    }
}
