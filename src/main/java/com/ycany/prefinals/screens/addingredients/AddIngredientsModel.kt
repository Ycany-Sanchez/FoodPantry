package com.ycany.prefinals.screens.addingredients

import com.ycany.prefinals.data.models.Ingredient
import com.ycany.prefinals.data.repositories.IngredientRepository
import com.ycany.prefinals.data.repositories.RecipeRepository
import com.ycany.prefinals.data.repositories.SpoonacularRecipeRepository

class AddIngredientsModel(
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository = SpoonacularRecipeRepository()
) {

    fun getPantryIngredients(): List<Ingredient> {
        return ingredientRepository.getPantryIngredients()
    }

    fun removeIngredient(ingredient: Ingredient) {
        ingredientRepository.removeIngredient(ingredient)
    }

    suspend fun searchIngredients(query: String) = ingredientRepository.searchIngredients(query)

    fun addIngredient(ingredient: Ingredient) {
        ingredientRepository.addIngredient(ingredient)
    }

    suspend fun searchRecipes(query: String) = recipeRepository.searchRecipes(query)
}
