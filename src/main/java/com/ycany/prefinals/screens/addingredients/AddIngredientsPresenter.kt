package com.ycany.prefinals.screens.addingredients

import com.ycany.prefinals.data.models.Ingredient
import com.ycany.prefinals.data.repositories.IngredientRepository
import com.ycany.prefinals.data.repositories.RecipeRepository
import com.ycany.prefinals.data.repositories.SpoonacularRecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddIngredientsPresenter(
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository = SpoonacularRecipeRepository()
) : AddIngredientsContract.Presenter {

    private var view: AddIngredientsContract.View? = null
    private val model = AddIngredientsModel(ingredientRepository, recipeRepository)
    private val presenterScope = CoroutineScope(Dispatchers.Main + Job())

    override fun attachView(view: AddIngredientsContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onViewReady() {
        view?.renderPantryIngredients(model.getPantryIngredients())
    }

    override fun onRemoveIngredientClicked(ingredient: Ingredient) {
        model.removeIngredient(ingredient)
        view?.renderPantryIngredients(model.getPantryIngredients())
        view?.showMessage("${ingredient.name} removed from pantry.")
    }

    override fun onSearchIngredientClicked(query: String) {
        if (query.isBlank()) {
            view?.showMessage("Please enter an ingredient name")
            return
        }
        view?.showSearchLoading(true)
        presenterScope.launch {
            val result = withContext(Dispatchers.IO) {
                model.searchIngredients(query)
            }
            view?.showSearchLoading(false)
            result.fold(
                onSuccess = { ingredients -> view?.renderSearchResults(ingredients) },
                onFailure = { error ->
                    view?.showMessage("Search failed: ${error.message}")
                    view?.renderSearchResults(emptyList())
                }
            )
        }
    }

    override fun onAddIngredientFromSearchClicked(ingredient: Ingredient) {
        model.addIngredient(ingredient)
        view?.renderPantryIngredients(model.getPantryIngredients())
        view?.showMessage("${ingredient.name} added to pantry.")
        view?.renderSearchResults(emptyList())
    }

    override fun onSearchRecipeClicked(query: String) {
        if (query.isBlank()) {
            view?.showMessage("Please enter a recipe name")
            return
        }
        view?.showRecipeSearchLoading(true)
        presenterScope.launch {
            val result = withContext(Dispatchers.IO) {
                model.searchRecipes(query)
            }
            view?.showRecipeSearchLoading(false)
            result.fold(
                onSuccess = { recipes -> view?.renderRecipeSearchResults(recipes) },
                onFailure = { error ->
                    view?.showMessage("Recipe search failed: ${error.message}")
                    view?.renderRecipeSearchResults(emptyList())
                }
            )
        }
    }

    override fun onRecipeClicked(recipeId: Int) {
        view?.navigateToViewRecipe(recipeId)
    }

    override fun onBackClicked() {
        view?.navigateToDashboard()
    }
}