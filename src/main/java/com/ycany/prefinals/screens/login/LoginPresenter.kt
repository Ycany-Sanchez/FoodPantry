package com.ycany.prefinals.screens.login
import com.ycany.prefinals.data.repositories.UserAccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginPresenter(
    private val userAccountRepository: UserAccountRepository
) : LoginContract.Presenter {

    private var view: LoginContract.View? = null
    private val model = LoginModel(userAccountRepository)
    private val presenterScope = CoroutineScope(Dispatchers.Main + Job())

    override fun attachView(view: LoginContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onViewReady() {
        model.getSavedCredentials()?.let { account ->
            view?.prefillCredentials(account.username, account.password)
        }
    }

    override fun onLoginClicked(username: String, password: String) {
        val error = model.validateInputs(username, password)
        if (error != null) {
            view?.showMessage(error)
            return
        }

        if (!model.authenticate(username, password)) {
            view?.showMessage("Username or password is incorrect.")
            return
        }

        view?.showMessage("Welcome back, Chef $username!")
        presenterScope.launch {
            delay(1500)
            view?.navigateToDashboard(username)
        }
    }

    override fun onRegisterClicked() {
        view?.navigateToRegister()
    }
}
