package com.ycany.prefinals.screens.register

import com.ycany.prefinals.data.repositories.UserAccountRepository

class RegisterPresenter(
    private val userAccountRepository: UserAccountRepository
) : RegisterContract.Presenter {

    private var view: RegisterContract.View? = null
    private val model = RegisterModel(userAccountRepository)

    override fun attachView(view: RegisterContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onSubmitClicked(username: String, password: String, confirmPassword: String) {
        val error = model.validateInputs(username, password, confirmPassword)
        if (error != null) {
            view?.showMessage(error)
            return
        }

        model.createAccount(username, password)

        view?.showMessage("Account created! Please log in.")
        view?.navigateToLogin()
    }
}