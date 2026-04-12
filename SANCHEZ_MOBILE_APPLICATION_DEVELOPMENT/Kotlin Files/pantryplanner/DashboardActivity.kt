package com.ycany.pantryplanner

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val textviewProfile = findViewById<TextView>(R.id.textviewProfile)
        val textviewLogout = findViewById<TextView>(R.id.textviewLogout)

        textviewProfile.setOnClickListener {
            val intentProfile = Intent(this, ProfileActivity::class.java)
            startActivity(intentProfile)
        }

        textviewLogout.setOnClickListener {
            val intentLogout = Intent(this, LoginActivity::class.java)
            intentLogout.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentLogout)
        }
    }
}