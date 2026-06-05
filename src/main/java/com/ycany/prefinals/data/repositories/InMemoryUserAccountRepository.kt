package com.ycany.prefinals.data.repositories
import com.ycany.prefinals.data.models.UserAccount

object InMemoryUserAccountRepository : UserAccountRepository {
    private var currentRegistration: UserAccount? = null
    override fun saveRegistration(account: UserAccount) {
        currentRegistration = account
    }
    override fun getCurrentRegistration(): UserAccount? = currentRegistration
    override fun clearCurrentRegistration() {
        currentRegistration = null
    }
}