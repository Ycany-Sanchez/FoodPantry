package com.ycany.prefinals.screens.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.ycany.prefinals.R
import com.ycany.prefinals.data.repositories.InMemoryUserAccountRepository
import com.ycany.prefinals.screens.login.LoginActivity
import com.ycany.prefinals.utils.getEditTextValue
import com.ycany.prefinals.utils.toast

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private val presenter: RegisterContract.Presenter = RegisterPresenter(InMemoryUserAccountRepository)

    private lateinit var edittextUsername: EditText
    private lateinit var edittextPassword: EditText
    private lateinit var edittextConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        presenter.attachView(this)

        edittextUsername = findViewById(R.id.edittextUsername)
        edittextPassword = findViewById(R.id.edittextPassword)
        edittextConfirmPassword = findViewById(R.id.edittextConfirmPassword)

        findViewById<Button>(R.id.buttonCreateAccount).apply {
            setOnClickListener {
                presenter.onSubmitClicked(
                    getEditTextValue(edittextUsername),
                    getEditTextValue(edittextPassword),
                    getEditTextValue(edittextConfirmPassword)
                )
            }
        }
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun navigateToLogin() {
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}