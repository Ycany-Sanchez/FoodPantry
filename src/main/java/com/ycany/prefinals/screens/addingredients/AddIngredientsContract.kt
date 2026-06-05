package com.ycany.prefinals.screens.addingredients

import com.ycany.prefinals.data.models.Ingredient
import com.ycany.prefinals.data.models.Recipe


interface AddIngredientsContract {

    interface View {
        fun renderPantryIngredients(ingredients: List<Ingredient>)
        fun renderSearchResults(ingredients: List<Ingredient>)
        fun renderRecipeSearchResults(recipes: List<Recipe>)
        fun showSearchLoading(isLoading: Boolean)
        fun showRecipeSearchLoading(isLoading: Boolean)
        fun showMessage(message: String)
        fun navigateToDashboard()
        fun navigateToViewRecipe(recipeId: Int)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun onViewReady()
        fun onRemoveIngredientClicked(ingredient: Ingredient)
        fun onSearchIngredientClicked(query: String)
        fun onAddIngredientFromSearchClicked(ingredient: Ingredient)
        fun onSearchRecipeClicked(query: String)
        fun onRecipeClicked(recipeId: Int)
        fun onBackClicked()
    }
}