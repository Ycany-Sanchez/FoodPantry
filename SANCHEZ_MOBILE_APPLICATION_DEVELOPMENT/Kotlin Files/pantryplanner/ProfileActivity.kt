package com.ycany.pantryplanner

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val textviewBack = findViewById<TextView>(R.id.textviewBack)

        textviewBack.setOnClickListener {
            finish()
        }
    }
}