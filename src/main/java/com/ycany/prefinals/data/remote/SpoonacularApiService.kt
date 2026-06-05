package com.ycany.prefinals.data.remote

import com.ycany.prefinals.data.remote.dto.IngredientSearchResponse
import com.ycany.prefinals.data.remote.dto.RecipeByIngredientItem
import com.ycany.prefinals.data.remote.dto.RecipeInformationResponse
import com.ycany.prefinals.data.remote.dto.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApiService {

    @GET("recipes/findByIngredients")
    suspend fun findRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 10,
        @Query("ranking") ranking: Int = 1,
        @Query("ignorePantry") ignorePantry: Boolean = true,
        @Query("apiKey") apiKey: String = SpoonacularConfig.API_KEY
    ): List<RecipeByIngredientItem>

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") recipeId: Int,
        @Query("includeNutrition") includeNutrition: Boolean = true,
        @Query("apiKey") apiKey: String = SpoonacularConfig.API_KEY
    ): RecipeInformationResponse

    @GET("food/ingredients/search")
    suspend fun searchIngredients(
        @Query("query") query: String,
        @Query("number") number: Int = 20,
        @Query("metaInformation") metaInformation: Boolean = false,
        @Query("apiKey") apiKey: String = SpoonacularConfig.API_KEY
    ): IngredientSearchResponse

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("number") number: Int = 10,
        @Query("addRecipeNutrition") addRecipeNutrition: Boolean = false,
        @Query("apiKey") apiKey: String = SpoonacularConfig.API_KEY
    ): RecipeSearchResponse
}