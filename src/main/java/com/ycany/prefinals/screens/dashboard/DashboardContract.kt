package com.ycany.prefinals.screens.dashboard

import com.ycany.prefinals.data.models.Recipe


interface DashboardContract {

    interface View {
        fun renderUsername(username: String)
        fun renderSuggestedRecipes(recipes: List<Recipe>)
        fun showLoading(isLoading: Boolean)
        fun showMessage(message: String)
        fun navigateToLogin()
        fun navigateToProfile(username: String)
        fun navigateToAddIngredients()
        fun navigateToViewRecipe(recipeId: Int)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onViewReady(username: String?)
        fun onResume()
        fun onAddIngredientsClicked()
        fun onProfileClicked()
        fun onViewRecipeClicked(recipeId: Int)
        fun onSignOutClicked()
        fun onAddToFavoritesClicked(recipe: Recipe)
    }
}