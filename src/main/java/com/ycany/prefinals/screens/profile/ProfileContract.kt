package com.ycany.prefinals.screens.profile

import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.models.UserAccount

interface ProfileContract {

    interface View {
        fun renderProfile(account: UserAccount)
        fun renderFavorites(favorites: List<Recipe>)
        fun navigateToDashboard()
        fun navigateToRecipeDetails(recipeId: Int)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onViewReady()
        fun onBackClicked()
        fun onRecipeClicked(recipeId: Int)
    }
}