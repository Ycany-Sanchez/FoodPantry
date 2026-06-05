package com.ycany.prefinals.screens.dashboard

import com.ycany.prefinals.data.models.Recipe
import com.ycany.prefinals.data.repositories.FavoritesRepository
import com.ycany.prefinals.data.repositories.InMemoryFavoritesRepository
import com.ycany.prefinals.data.repositories.IngredientRepository
import com.ycany.prefinals.data.repositories.RecipeRepository
import com.ycany.prefinals.data.repositories.SpoonacularRecipeRepository
import com.ycany.prefinals.data.repositories.UserAccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardPresenter(
    private val userAccountRepository: UserAccountRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository = SpoonacularRecipeRepository(),
    private val favoritesRepository: FavoritesRepository = InMemoryFavoritesRepository
) : DashboardContract.Presenter {

    private var view: DashboardContract.View? = null
    private val model = DashboardModel(userAccountRepository, recipeRepository, ingredientRepository, favoritesRepository)
    private val presenterScope = CoroutineScope(Dispatchers.Main + Job())

    override fun attachView(view: DashboardContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onViewReady(username: String?) {
        val resolvedUsername = model.getUsername(username)
        view?.renderUsername(resolvedUsername)
        loadRecipes()
    }

    override fun onResume() {
        loadRecipes()
    }

    private fun loadRecipes() {
        view?.showLoading(true)
        presenterScope.launch {
            val result = withContext(Dispatchers.IO) {
                model.getSuggestedRecipes()
            }
            view?.showLoading(false)
            result.fold(
                onSuccess = { recipes -> view?.renderSuggestedRecipes(recipes) },
                onFailure = { error ->
                    view?.showMessage("Could not load recipes: ${error.message}")
                    view?.renderSuggestedRecipes(emptyList())
                }
            )
        }
    }

    override fun onAddIngredientsClicked() {
        view?.navigateToAddIngredients()
    }

    override fun onProfileClicked() {
        val username = model.getProfileUsername()
        view?.navigateToProfile(username)
    }

    override fun onViewRecipeClicked(recipeId: Int) {
        view?.navigateToViewRecipe(recipeId)
    }

    override fun onSignOutClicked() {
        model.signOut()
        view?.showMessage("Signed out successfully.")
        view?.navigateToLogin()
    }

    override fun onAddToFavoritesClicked(recipe: Recipe) {
        model.addToFavorites(recipe)
        view?.showMessage("Added to favorites!")
    }
}