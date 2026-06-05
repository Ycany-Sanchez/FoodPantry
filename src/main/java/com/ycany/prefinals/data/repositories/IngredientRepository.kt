package com.ycany.prefinals.data.repositories

import com.ycany.prefinals.data.models.Ingredient

interface IngredientRepository {
    fun getPantryIngredients(): List<Ingredient>
    fun addIngredient(ingredient: Ingredient)
    fun removeIngredient(ingredient: Ingredient)

    suspend fun searchIngredients(query: String): Result<List<Ingredient>>

    fun getPantryIngredientCount(): Int = getPantryIngredients().size

    fun hasIngredient(ingredientName: String): Boolean =
        getPantryIngredients().any { it.name.equals(ingredientName, ignoreCase = true) }
}