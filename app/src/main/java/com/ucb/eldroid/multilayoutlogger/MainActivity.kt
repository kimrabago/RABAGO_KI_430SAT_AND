package com.ucb.eldroid.multilayoutlogger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v(TAG, "App started")
        Log.d(TAG, "Debugging MainActivity")
        Log.i(TAG, "MainActivity Loaded")
        Log.w(TAG, "Potential issue detected")
        Log.e(TAG, "Error message")
    }

    @Suppress("UNUSED_PARAMETER")
    fun openLinearActivity(view: View) {
        Log.d(TAG, "Switching to Second Activity")
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    @Suppress("UNUSED_PARAMETER")
    fun openRelativeActivity(view: View) {
        Log.d(TAG, "Switching to Third Activity")
        val intent = Intent(this, ThirdActivity::class.java)
        startActivity(intent)
    }
}