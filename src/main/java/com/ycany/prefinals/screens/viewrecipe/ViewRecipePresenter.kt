package com.ycany.prefinals.screens.viewrecipe

import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.repositories.FavoritesRepository
import com.ycany.prefinals.data.repositories.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewRecipePresenter(
    private val recipeRepository: RecipeRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewRecipeContract.Presenter {

    private var view: ViewRecipeContract.View? = null
    private val model = ViewRecipeModel(recipeRepository, favoritesRepository)
    private val presenterScope = CoroutineScope(Dispatchers.Main + Job())

    private var currentRecipe: Recipe? = null

    override fun attachView(view: ViewRecipeContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onViewReady(recipeId: Int) {
        view?.showLoading(true)
        presenterScope.launch {
            val result = withContext(Dispatchers.IO) {
                model.getRecipeById(recipeId)
            }
            view?.showLoading(false)
            result.fold(
                onSuccess = { recipe ->
                    currentRecipe = recipe
                    view?.renderRecipe(recipe)
                    val isFav = model.isFavorite(recipe.id)
                    view?.updateFavoriteStatus(isFav)
                },
                onFailure = { error ->
                    view?.showMessage("Failed to load recipe: ${error.message}")
                    view?.navigateToDashboard()
                }
            )
        }
    }

    override fun onBackClicked() {
        view?.navigateToDashboard()
    }

    override fun onFavoriteClicked() {
        val recipe = currentRecipe ?: return
        val currentlyFavorite = model.isFavorite(recipe.id)

        if (currentlyFavorite) {
            model.removeFavorite(recipe)
            view?.updateFavoriteStatus(false)
            view?.showMessage("Removed from favorites")
        } else {
            model.addFavorite(recipe)
            view?.updateFavoriteStatus(true)
            view?.showMessage("Added to favorites")
        }
    }
}