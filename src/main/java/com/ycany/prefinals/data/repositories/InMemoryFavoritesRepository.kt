package com.ycany.prefinals.data.repositories

import com.ycany.prefinals.data.models.Recipe

object InMemoryFavoritesRepository : FavoritesRepository {

    private val favorites = mutableListOf<Recipe>()

    override fun addFavorite(recipe: Recipe) {
        if (favorites.none { it.id == recipe.id }) {
            favorites.add(recipe)
        }
    }

    override fun removeFavorite(recipe: Recipe) {
        favorites.removeAll { it.id == recipe.id }
    }

    override fun getFavorites(): List<Recipe> = favorites.toList()

    override fun isFavorite(recipeId: Int): Boolean = favorites.any { it.id == recipeId }

    override fun clearFavorites() {
        favorites.clear()
    }
}
