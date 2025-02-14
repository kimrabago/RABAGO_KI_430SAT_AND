package com.ucb.eldroid.multilayoutlogger

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var contactNum: EditText

    companion object {
        private const val TAG = "SecondActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        fullName = findViewById(R.id.fullName)
        email = findViewById(R.id.email)
        contactNum = findViewById(R.id.contactNum)

        Log.i(TAG, "Second Activity Loaded");
    }

    @Suppress("UNUSED_PARAMETER")
    fun submitForm(view: View) {
        val name = fullName.text.toString()
        val email = email.text.toString()
        val num = contactNum.text.toString()

        if (name.isEmpty() || email.isEmpty() || num.isEmpty()) {
            Log.w(TAG, "Form submission failed: Fields are empty")
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        } else {
            Log.d(TAG, "Form Submitted: Name = $name, Email = $email, Contact Number = $num")
            Toast.makeText(this, "Form Submitted Successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}