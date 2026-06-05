package com.ycany.prefinals.screens.register

interface RegisterContract {

    interface View {
        fun showMessage(message: String)
        fun navigateToLogin()
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onSubmitClicked(username: String, password: String, confirmPassword: String)
    }
}

