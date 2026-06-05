package com.ycany.prefinals.screens.viewrecipe

import com.ycany.prefinals.data.models.Recipe

interface ViewRecipeContract {

    interface View {
        fun renderRecipe(recipe: Recipe)
        fun showLoading(isLoading: Boolean)
        fun showMessage(message: String)
        fun navigateToDashboard()
        fun updateFavoriteStatus(isFavorite: Boolean)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onViewReady(recipeId: Int)
        fun onBackClicked()
        fun onFavoriteClicked()
    }
}