package com.ycany.prefinals.screens.dashboard

import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.repositories.FavoritesRepository
import com.ycany.prefinals.data.repositories.IngredientRepository
import com.ycany.prefinals.data.repositories.RecipeRepository
import com.ycany.prefinals.data.repositories.UserAccountRepository

class DashboardModel(
    private val userAccountRepository: UserAccountRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val favoritesRepository: FavoritesRepository
) {

    suspend fun getSuggestedRecipes(): Result<List<Recipe>> {
        val pantryIngredients = ingredientRepository.getPantryIngredients().map { it.name }
        return if (pantryIngredients.isEmpty()) {
            Result.success(emptyList())
        } else {
            recipeRepository.findRecipesByIngredients(pantryIngredients).map { recipes ->
                recipes.sortedWith(compareBy<Recipe> { -it.usedIngredientCount }
                    .thenBy { it.missedIngredientCount })
            }
        }
    }

    fun getRecipeMatchPercentage(recipe: Recipe): Int {
        val totalIngredients = recipe.usedIngredientCount + recipe.missedIngredientCount
        return if (totalIngredients == 0) 0 else (recipe.usedIngredientCount * 100) / totalIngredients
    }

    fun addToFavorites(recipe: Recipe) {
        favoritesRepository.addFavorite(recipe)
    }

    fun getUsername(username: String?): String {
        return if (username != null) username else userAccountRepository.getCurrentRegistration()?.username.orEmpty()
    }

    fun signOut() {
        userAccountRepository.clearCurrentRegistration()
    }

    fun getProfileUsername(): String {
        return userAccountRepository.getCurrentRegistration()?.username.orEmpty()
    }
}
