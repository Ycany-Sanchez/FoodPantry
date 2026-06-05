package com.ycany.prefinals.screens.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ycany.prefinals.R
import com.ycany.prefinals.data.repositories.InMemoryUserAccountRepository
import com.ycany.prefinals.screens.dashboard.DashboardActivity
import com.ycany.prefinals.screens.register.RegisterActivity
import com.ycany.prefinals.utils.getEditTextValue
import com.ycany.prefinals.utils.toast

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private val presenter: LoginContract.Presenter = LoginPresenter(InMemoryUserAccountRepository)

    private lateinit var edittextUsername: EditText
    private lateinit var edittextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.attachView(this)

        edittextUsername = findViewById(R.id.edittextUsername)
        edittextPassword = findViewById(R.id.edittextPassword)

        findViewById<Button>(R.id.buttonLogin).apply {
            setOnClickListener {
                presenter.onLoginClicked(
                    getEditTextValue(edittextUsername),
                    getEditTextValue(edittextPassword)
                )
            }
        }

        findViewById<TextView>(R.id.textviewRegister).apply {
            setOnClickListener {
                presenter.onRegisterClicked()
            }
        }

        presenter.onViewReady()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun prefillCredentials(username: String, password: String) {
        edittextUsername.also {
            it.setText(username)
            edittextPassword.setText(password)
        }
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun navigateToDashboard(username: String) {
        Intent(this, DashboardActivity::class.java).apply {
            putExtra("USERNAME", username)
            startActivity(this)
        }
        finish()
    }

    override fun navigateToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}