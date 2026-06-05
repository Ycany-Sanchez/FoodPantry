package com.ycany.prefinals.data.repositories
import com.ycany.prefinals.data.models.UserAccount

interface UserAccountRepository {
    fun saveRegistration(account: UserAccount)
    fun getCurrentRegistration(): UserAccount?
    fun clearCurrentRegistration()
}

