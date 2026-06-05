package com.ycany.prefinals.data.repositories

import com.ycany.prefinals.data.models.Ingredient
import com.ycany.prefinals.data.remote.RetrofitClient
import com.ycany.prefinals.data.remote.toDomain

object SpoonacularIngredientRepository : IngredientRepository {

    private val api = RetrofitClient.apiService

    private val pantryIngredients = mutableListOf<Ingredient>()

    override fun getPantryIngredients(): List<Ingredient> = pantryIngredients.toList()

    override fun addIngredient(ingredient: Ingredient) {
        if (pantryIngredients.none { it.name.equals(ingredient.name, ignoreCase = true) }) {
            pantryIngredients.add(ingredient)
        }
    }

    override fun removeIngredient(ingredient: Ingredient) {
        pantryIngredients.removeAll { it.name.equals(ingredient.name, ignoreCase = true) }
    }

    override suspend fun searchIngredients(query: String): Result<List<Ingredient>> {
        return try {
            val response = api.searchIngredients(query = query, number = 20)
            Result.success(response.results.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}