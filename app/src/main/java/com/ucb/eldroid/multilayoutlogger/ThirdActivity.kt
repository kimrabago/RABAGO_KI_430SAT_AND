package com.ucb.eldroid.multilayoutlogger

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ThirdActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ThirdActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        Log.i(TAG, "Third Activity Loaded");

    }

    @Suppress("UNUSED_PARAMETER")
    fun editProfile(view: View) {
        Log.d(TAG, "Edit Profile button clicked")
        Toast.makeText(this, "Edit Profile UI Soon", Toast.LENGTH_SHORT).show()
    }
}