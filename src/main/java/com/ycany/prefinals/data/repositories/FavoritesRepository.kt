package com.ycany.prefinals.data.repositories

import com.ycany.prefinals.data.models.Recipe

interface FavoritesRepository {
    fun addFavorite(recipe: Recipe)
    fun removeFavorite(recipe: Recipe)
    fun getFavorites(): List<Recipe>
    fun isFavorite(recipeId: Int): Boolean
    fun clearFavorites()
}
