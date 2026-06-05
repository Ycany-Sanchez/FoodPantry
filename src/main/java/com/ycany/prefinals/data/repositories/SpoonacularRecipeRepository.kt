package com.ycany.prefinals.data.repositories

import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.remote.RetrofitClient
import com.ycany.prefinals.data.remote.toDomain

class SpoonacularRecipeRepository : RecipeRepository {

    private val api = RetrofitClient.apiService

    override suspend fun findRecipesByIngredients(ingredients: List<String>): Result<List<Recipe>> {
        return try {
            val ingredientsCsv = ingredients.joinToString(",")
            val response = api.findRecipesByIngredients(ingredients = ingredientsCsv, number = 10)
            Result.success(response.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipeById(id: Int): Result<Recipe> {
        return try {
            val response = api.getRecipeInformation(recipeId = id, includeNutrition = true)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchRecipes(query: String): Result<List<Recipe>> {
        return try {
            val response = api.searchRecipes(query = query)
            val recipes = response.results.map { result ->
                Recipe(
                    id = result.id,
                    title = result.title,
                    image = result.image,
                    servings = 0,
                    readyInMinutes = 0,
                    ingredients = emptyList(),
                    instructions = emptyList(),
                    calories = "—",
                    protein = "—",
                    carbs = "—",
                    fat = "—"
                )
            }
            Result.success(recipes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}