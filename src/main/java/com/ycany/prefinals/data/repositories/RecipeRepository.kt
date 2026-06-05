package com.ycany.prefinals.data.repositories

import com.ycany.prefinals.data.models.Recipe


interface RecipeRepository {
    suspend fun findRecipesByIngredients(ingredients: List<String>): Result<List<Recipe>>
    suspend fun getRecipeById(id: Int): Result<Recipe>
    suspend fun searchRecipes(query: String): Result<List<Recipe>>

    suspend fun findRecipesByIngredientsWithMinMatch(
        ingredients: List<String>,
        minMatchPercentage: Int = 0
    ): Result<List<Recipe>> {
        return findRecipesByIngredients(ingredients).map { recipes ->
            recipes.filter { recipe ->
                val totalIngredients = recipe.usedIngredientCount + recipe.missedIngredientCount
                if (totalIngredients == 0) false
                else (recipe.usedIngredientCount * 100) / totalIngredients >= minMatchPercentage
            }
        }
    }
}
