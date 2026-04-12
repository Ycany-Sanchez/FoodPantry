package com.ycany.pantryplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val textviewRegister = findViewById<TextView>(R.id.textviewRegister)

        buttonLogin.setOnClickListener {
            val intentDashboard = Intent(this, DashboardActivity::class.java)
            startActivity(intentDashboard)
        }

        textviewRegister.setOnClickListener {
            val intentRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentRegister)
        }
    }
}