package com.ycany.prefinals.screens.viewrecipe

import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.repositories.FavoritesRepository
import com.ycany.prefinals.data.repositories.RecipeRepository

class ViewRecipeModel(
    private val recipeRepository: RecipeRepository,
    private val favoritesRepository: FavoritesRepository
) {

    suspend fun getRecipeById(recipeId: Int) = recipeRepository.getRecipeById(recipeId)

    fun isFavorite(recipeId: Int): Boolean = favoritesRepository.isFavorite(recipeId)

    fun addFavorite(recipe: Recipe) = favoritesRepository.addFavorite(recipe)

    fun removeFavorite(recipe: Recipe) = favoritesRepository.removeFavorite(recipe)
}