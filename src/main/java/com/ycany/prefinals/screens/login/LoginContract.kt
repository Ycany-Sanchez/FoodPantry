package com.ycany.prefinals.screens.login

import android.widget.EditText

interface LoginContract {

    interface View {
        fun showMessage(message: String)
        fun prefillCredentials(username: String, password: String)
        fun navigateToDashboard(username: String)
        fun navigateToRegister()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onViewReady()
        fun onLoginClicked(username: String, password: String)
        fun onRegisterClicked()
    }
}
