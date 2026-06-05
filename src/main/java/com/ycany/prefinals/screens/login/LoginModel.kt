package com.ycany.prefinals.screens.login
import com.ycany.prefinals.data.models.UserAccount
import com.ycany.prefinals.data.repositories.UserAccountRepository

class LoginModel(
    private val userAccountRepository: UserAccountRepository
) {

    fun getSavedCredentials(): UserAccount? {
        return userAccountRepository.getCurrentRegistration()
    }

    fun validateInputs(username: String, password: String): String? {
        if (username.isBlank() && password.isBlank()) return "Please enter your username and password."
        if (username.isBlank()) return "Please enter your username."
        if (password.isBlank()) return "Please enter your password."
        return null
    }

    fun authenticate(username: String, password: String): Boolean {
        val registeredAccount = userAccountRepository.getCurrentRegistration()
        return registeredAccount != null &&
                registeredAccount.username == username &&
                registeredAccount.password == password
    }

    fun clearSession() {
        userAccountRepository.clearCurrentRegistration()
    }
}