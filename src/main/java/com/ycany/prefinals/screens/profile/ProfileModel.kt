package com.ycany.prefinals.screens.profile

import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.models.UserAccount
import com.ycany.prefinals.data.repositories.FavoritesRepository
import com.ycany.prefinals.data.repositories.UserAccountRepository

class ProfileModel(
    private val accountRepository: UserAccountRepository,
    private val favoritesRepository: FavoritesRepository
) {
    fun getCurrentUser(): UserAccount? = accountRepository.getCurrentRegistration()

    fun getFavoriteRecipes(): List<Recipe> = favoritesRepository.getFavorites()
}