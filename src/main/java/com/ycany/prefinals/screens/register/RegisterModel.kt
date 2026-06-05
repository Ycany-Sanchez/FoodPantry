package com.ycany.prefinals.screens.register

import com.ycany.prefinals.data.models.UserAccount
import com.ycany.prefinals.data.repositories.UserAccountRepository

class RegisterModel(
    private val userAccountRepository: UserAccountRepository
) {

    fun validateInputs(username: String, password: String, confirmPassword: String): String? {
        if (username.isBlank() && password.isBlank() && confirmPassword.isBlank()) {
            return "Please fill in all fields."
        }
        if (username.isBlank()) return "Please enter a username."
        if (password.isBlank()) return "Please enter a password."
        if (confirmPassword.isBlank()) return "Please confirm your password."
        if (password != confirmPassword) return "Passwords do not match."
        return null
    }

    fun createAccount(username: String, password: String) {
        userAccountRepository.saveRegistration(
            UserAccount(username = username, password = password)
        )
    }
}
