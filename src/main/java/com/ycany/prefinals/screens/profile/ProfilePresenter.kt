package com.ycany.prefinals.screens.profile

import com.ycany.prefinals.data.repositories.FavoritesRepository
import com.ycany.prefinals.data.repositories.UserAccountRepository

class ProfilePresenter(
    private val accountRepository: UserAccountRepository,
    private val favoritesRepository: FavoritesRepository
) : ProfileContract.Presenter {

    private var view: ProfileContract.View? = null
    private val model = ProfileModel(accountRepository, favoritesRepository)

    override fun attachView(view: ProfileContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onViewReady() {
        val user = model.getCurrentUser()
        if (user != null) {
            view?.renderProfile(user)
        }

        val favorites = model.getFavoriteRecipes()
        view?.renderFavorites(favorites)
    }

    override fun onBackClicked() {
        view?.navigateToDashboard()
    }

    override fun onRecipeClicked(recipeId: Int) {
        view?.navigateToRecipeDetails(recipeId)
    }
}